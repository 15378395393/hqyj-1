#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import functools, inspect,os, asyncio, time, hashlib;
from aiohttp import web;
from urllib import parse;
from www.common.GlobalLog import LOGGER;
from www.common.CustomException import *;
from conf.Config import configs;
from www.orm.Models import User;

# ---- 常量 ----
COOKIE_NAME = 'pythonWebBlog';
COOKIE_KEY = configs.session.secret;
URL_FILTER_LIST = ["/register", "/api/user", "/signin", "/api/authenticate"];

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
                content_type = request.content_type.lower();
                if content_type.startswith('application/json'):
                    params = await request.json();
                    if not isinstance(params, dict):
                        return web.HTTPBadRequest('JSON body must be object.');
                    kw = params;
                elif content_type.startswith('application/x-www-form-urlencoded') \
                        or content_type.startswith('multipart/form-data'):
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

# ---- 注册静态资源 ----
def add_static(app):
    separator = "\\" if os.name == "nt" else "/";
    projectName = "python_webapp_blog" + separator;
    curPath = os.path.abspath(os.path.dirname(__file__));  # 获取当前文夹路径
    rootPath = curPath[:curPath.find(projectName) + len(projectName)];  # 获取项目根目录
    staticPath = rootPath + "www" + separator + "static";
    app.router.add_static('/static/', staticPath);
    LOGGER.info('add static %s => %s' % ('/static/', staticPath));

# ---- 注册Url处理函数 ----
def add_route(app, fn):
    method = getattr(fn, '__method__', None);
    path = getattr(fn, '__route__', None);
    if path is None or method is None:
        raise ValueError('@get or @post not defined in %s.' % str(fn));
    # 判断函数是否为coroutine、生成器函数
    if not asyncio.iscoroutinefunction(fn) and not inspect.isgeneratorfunction(fn):
        fn = asyncio.coroutine(fn);
    LOGGER.info('add route %s %s => %s(%s)' % (method, path, fn.__name__,
        ', '.join(inspect.signature(fn).parameters.keys())));
    # 注册函数的时候，初始化RequestHandler对象，因其重写了__call__函数，该对象实例化当函数调用
    app.router.add_route(method, path, RequestHandler(app, fn));

# 根据模块批量注册Url处理函数
def add_routes(app, module_name):
    n = module_name.rfind('.')
    if n == (-1):
        mod = __import__(module_name, globals(), locals());
    else:
        name = module_name[n+1:];
        mod = getattr(__import__(module_name[:n], globals(), locals(), [name]), name);
    # 扫描模块属性，若是有__method__、__route__则添加app.router中
    for attr in dir(mod):
        if attr.startswith('_'):
            continue;
        fn = getattr(mod, attr);
        if callable(fn):
            method = getattr(fn, '__method__', None);
            path = getattr(fn, '__route__', None);
            if method and path:
                add_route(app, fn);

# ---- COOKIE处理 ----
# 根据user生成cookie
def user2cookie(user, max_age):
    expires = str(int(time.time() + max_age));
    s = '%s-%s-%s-%s' % (user.id, user.password, expires, COOKIE_KEY);
    L = [user.id, expires, hashlib.sha1(s.encode('utf-8')).hexdigest()];
    return '-'.join(L);

# 根据cookie加载user
async def cookie2user(cookie_str):
    if not cookie_str:
        return None;
    try:
        L = cookie_str.split('-');
        if len(L) != 3:
            return None;
        uid, expires, sha1 = L;
        if int(expires) < time.time():
            return None;
        user = await User.find(uid);
        if user is None:
            return None;
        s = '%s-%s-%s-%s' % (uid, user.password, expires, COOKIE_KEY);
        if sha1 != hashlib.sha1(s.encode('utf-8')).hexdigest():
            LOGGER.info('invalid sha1');
            return None;
        user.passwd = '******';
        return user;
    except Exception as e:
        LOGGER.exception(e);
        return None;