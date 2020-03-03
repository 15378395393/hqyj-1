# -*- coding: utf-8 -*-
import asyncio;
from aiohttp import web;

# http://127.0.0.1:8000/
async def index(request):
    await asyncio.sleep(1);
    resp = web.Response(body=b'<h1>Index</h1>');
    # 如果不添加content_type，某些严谨的浏览器会把网页当成文件下载，而不是直接显示
    resp.content_type = "text/html;charset=utf-8";
    return resp;

# http://127.0.0.1:8000/hello/HymanHu
async def hello(request):
    await asyncio.sleep(1);
    body = '<h1>hello, %s!</h1>' % request.match_info['name'];
    resp = web.Response(body=body.encode("utf-8"));
    resp.content_type = "text/html;charset=utf-8";
    return resp;

# aiohttp的初始化函数init()也是一个coroutine，loop.create_server()则利用asyncio创建TCP服务
async def init(loop):
    # 此处使用loop参数，会出现deprecated警告，直接不用或者补全其他参数
    # app = web.Application(loop=loop);
    app = web.Application();
    app.router.add_route("GET", "/", index);
    app.router.add_route("GET", "/hello/{name}", hello);

    # 老的写法，会产生deprecated警告
    # server = await loop.create_server(app.make_handler(), '127.0.0.1', 8000);
    # print('Server started at http://127.0.0.1:8000...');
    # return server;
    # 新的写法
    runner = web.AppRunner(app);
    await runner.setup();
    server = web.TCPSite(runner, '127.0.0.1', 8000)
    print('Server started at http://127.0.0.1:8000...');
    await server.start();

loop = asyncio.get_event_loop();
loop.run_until_complete(init(loop));
loop.run_forever();