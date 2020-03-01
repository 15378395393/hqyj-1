# -*- coding: utf-8 -*-
from wsgiref.simple_server import make_server;

# WSGI服务器
def wsgiServer():
    # 创建wsgi server，输入ip、端口以及web处理函数
    wsgiServer = make_server("127.0.0.1", 8080, application);
    # 开始监听http请求
    wsgiServer.serve_forever();

# web处理程序
def application(requestDict, responseFunction):
    print(requestDict);
    responseFunction("200 OK", [("Content-Type", "text/html")]);
    body = "<h1>Hello World! The path is %s</h1>" % (requestDict["PATH_INFO"] or "None");
    return [body.encode("utf-8")];

wsgiServer();