import time, random, queue;
from multiprocessing.managers import BaseManager;

# 发送任务队列
sendQueue = queue.Queue();
# 接收任务队列
receiveQueue = queue.Queue();

'''
为什么要定义这两个函数？
如果在初始化manager的时候，使用lambda表达式，那么在创先进程的时候会报错
_pickle.PicklingError: Can't pickle <function <lambda> at 0x000002C4573B51F0>: 
attribute lookup <lambda> on __main__ failed
Windows没有fork函数，multiprocessing需要模拟fork的效果，是通过pickle实现的，
报错信息可知在pickle时候不能调用lambda表达式，所以我们创建函数替换lambda
'''
def getSendQueue():
    global sendQueue;
    return sendQueue;
def getReceiveQueue():
    global receiveQueue;
    return receiveQueue;

# 创建QueueManager继承BaseManager
class QueueManager(BaseManager):
    pass;

if __name__ == "__main__":
    # 将两个队列QueueManager上注册，参数一是注册名，参数二关联Queue对象
    # QueueManager.register("sendQueue", callable = lambda : sendQueue);
    # QueueManager.register("receiveQueue", callable = lambda : receiveQueue);
    QueueManager.register("sendQueue", callable=getSendQueue);
    QueueManager.register("receiveQueue", callable=getReceiveQueue);
    # 创建QueueManager对象，绑定8080端口，设置验证码‘abc’
    manager = QueueManager(address=("127.0.0.1", 8080), authkey=b'abc');
    # 启动manager
    manager.start();
    # 从manager中取出send queue，不能对原生的sendQueue操作，会绕过manager的封装
    # 根据注册名来获取
    send = manager.sendQueue();
    receive = manager.receiveQueue();
    # 在send queue中放任务
    for i in range(5):
        n = random.randint(0, 100);
        print("Put %d in send Queue" % n);
        send.put(n);
    # 从receive queue中拿到queue对象
    print("Try to get reveive queue");
    for i in range(5):
        r = receive.get(timeout = 10);
        print("Result is %d" % r);
    # 关闭manager
    manager.shutdown();
    print("Master exit");