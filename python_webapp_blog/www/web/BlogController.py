#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
'Url handlers'
import re, json;
from www.web.WebHandler import *;
from www.orm.Models import *;
from www.common.CustomException import *;

# 私有常量
_RE_EMAIL = re.compile(r'^[a-z0-9\.\-\_]+\@[a-z0-9\-\_]+(\.[a-z0-9\-\_]+){1,4}$');
_RE_SHA1 = re.compile(r'^[0-9a-f]{40}$');

# ---- 用户注册模块 ----
@get('/register')
def register():
    return {
        '__template__': 'register.html'
    };

@post("/api/user")
async def registerUserApi(*, email, name, password):
    if not name or not name.strip():
        raise APIValueError("name");
    if not email or not email.strip() or not _RE_EMAIL.match(email.strip()):
        raise APIValueError("email", "Invalid email.");
    if not password or not password.strip() or not _RE_SHA1.match(password.strip()):
        raise APIValueError("password", "Invalid password.");
    users = await User.findAll("email=?", [email]);
    if len(users) > 0:
        raise APIError('register:failed', 'email', 'Email is already in use.');
    userId = nextId();
    sha1Password = "%s:%s" % (userId, password);
    password = hashlib.sha1(sha1Password.encode("utf-8")).hexdigest();
    image = "http://www.gravatar.com/avatar/%s?d=mm&s=120" \
            % hashlib.md5(email.encode('utf-8')).hexdigest();
    user = User(id=userId, email=email.strip(), password=password, name=name.strip(), image = image);
    await user.save();
    r = web.Response();
    r.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True);
    user.password = '******';
    r.content_type = 'application/json';
    r.body = json.dumps(user, ensure_ascii=False).encode('utf-8');
    return r;

# ---- 登录模块 ----
@get('/signin')
def signin():
    return {
        '__template__': 'signin.html'
    };

@post('/api/authenticate')
async def authenticate(*, email, password):
    if not email:
        raise APIValueError('email', 'Invalid email.');
    if not password:
        raise APIValueError('passwd', 'Invalid password.');
    users = await User.findAll('email=?', [email]);
    if len(users) == 0:
        raise APIValueError('email', 'Email not exist.');
    user = users[0];
    # check password:
    sha1 = hashlib.sha1();
    sha1.update(user.id.encode('utf-8'));
    sha1.update(b':');
    sha1.update(password.encode('utf-8'));
    if user.password != sha1.hexdigest():
        raise APIValueError('passwd', 'Invalid password.');
    # authenticate ok, set cookie:
    r = web.Response();
    r.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True);
    user.password = '******';
    r.content_type = 'application/json';
    r.body = json.dumps(user, ensure_ascii=False).encode('utf-8');
    return r;

@get('/signout')
def signout(request):
    referer = request.headers.get('Referer');
    r = web.HTTPFound(referer or '/');
    r.set_cookie(COOKIE_NAME, '-deleted-', max_age=0, httponly=True);
    LOGGER.info('user signed out.');
    return r;

# ---- 主页Blogs ----
@get("/")
async def blogs(request):
    # test data
    summary = "Balabalabala...";
    content = "一个IO操作阻塞了当前线程，导致其他代码无法执行，" \
              "所以我们必须使用多线程或者多进程来并发执行代码，" \
              "为多个用户服务，每个用户都会分配一个线程，<br/>" \
              "现代操作系统对IO操作已经做了巨大的改进，最大的特点就是支持异步IO。";
    blogs = [
        Blog(id='1', name='Test Blog1', content=content, summary=summary, created_date=time.time() - 120),
        Blog(id='2', name='Test Blog2', content=content, summary=summary, created_date=time.time() - 3600),
        Blog(id='3', name='Test Blog3', content=content, summary=summary, created_date=time.time() - 7200),
        Blog(id='3', name='Test Blog4', content=content, summary=summary, created_date=time.time() - 704800)
    ];
    return {
        '__template__': 'blogs.html',
        'blogs': blogs
    };

# ---- 测试模块 ----
@get('/index')
async def index(request):
    users = await User.findAll();
    return {
        '__template__': 'index.html',
        'users': users
    };

@get("/api/users")
async def getUsersApi():
    users = await User.findAll();
    for user in users:
        user.password = "******";
    return dict(users=users);