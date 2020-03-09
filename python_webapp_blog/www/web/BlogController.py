#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
'Url handlers'
from www.web.WebHandler import *;
from www.orm.Models import *;

@get('/index')
async def index(request):
    users = await User.findAll();
    return {
        '__template__': 'index.html',
        'users': users
    };

@get("/blogs")
async def blogs(request):
    # test data
    summary = "Balabalabala...";
    content = "一个IO操作阻塞了当前线程，导致其他代码无法执行，" \
              "所以我们必须使用多线程或者多进程来并发执行代码，" \
              "为多个用户服务，每个用户都会分配一个线程，<br/>" \
              "现代操作系统对IO操作已经做了巨大的改进，最大的特点就是支持异步IO。";
    blogs = [
        Blog(id='1', name='Test Blog1', content=content, summary=summary, created_date=time.time() - 120),
        Blog(id='2', name='Test Blog2', content=content, summary=summary, created_date=time.time() - 3600),
        Blog(id='3', name='Test Blog3', content=content, summary=summary, created_date=time.time() - 7200),
        Blog(id='3', name='Test Blog4', content=content, summary=summary, created_date=time.time() - 704800)
    ];
    return {
        '__template__': 'blogs.html',
        'blogs': blogs
    };