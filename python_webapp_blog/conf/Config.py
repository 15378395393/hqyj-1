#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";

import conf.DevConfig;

'''
Config dict，支持直接获取配置属性，例如：x.y
'''
class ConfigDict(dict):
    def __init__(self, names=(), values=(), **kw):
        super(ConfigDict, self).__init__(**kw);
        for k, v in zip(names, values):
            self[k] = v;
    def __getattr__(self, key):
        try:
            return self[key];
        except KeyError:
            raise AttributeError(r"'Dict' object has no attribute '%s'" % key);
    def __setattr__(self, key, value):
        self[key] = value;

# 合并不同配置
def merge(defaults, override):
    r = {};
    for k, v in defaults.items():
        if k in override:
            if isinstance(v, dict):
                r[k] = merge(v, override[k]);
            else:
                r[k] = override[k];
        else:
            r[k] = v;
    return r;

# 将dict转化为自定义子类ConfigDict
def toConfigDict(d):
    D = ConfigDict();
    for k, v in d.items():
        D[k] = toConfigDict(v) if isinstance(v, dict) else v
    return D;

# 获取dev config配置信息
configs = conf.DevConfig.configs;

try:
    # 尝试导入live config，如果有则合并两个配置信息
    import conf.LiveConfig;
    configs = merge(configs, conf.LiveConfig.configs);
except ImportError:
    pass

configs = toConfigDict(configs);