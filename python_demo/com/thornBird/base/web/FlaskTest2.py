# -*- coding: utf-8 -*-
from flask import Flask, request,render_template;

app = Flask(__name__);

# 主页
@app.route("/", methods=["GET", "POST"])
def home():
    return render_template("wellcome.html");

# 登录页
@app.route("/signin", methods=["GET"])
def lognin():
    return render_template("lognin.html");

# 登录
@app.route("/signin", methods=["POST"])
def signin():
    userName = request.form["userName"];
    password = request.form["password"];
    if userName == "admin" and password == "admin":
        return render_template("index.html", userName = userName, pageList = range(10));
    else:
        return render_template("lognin.html", message = "Bad user name or password", userName = userName);

# 启动web server，默认端口5000，访问http://127.0.0.1:5000/
if __name__ == "__main__":
    app.run(
        host = "127.0.0.1",
        port = 8080,
        debug = True
    );