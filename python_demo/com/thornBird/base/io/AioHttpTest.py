# -*- coding: utf-8 -*-
import asyncio;
from aiohttp import web;

# http://127.0.0.1:8000/
async def index(request):
    await asyncio.sleep(1);
    return web.Response(body=b'<h1>Index</h1>');

# http://127.0.0.1:8000/hello/HymanHu
async def hello(request):
    await asyncio.sleep(1);
    body = '<h1>hello, %s!</h1>' % request.match_info['name'];
    return web.Response(body=body.encode("utf-8"));

# aiohttp的初始化函数init()也是一个coroutine，loop.create_server()则利用asyncio创建TCP服务
async def init(loop):
    app = web.Application(loop=loop);
    app.router.add_route("GET", "/", index);
    app.router.add_route("GET", "/hello/{name}", hello);
    server = await loop.create_server(app.make_handler(), '127.0.0.1', 8000);
    print('Server started at http://127.0.0.1:8000...');
    return server;

loop = asyncio.get_event_loop();
loop.run_until_complete(init(loop));
loop.run_forever();
