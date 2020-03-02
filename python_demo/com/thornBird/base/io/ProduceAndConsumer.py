# -*- coding: utf-8 -*-

# 消费者（生成器generator）
def consumer():
    r = "";
    while True:
        temp = yield r;
        if not temp:
            return;
        print("[Consumer] consuming %s..." % temp);
        r = "200 OK";

# 生产者
def produce(consumer):
    consumer.send(None);
    n = 0;
    while n < 5:
        n += 1;
        print("[Produce] producing %s..." % n);
        r = consumer.send(n);
        print("[Consumer] consumer return %s" % r);
    consumer.close();

consumer = consumer();
produce(consumer);
