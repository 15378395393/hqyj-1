# -*- coding: utf-8 -*-
# ---- asyncio ----
import asyncio;
import threading;

# @asyncio.coroutine把一个generator标记为coroutine类型，然后把这个coroutine扔到EventLoop中执行
@asyncio.coroutine
def hello():
    print("Hello World! (%s)" % threading.current_thread());
    '''
    yield from语法可以让我们方便地调用另一个generator
    由于asyncio.sleep()也是一个coroutine，所以线程不会等待asyncio.sleep()，而是直接中断并执行下一个消息循环
    当asyncio.sleep()返回时，线程就可以从yield from拿到返回值（此处是None），然后接着执行下一行语句
    '''
    r = yield from asyncio.sleep(1);
    print("Hello World again! (%s)" % threading.current_thread());

def helloLoop():
    # 获取EventLoop
    loop = asyncio.get_event_loop();
    # 执行单个coroutine
    # loop.run_until_complete(hello());
    # 执行多个coroutine
    loop.run_until_complete(asyncio.wait([hello(), hello()]));
    loop.close();
# helloLoop();

# ---- async/await ----
import asyncio;
import threading;

async def hello2():
    print("Hello World! (%s)" % threading.current_thread());
    r = await asyncio.sleep(1);
    print("Hello World again! (%s)" % threading.current_thread());

def helloLoop2():
    loop = asyncio.get_event_loop();
    loop.run_until_complete(asyncio.wait([hello2(), hello2()]));
    loop.close();
helloLoop2();

# ---- asyncio\StreamReader\StreamWriter ----
import asyncio;
import urllib.parse;

@asyncio.coroutine
def wget(url):
    print("wget host %s" % url);
    # 解析url，根据scheme来判断端口
    urlParse = urllib.parse.urlsplit(url);
    port = 443 if (urlParse.scheme == "https") else 80;

    '''
    打开连接，获取StreamReader（从IO数据流中读数据）、StreamWriter对象（从IO数据流中写数据）
    不建议直接实例化reader, writer，而是通过open_connection()或start_server()创建
    '''
    reader, writer = yield from asyncio.open_connection(url, port);
    # 将请求信息写入IO
    query = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n' % url;
    writer.write(query.encode("utf-8"));
    '''
    这是一个与底层IO输入缓冲区交互的流量控制方法
    当缓冲区达到上限时，drain()阻塞，待到缓冲区回落到下限时，写操作可以被恢复
    当不需要等待时，drain()会立即返回
    '''
    yield from writer.drain();
    while True:
        line = yield from reader.readline();
        if line == b'\r\n':
            break;
        print("%s header ---- %s" % (url, line.decode("utf-8")));
    # Ignore the body, close the socket
    writer.close();

def wgetLoop():
    loop = asyncio.get_event_loop();
    tasks = [wget(url) for url in ['www.sina.com.cn', 'www.sohu.com', 'www.163.com']];
    loop.run_until_complete(asyncio.wait(tasks));
    loop.close();
# wgetLoop();



