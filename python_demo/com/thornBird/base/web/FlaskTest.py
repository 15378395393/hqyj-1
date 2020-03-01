# -*- coding: utf-8 -*-
from flask import Flask, request, render_template;

# 实例化Flask类
app = Flask(__name__);

# 主页
@app.route("/", methods=["GET", "POST"])
def home():
    return "<h1>Home</h1>";

# 登录页
@app.route("/signin", methods=["GET"])
def signinPage():
    return '''
    <form action="/signin" method="post">
    <p><input name="userName"></p>
    <p><input name="password" type="password"></p>
    <p><button type="submit">Sign In</button></p>
    </form>
    ''';

# 登录
@app.route("/signin", methods=["POST"])
def signin():
    userName = request.form["userName"];
    password = request.form["password"];
    if userName == "admin" and password == "admin":
        return '<h3>Hello, admin!</h3>';
    else:
        return "<h3>Bad username or password.</h3>";

# 启动web server，默认端口5000，访问http://127.0.0.1:5000/
if __name__ == "__main__":
    app.run(
        host="127.0.0.1",
        port=8080,
        debug=True
    );

