import socket;

# ---- socket客户端 ----
def clientSocket():
    # 创建socket，指定TCP/IP协议
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
    # 连接目标，指定ip和端口
    s.connect(("www.sina.com.cn", 80));
    # 发送请求
    s.send(b'GET / HTTP/1.1\r\nHost: www.sina.com.cn\r\nConnection: close\r\n\r\n');
    # 处理返回数据
    buffer = [];
    while True:
        # 每次最多接受1k字节
        d = s.recv(1024);
        if d:
            buffer.append(d);
        else:
            break;
    data = b''.join(buffer);
    # 关闭socket
    s.close();
    # 处理返回数据，打印响应头，网页内容写入文件
    headers, body = data.split(b'\r\n\r\n', 1);
    print(headers.decode("utf-8"));
    with open("D:\\temp\\baidu.html", "wb") as f:
        f.write(body);
# clientSocket();

# 客户端程序，测试serverSocket
def clientSocketTest():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
    s.connect(("127.0.0.1", 9999));
    # 接收欢迎消息
    print(s.recv(1024).decode("utf-8"));
    for data in [b'HymanHu', b'JiangHu', b'Ye']:
        s.send(data);
        print(s.recv(1024).decode("utf-8"));
    s.send(b'exit');
    s.close();
# clientSocketTest();

# 客户端程序，测试serverSocketForUDP
def clientSocketTestForUDP():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM);
    for data in [b'Michael', b'Tracy', b'Sarah']:
        # 发送数据:
        s.sendto(data, ('127.0.0.1', 9999));
        # 接收数据:
        print(s.recv(1024).decode('utf-8'));
    s.close();
clientSocketTestForUDP();
