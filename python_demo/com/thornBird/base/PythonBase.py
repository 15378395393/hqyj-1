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
print("a的数据类型%s"%(type(a)));
a = "33";
print("a的数据类型%s"%(type(a)));
b = int(a);
print("b的数据类型%s"%(type(b)));
a = "33.34";
b = float(a);
# 字符串替换并换行
print("b的数据类型%s,值%s"%(type(b), b));
# 字符串替换，尾部追加，不换行
print("a的数据类型%s"%(type(a)), end="----");
print("b的数据类型%s"%(type(b)));
# -----------------------------------
# 常用数学函数
print("向上求整：", math.ceil(b));
print("向下求整：", math.floor(b));
print("四舍五入：", round(b));