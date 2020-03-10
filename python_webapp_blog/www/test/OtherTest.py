#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import os, time;

def dirList(dir):
    dir = os.path.abspath(dir);
    for x in os.listdir(dir):
        sonDir = os.path.join(dir, x);
        if os.path.isdir(sonDir):
            dirList(sonDir);
        else:
            print(sonDir);

# dirList(os.path.abspath(os.path.dirname(__file__)));

print(time.time());