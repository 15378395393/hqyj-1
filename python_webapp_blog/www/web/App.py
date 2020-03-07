#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, json, os, time;
import www.orm.Orm;
import www.web.WebHandler;
from aiohttp import web;
from aiohttp.web import middleware;
from www.common.GlobalLog import LOGGER;
from jinja2 import Environment, FileSystemLoader;
from datetime import datetime;

# 时间格式化处理器过滤器
def datetime_filter(t):
    delta = int(time.time() - t);
    if delta < 60:
        return u'1分钟前';
    if delta < 3600:
        return u'%s分钟前' % (delta // 60);
    if delta < 86400:
        return u'%s小时前' % (delta // 3600);
    if delta < 604800:
        return u'%s天前' % (delta // 86400);
    dt = datetime.fromtimestamp(t);
    return u'%s年%s月%s日' % (dt.year, dt.month, dt.day);

# 引入jinja2模版，并初始化配置
def init_jinja2(app, **kw):
    LOGGER.info('init jinja2...');
    # 获取相应参数
    options = dict(
        autoescape = kw.get('autoescape', True),
        block_start_string = kw.get('block_start_string', '{%'),
        block_end_string = kw.get('block_end_string', '%}'),
        variable_start_string = kw.get('variable_start_string', '{{'),
        variable_end_string = kw.get('variable_end_string', '}}'),
        auto_reload = kw.get('auto_reload', True)
    );
    # 获取模版路径，如果没有则在同级template目录下
    path = kw.get('path', None);
    if path is None:
        path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'templates');
    LOGGER.info('set jinja2 template path: %s' % path);
    env = Environment(loader=FileSystemLoader(path), **options);
    filters = kw.get('filters', None);
    if filters is not None:
        for name, f in filters.items():
            env.filters[name] = f;
    # 将模版设置到app中
    app['__template__'] = env;

# 定义中间件logger_factory，log请求method、path
@middleware
async def logger_factory(app, handler):
    async def logger(request):
        LOGGER.info('Request: %s %s' % (request.method, request.path));
        # await asyncio.sleep(0.3)
        return (await handler(request));
    return logger;

# 定义中间件data_factory，将请求的数据包装到request.__data__中
@middleware
async def data_factory(app, handler):
    async def parse_data(request):
        if request.method == 'POST':
            if request.content_type.startswith('application/json'):
                request.__data__ = await request.json();
                LOGGER.info('request json: %s' % str(request.__data__));
            elif request.content_type.startswith('application/x-www-form-urlencoded'):
                request.__data__ = await request.post();
                LOGGER.info('request form: %s' % str(request.__data__));
        return (await handler(request));
    return parse_data;

# 定义中间件response_factory，处理响应
@middleware
async def response_factory(app, handler):
    async def response(request):
        LOGGER.info('Response handler...');
        r = await handler(request);
        # 如果是响应流，直接返回
        if isinstance(r, web.StreamResponse):
            return r;
        # 如果是bytes，包装web.Response
        if isinstance(r, bytes):
            resp = web.Response(body=r);
            resp.content_type = 'application/octet-stream';
            return resp;
        # 如果是string，区别是否为重定向
        if isinstance(r, str):
            if r.startswith('redirect:'):
                return web.HTTPFound(r[9:]);
            resp = web.Response(body=r.encode('utf-8'));
            resp.content_type = 'text/html;charset=utf-8';
            return resp;
        # 如果是dict，找到模版名称，并装载模版
        if isinstance(r, dict):
            template = r.get('__template__');
            if template is None:
                resp = web.Response(
                    body=json.dumps(r, ensure_ascii=False, default=lambda o: o.__dict__).encode('utf-8'));
                resp.content_type = 'application/json;charset=utf-8';
                return resp;
            else:
                resp = web.Response(
                    body=app['__templating__'].get_template(template).render(**r).encode('utf-8'));
                resp.content_type = 'text/html;charset=utf-8';
                return resp;
        # 检查是否响应码
        if isinstance(r, int) and r >= 100 and r < 600:
            return web.Response(r);
        if isinstance(r, tuple) and len(r) == 2:
            t, m = r;
            if isinstance(t, int) and t >= 100 and t < 600:
                return web.Response(t, str(m));
        # default:
        resp = web.Response(body=str(r).encode('utf-8'));
        resp.content_type = 'text/plain;charset=utf-8';
        return resp;
    return response;

# 主页http://127.0.0.1:8080/
def index(request):
    resp = web.Response(body=b'<h1>Python WebApp Blog</h1>');
    # 如果不添加content_type，某些严谨的浏览器会把网页当成文件下载，而不是直接显示
    resp.content_type = "text/html;charset=utf-8";
    return resp;

# 初始化aiohttp web server
async def init(loop):
    # 创建数据库连接池
    await www.orm.Orm.createPool(loop, user="root", password="root", db="python_blog");
    # 初始化app，并传入中间件
    app = web.Application(middlewares=[
        logger_factory, data_factory, response_factory
    ]);
    # 初始化页面模版
    init_jinja2(app, filters=dict(datetime=datetime_filter));
    # 注册路由，全书写在www.web.handlers模块下
    www.web.WebHandler.add_routes(app, "www.web.UrlHandlers");
    # 注册静态资源
    www.web.WebHandler.add_static(app);
    # 启动app
    runner = web.AppRunner(app);
    await runner.setup();
    srv = web.TCPSite(runner, '127.0.0.1', 8080)
    LOGGER.debug("Server start at 127.0.0.1:8080...");
    await srv.start();

loop = asyncio.get_event_loop();
loop.run_until_complete(init(loop));
loop.run_forever();