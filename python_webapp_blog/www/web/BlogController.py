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