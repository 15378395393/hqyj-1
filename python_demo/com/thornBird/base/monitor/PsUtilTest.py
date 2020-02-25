import psutil;

# ---- cpu ----
print(psutil.cpu_count()); # cpu逻辑数量
print(psutil.cpu_count(logical=False)); # cpu物理数量
print(psutil.cpu_times()); # cpu用户/系统/空闲……
# cpu使用率
# for i in range(10):
#     print(psutil.cpu_percent(interval=1, percpu=True));

# ---- memory ----
print(psutil.virtual_memory()); # 物理内存信息
print(psutil.swap_memory()); # 交换内存信息

# ---- disk ----
print(psutil.disk_partitions()); # 磁盘分区信息
print(psutil.disk_usage("d:")); # 磁盘使用情况
print(psutil.disk_io_counters()); # 磁盘io

# ---- net ----
print(psutil.net_io_counters()); # 获取网络读写字节数/包个数
print(psutil.net_if_addrs()); # 获取网络接口信息
print(psutil.net_if_stats()); # 获取网络接口状态
print(psutil.net_connections()); # 获取当前网络连接信息

# ---- Process ----
print(psutil.pids()); # 获取所有进程ID
print(psutil.Process(5240).create_time()); # 进程创建时间
print(psutil.Process(5240).status()); # 进程状态
print(psutil.Process(5240).username()); # 进程用户名
print(psutil.Process(5240).name()); # 进程名称
print(psutil.Process(5240).exe()); # 进程exe路径
print(psutil.Process(5240).cwd()); # 进程工作目录
print(psutil.Process(5240).cmdline()); # 进程启动命令行
print(psutil.Process(5240).ppid()); # 父进程id
print(psutil.Process(5240).parent()); # 父进程
print(psutil.Process(5240).children()); # 子进程
# print(psutil.Process(5240).terminal()); # 进程终端
print(psutil.Process(5240).cpu_times()); # 进程使用cpu时间
print(psutil.Process(5240).memory_info()); # 进程使用内存信息
print(psutil.Process(5240).open_files()); # 进程打开的文件
print(psutil.Process(5240).connections()); # 进程相关网络连接
print(psutil.Process(5240).num_threads()); # 进程的线程数量
print(psutil.Process(5240).threads()); # 所有线程信息
print(psutil.Process(5240).environ()); # 进程环境变量
# psutil.Process(5240).terminate(); # 结束进程

# ---- test ----
# print(psutil.test()); # test函数，模拟出ps命令的效果