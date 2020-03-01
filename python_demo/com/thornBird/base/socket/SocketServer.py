# -*- coding: utf-8 -*-
import socket, threading;

# ---- socket服务端 ----
def serverSocket():
    # 创建基于TCP/IP协议的socket
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
    # 监听本机9999端口
    s.bind(("127.0.0.1", 9999));
    s.listen(5); # 监听最大连接数
    # 死循环接受客户端连接
    while True:
        # 接受一个连接，获得socket和address
        sock, addr = s.accept();
        # 创建新线程来处理TCP连接
        t = threading.Thread(target=tcpLink, args=(sock, addr));
        t.start();

# 服务端处理连接方法
def tcpLink(socket, address):
    print("Accept new connection from %s:%s" % address);
    socket.send(b'wellcome');
    while True:
        data = socket.recv(1024);
        if not data or data.decode("utf-8") == "exit":
            break;
        socket.send(('Hello %s' % data.decode("utf-8")).encode("utf-8"));
    socket.close();
    print("Connection is closed from %s" % address);
# serverSocket();

# ---- UDP类型服务端 ----
def serverSocketForUDP():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM);
    s.bind(("127.0.0.1", 9999));
    print("Bind UDP on 9999..");
    while True:
        # udp接受数据/发送数据的方式
        data, addr = s.recvfrom(1024);
        print('Received from %s:%s.' % addr);
        s.sendto(b'Hello, %s!' % data, addr);
serverSocketForUDP();