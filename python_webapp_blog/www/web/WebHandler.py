#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import functools, inspect,os, asyncio;
from aiohttp import web;
from urllib import parse;
from www.common.GlobalLog import LOGGER;
from www.common.CustomException import *;

# get装饰器:@get('/path')
def get(path):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            return func(*args, **kw);
        wrapper.__method__ = 'GET';
        wrapper.__route__ = path;
        return wrapper;
    return decorator;

# post装饰器:@post('/path')
def post(path):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            return func(*args, **kw);
        wrapper.__method__ = 'POST';
        wrapper.__route__ = path;
        return wrapper;
    return decorator;

# 解析出必须的命名关键字参数
def get_required_kw_args(fn):
    args = [];
    # inspect获取函数签名、参数等信息
    params = inspect.signature(fn).parameters;
    for name, param in params.items():
        if param.kind == inspect.Parameter.KEYWORD_ONLY and param.default == inspect.Parameter.empty:
            args.append(name);
    return tuple(args);

# 解析出命名关键字参数
def get_named_kw_args(fn):
    args = [];
    params = inspect.signature(fn).parameters;
    for name, param in params.items():
        if param.kind == inspect.Parameter.KEYWORD_ONLY:
            args.append(name);
    return tuple(args);

# 判断是否有命名关键字参数
def has_named_kw_args(fn):
    params = inspect.signature(fn).parameters;
    for name, param in params.items():
        if param.kind == inspect.Parameter.KEYWORD_ONLY:
            return True;

# 判断是否有可变关键字参数
def has_var_kw_arg(fn):
    params = inspect.signature(fn).parameters;
    for name, param in params.items():
        if param.kind == inspect.Parameter.VAR_KEYWORD:
            return True;

# 判断函数中是否有可变参数、可变关键字参数、命名关键字参数
def has_request_arg(fn):
    sig = inspect.signature(fn);
    params = sig.parameters;
    found = False;
    for name, param in params.items():
        if name == 'request':
            found = True;
            continue;
        if found and (param.kind != inspect.Parameter.VAR_POSITIONAL
            and param.kind != inspect.Parameter.KEYWORD_ONLY
            and param.kind != inspect.Parameter.VAR_KEYWORD):
            raise ValueError('request parameter must be the last named parameter in function: %s%s'
                % (fn.__name__, str(sig)));
    return found;

# ---- 请求处理类 ----
class RequestHandler(object):
    def __init__(self, app, fn):
        self._app = app;
        self._func = fn;
        self._has_request_arg = has_request_arg(fn);
        self._has_var_kw_arg = has_var_kw_arg(fn);
        self._has_named_kw_args = has_named_kw_args(fn);
        self._named_kw_args = get_named_kw_args(fn);
        self._required_kw_args = get_required_kw_args(fn);

    '''
    参数处理
    重写__call__，让RequestHandler实例可以当函数调用
    协程编程，每个Url处理函数都应是coroutine类型，添加async
    '''
    async def __call__(self, request):
        kw = None;
        if self._has_var_kw_arg or self._has_named_kw_args or self._required_kw_args:
            # 处理post中application/json、application/x-www-form-urlencoded、multipart/form-data类型
            if request.method == 'POST':
                if not request.content_type:
                    return web.HTTPBadRequest('Missing Content-Type.');
                contentType = request.content_type.lower();
                if contentType.startswith('application/json'):
                    params = await request.json();
                    if not isinstance(params, dict):
                        return web.HTTPBadRequest('JSON body must be object.');
                    kw = params;
                elif contentType.startswith('application/x-www-form-urlencoded') \
                        or contentType.startswith('multipart/form-data'):
                    params = await request.post();
                    kw = dict(**params);
                else:
                    return web.HTTPBadRequest('Unsupported Content-Type: %s' % request.content_type);
            # 处理Get请求中查询字符串
            if request.method == 'GET':
                qs = request.query_string;
                if qs:
                    kw = dict()
                    for k, v in parse.parse_qs(qs, True).items():
                        kw[k] = v[0];
        if kw is None:
            kw = dict(**request.match_info);
        else:
            if not self._has_var_kw_arg and self._named_kw_args:
                # remove all unnamed kw:
                copy = dict();
                for name in self._named_kw_args:
                    if name in kw:
                        copy[name] = kw[name];
                kw = copy;
            # check named arg:
            for k, v in request.match_info.items():
                if k in kw:
                    LOGGER.warning('Duplicate arg name in named arg and kw args: %s' % k);
                kw[k] = v;
        if self._has_request_arg:
            kw['request'] = request;
        # check required kw:
        if self._required_kw_args:
            for name in self._required_kw_args:
                if not name in kw:
                    return web.HTTPBadRequest('Missing argument: %s' % name);
        LOGGER.info('call with args: %s' % str(kw));

        try:
            r = await self._func(**kw);
            return r;
        except APIError as e:
            return dict(error=e.error, data=e.data, message=e.message);

# ---- 注册Url处理函数 ----
def add_static(app):
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'static')
    app.router.add_static('/static/', path)
    LOGGER.info('add static %s => %s' % ('/static/', path))

def add_route(app, fn):
    method = getattr(fn, '__method__', None)
    path = getattr(fn, '__route__', None)
    if path is None or method is None:
        raise ValueError('@get or @post not defined in %s.' % str(fn))
    if not asyncio.iscoroutinefunction(fn) and not inspect.isgeneratorfunction(fn):
        fn = asyncio.coroutine(fn)
    LOGGER.info('add route %s %s => %s(%s)' % (method, path, fn.__name__, ', '.join(inspect.signature(fn).parameters.keys())))
    app.router.add_route(method, path, RequestHandler(app, fn))

def add_routes(app, module_name):
    n = module_name.rfind('.')
    if n == (-1):
        mod = __import__(module_name, globals(), locals())
    else:
        name = module_name[n+1:]
        mod = getattr(__import__(module_name[:n], globals(), locals(), [name]), name)
    for attr in dir(mod):
        if attr.startswith('_'):
            continue
        fn = getattr(mod, attr)
        if callable(fn):
            method = getattr(fn, '__method__', None)
            path = getattr(fn, '__route__', None)
            if method and path:
                add_route(app, fn)