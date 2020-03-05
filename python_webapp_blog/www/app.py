#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, os, json, time;
from aiohttp import web;
from datetime import datetime;
from www.globalLog import LOGGER;

# 主页http://127.0.0.1:8080/
def index(request):
    resp = web.Response(body=b'<h1>Python WebApp Blog</h1>');
    # 如果不添加content_type，某些严谨的浏览器会把网页当成文件下载，而不是直接显示
    resp.content_type = "text/html;charset=utf-8";
    return resp;

# 初始化aiohttp web server
async def init(loop):
    app = web.Application();
    app.router.add_route("GET", "/", index);
    runner = web.AppRunner(app);
    await runner.setup();
    srv = web.TCPSite(runner, '127.0.0.1', 8080)
    LOGGER.debug("Server start at 127.0.0.1:8080...");
    await srv.start();

loop = asyncio.get_event_loop();
loop.run_until_complete(init(loop));
loop.run_forever();