#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
'dev config file'

configs = {
    "debug": True,
    "db": {
        "host": "127.0.0.1",
        "port": 3306,
        "user": "root",
        "password": "root",
        "db": "python_blog"
    },
    "session": {
        "secret": "PythonWebappBlog"
    }
};