#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import sys, subprocess;
from watchdog.observers import Observer;
from watchdog.events import FileSystemEventHandler;
from www.common.GlobalLog import *;
from www.common.BlogCommons import *;

# 记录监控日志
def log(s):
    LOGGER.debug('[Monitor] %s' % s);

# 监控.py文件发生变更
class MyFileSystemEventHander(FileSystemEventHandler):
    def __init__(self, fn):
        super(MyFileSystemEventHander, self).__init__();
        self.restart = fn;

    def on_any_event(self, event):
        if event.src_path.endswith('.py'):
            log('Python source file changed: %s' % event.src_path);
            self.restart();

# command = ['echo', 'ok'];
command = ['echo', 'ok'];
process = None;

# 杀掉进程
def kill_process():
    global process
    if process:
        log('Kill process [%s]...' % process.pid);
        process.kill();
        process.wait();
        log('Process ended with code %s.' % process.returncode);
        process = None;

# 开始进程
def start_process():
    global process, command;
    log('Start process %s...' % ' '.join(command));
    process = subprocess.Popen(command, stdin=sys.stdin, stdout=sys.stdout, stderr=sys.stderr);

# 重启进程
def restart_process():
    kill_process();
    start_process();

# 开始监控
def start_watch(path, callback):
    observer = Observer();
    observer.schedule(MyFileSystemEventHander(restart_process), path, recursive=True);
    observer.start();
    log('Watching directory %s...' % path);
    start_process();
    try:
        while True:
            time.sleep(0.5);
    except KeyboardInterrupt:
        observer.stop();
    observer.join();

if __name__ == '__main__':
    print(getPath("python_webapp_blog", "www"));
    argv = sys.argv[1:];
    if not argv:
        print('Usage: ./pymonitor your-script.py');
        exit(0);
    if argv[0] != 'python3':
        argv.insert(0, 'python3');
    command = argv;
    # path = os.path.abspath('.');
    path = getPath("python_webapp_blog", "www");
    start_watch(path, None);
