#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, www.orm;
from www.models import User, Blog, Comment;

async def entityTest(loop):
    # 如果不加yield from，只是创建了协程，没有真正的调用
    await www.orm.createPool(loop, user="root", password="root", db="python_blog");
    user = User(name="admin", email="admin1@163.com", password="111111", image="about:blank");
    await user.save();

loop = asyncio.get_event_loop();
loop.run_until_complete(entityTest(loop));
loop.close();