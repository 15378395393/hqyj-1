# -*- coding: utf-8 -*-
from tkinter import *;
import tkinter.messagebox as messagebox;

# ---- hello world ----
class Application(Frame):
    def __init__(self, master=None):
        Frame.__init__(self, master);
        # 将自身加入容器
        self.pack();
        # self.createWidgets1();
        self.createWidgets2();
    # 创建第一套组件
    def createWidgets1(self):
        self.helloLabel = Label(self, text="Hello World");
        self.helloLabel.pack();
        self.quitButton = Button(self, text="Quit", command=self.quit);
        self.quitButton.pack();
    # 创建第二套组件，带输入功能
    def createWidgets2(self):
        self.nameInput = Entry(self);
        self.nameInput.pack();
        self.alertButton = Button(self, text="Hello", command=self.alertMessag);
        self.alertButton.pack();
    # 定义点击button触发的方法
    def alertMessag(self):
        name = self.nameInput.get() or "world";
        messagebox.showinfo("Hello %s" % name);

app = Application();
# 设置窗口标题:
app.master.title("GUI Test");
# 主消息循环:
app.mainloop();