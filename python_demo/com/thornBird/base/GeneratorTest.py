# -*- coding: utf-8 -*-

# ---- 列表转generator ----
generator1 = (item * item for item in range(10));
print(next(generator1));
print(generator1.__next__());
print(next(generator1));
print("-------------------------")

# ---- 函数转generator ----
def test():
    i = 1;
    while True:
        temp = yield i**2;
        print(temp);
        i += 1;
t = test();
# 第一次运行只能使用next或者send(None)
print(t.__next__());
# send函数是Generator类的方法
# send的作用相当于使生成器继续运行
# send参数，传递给yield返回值，即temp = "Hello World"
print(t.send("Hello World"));
# 相当于send(None)，即temp = None
print(next(t));