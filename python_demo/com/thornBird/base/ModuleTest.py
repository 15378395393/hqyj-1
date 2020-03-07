#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";

# ---- 根据模块路径获取module ----
def getModel(module_name):
    n = module_name.rfind('.');
    if n == (-1):
        mod = __import__(module_name, globals(), locals());
    else:
        name = module_name[n + 1:];
        mod = getattr(__import__(module_name[:n], globals(), locals(), [name]), name);
    for attr in dir(mod):
        if attr.startswith('_'):
            continue;
        fn = getattr(mod, attr);
        print(fn);
        # 获取fn属性
        # if callable(fn):
        #     method = getattr(fn, '__method__', None);
        #     path = getattr(fn, '__route__', None);

module = getModel("com.thornBird.base.io.ProduceAndConsumer");