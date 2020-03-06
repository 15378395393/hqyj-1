#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import os, time;
import logging,logging.config;
from logging.handlers import RotatingFileHandler;

# 设置变量
fileFormat = "PythonLog_" + time.strftime("%Y%m%d", time.localtime(time.time()));
logFormat = logging.Formatter("%(asctime)s - %(filename)s[line:%(lineno)d] - %(levelname)s: %(message)s");
logPath = "/log";
logLevel = logging.DEBUG;

# 获得logger
def getLogger():
    logger = logging.getLogger();
    logger.setLevel(logLevel);
    # 创建一个 stream handler，输出到控制台
    logConsoleHandler = logging.StreamHandler();
    logConsoleHandler.setLevel(logLevel);
    logConsoleHandler.setFormatter(logFormat);
    logger.addHandler(logConsoleHandler);
    # 创建一个 file handler，输出到文件
    logFile = os.path.abspath(logPath) + "/" + fileFormat + ".log";
    # logFileHandler = logging.FileHandler(logFile, mode="w");
    logFileHandler = RotatingFileHandler(logFile,maxBytes=100*1024*1024,backupCount=10);
    logFileHandler.setLevel(logLevel);
    logFileHandler.setFormatter(logFormat);
    logger.addHandler(logFileHandler);
    return logger;

# 创建全局logger对象，命名为LOGGER
LOGGER = getLogger();