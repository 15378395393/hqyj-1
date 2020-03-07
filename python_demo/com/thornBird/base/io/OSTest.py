import os;

# ---- os ----
print(os.name); # 操作系统，nt(Windows)、posix(Linux、Unix、Mac OS X)
# print(os.uname()); # 操作系统详细信息，不支持Windows系统
print(os.environ); # 获取操作系统环境变量
print(os.environ.get("CLASSPATH")); # 获取环境变量中某个值
print(os.path.isfile(".")); # 判断是否为文件
print(os.path.isdir(".")); # 判断是否为文件夹
print(os.path.abspath(".")); # 查看当前目录绝对路径
separator = "\\" if os.name == "nt" else "/";
projectName = "python_demo" + separator;
curPath = os.path.abspath(os.path.dirname(__file__)); # 获取当前文夹路径
rootPath = curPath[:curPath.find(projectName) + len(projectName)]; # 获取项目根目录
print(curPath);
print(rootPath);

''''
join ---- 文件目录组装
split ---- 文件目录拆分成tuple，最后一个元素是最后级别的目录或文件夹
why ---- 可以正确处理不同操作系统的分隔符，只是对字符串的操作
'''
print(os.path.join("D:/", "testDir"));
print(os.path.split("D:\\projectCode\\hqyj\\python_demo\\com\\thornBird\\base"));
print(os.path.splitext("D:\\sql\\testdb.sql")) # 拆分文件全路径，可以得到文件后缀
print(os.listdir(".")); # 文件夹下所有的文件和目录
print(os.listdir(os.path.split(os.path.abspath("."))[0])); # 查找当前目录同级文件和目录，包含自己
if not os.path.exists("D:/testDir"):
    os.mkdir("D:/testDir"); # 创建文件夹
# os.rmdir("D:/testDir"); # 删除文件夹
if not os.path.exists("D:/testDir/test.txt"):
    # os.mknod("test.txt"); # Windows不支持mknod
    with open("D:/testDir/test.txt", "a", encoding="utf-8") as f:
        f.write("\nHello world\n");
os.rename("D:/testDir/test.txt", "D:/testDir/test1.txt"); # 文件重命名
os.remove("D:/testDir/test1.txt"); # 删除文件