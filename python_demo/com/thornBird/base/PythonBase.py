# input and output
import math

print("Hello World");
# 遇到逗号，在拼接字符串的时候自动添加空格
print("Hello", "World");
print("200+100=", 200 + 100);
# -----------------------------------
#name = input();
#print(name);
# -----------------------------------
# 数据类型
a = 33;
print("a的数据类型%s"%(type(a)));
a = 33.3;
b = float(a);
print("b的数据类型%s,值%s"%(type(b), b));
a = "33";
print("a的数据类型%s"%(type(a)));
b = int(a);
print("b的数据类型%s"%(type(b)));
a = 3 > 2;
print("a的数据类型%s, value:%s"%(type(a), a));
# 加减乘除
print((100 + 200 - 20 * 2) / 2);
print(5 // 2);
print(5 % 2);
# and\or\not运算
a = True and False;
print("a的数据类型%s, value:%s"%(type(a), a));
a = True or False;
print("a的数据类型%s, value:%s"%(type(a), a));
a = not True;
print("a的数据类型%s, value:%s"%(type(a), a));
a = None;
print("a的数据类型%s, value:%s"%(type(a), a));
'''
字符串转义
\n ---- 换行
\t ---- 制表符
'''
print("I\'m ok.")
# 字符串替换，尾部追加，不换行
print("a的数据类型%s"%(type(a)), end="----");
print("b的数据类型%s"%(type(b)));
# -----------------------------------
# 常用数学函数
print("向上求整：", math.ceil(b));
print("向下求整：", math.floor(b));
print("四舍五入：", round(b));
a = max(1, 2, 3, 4, 5);
print("最大值：", a);
a = min(1, 2, 3, 4, 5);
print("最小值：", a);
# -----------------------------------
# for 循环
for i in range(10):
    print(i, end="-");
print("");
# 指定范围起始
for i in range(1, 11):
    print(i, end="-");
print("");
# 指定循环间隔
for i in range(1, 11, 2):
    print(i, end="-");
print("");

print(ord('a'));
print(chr(98));
print(b"abd");
print("cdsa".encode("ascii"));
print("cdsa".encode("utf-8"));
print("中文".encode("utf-8"));
print(b'cdsa'.decode("ascii"));
print(b'\xe4\xb8\xad\xe6\x96\x87'.decode("utf-8"));

print(len("aaa"));
print(len("中文"));
print(len("aaa".encode("ascii")));
print(len("中文".encode("utf-8")));

a = 1;
print("a的数据类型%s,值为%d"%(type(a), a));
print("%2d ---- %02d ---- %d%%" % (3, 3, 2));
print('%.2f' % 3.1415926);

print("Hi {0}, 成绩提高了{1:.1f}%".format("小明", 1.234));
print("Hi {0}, 成绩提高了{1}%".format("小明", '%.1f' % 1.234));

list = ["csa", 123, True, "cdsacda"];
print("list: %s, length: %d" %(list, len(list)));
print(list[0] + "--" + list[-1]);
list.append("ffffff");
list.insert(1, "jjjjj");
list.pop();
list.pop(1);
list[1] = "sssss";
print(list);
tuple = ("cdsa", "fdsa");
print("tuple: %s, length: %d" %(tuple, len(tuple)));
tuple = (1,);
print("tuple: %s, length: %d" %(tuple, len(tuple)));
tuple = (1, ["cdsa", "cdsa"]);
tuple[1][0] = "aaaa";
print("tuple: %s, length: %d" %(tuple, len(tuple)));
