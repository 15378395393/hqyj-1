import time, sys, queue;
from multiprocessing.managers import BaseManager;

# 创建QueueManager，继承BaseManager
class QueueManager(BaseManager):
    pass;

# worker端从网络上获取queue，所以只需注册名称
QueueManager.register("sendQueue");
QueueManager.register("receiveQueue");
# 创建QueueManager对象，连接master manager
serverAddr = "127.0.0.1";
print("Connect server %s" % serverAddr);
manager = QueueManager(address=("127.0.0.1", 8080), authkey=b'abc');
# 从网络连接master
manager.connect();
# 获取queue对象
sendQueue = manager.sendQueue();
receiveQueue = manager.receiveQueue();
# 从sendQueue中取任务，并将处理结果装到receiveQueue中
for i in range(5):
    try:
        n = sendQueue.get(timeout = 1);
        result = n * n;
        print("Run task: %d * %d = %d" % (n, n, result));
        time.sleep(1);
        receiveQueue.put(result);
    except queue.Queue.Empty:
        print("Serd Queue is empty");
# worker Process 结束
print("Worker exit");

