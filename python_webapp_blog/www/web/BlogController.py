#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
'Url Controller'
import json, markdown2;
from www.web.WebHandler import *;
from www.orm.Models import *;
from www.common.BlogCommons import *;

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
    if not email or not email.strip() or not RE_EMAIL.match(email.strip()):
        raise APIValueError("email", "Invalid email.");
    if not password or not password.strip() or not RE_SHA1.match(password.strip()):
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

# ---- 博客创建模块 ----
@get('/manage/blog')
def manage_create_blog():
    return {
        '__template__': 'managerBlogEdit.html',
        'id': '',
        'action': '/api/blog'
    };

@post('/api/blog')
async def api_create_blog(request, *, name, summary, content):
    check_admin(request);
    if not name or not name.strip():
        raise APIValueError('name', 'name cannot be empty.');
    if not summary or not summary.strip():
        raise APIValueError('summary', 'summary cannot be empty.');
    if not content or not content.strip():
        raise APIValueError('content', 'content cannot be empty.');
    blog = Blog(user_id=request.__user__.id, user_name=request.__user__.name,
                user_image=request.__user__.image, name=name.strip(),
                summary=summary.strip(), content=content.strip());
    await blog.save();
    return blog;

# ---- 博客列表管理模块 ----
@get('/manage/blogs')
def manage_blogs(*, page='1'):
    return {
        '__template__': 'managerBlogs.html',
        'page_index': get_page_index(page)
    };

@get('/api/blogs')
async def api_blogs(*, page='1'):
    page_index = get_page_index(page);
    num = await Blog.findNumber('count(id)');
    p = Page(num, page_index);
    if num == 0:
        return dict(page=p, blogs=());
    blogs = await Blog.findAll(orderBy='created_date desc', limit=(p.offset, p.limit));
    return dict(page=p, blogs=blogs);

# 获取id获取博客接口
@get('/api/blog/{id}')
async def api_get_blog(*, id):
    blog = await Blog.find(id);
    return blog;

# 根据id返回博客页
@get('/blog/{id}')
async def get_blog(id):
    blog = await Blog.find(id);
    comments = await Comment.findAll('blog_id=?', [id], orderBy='created_at desc');
    for c in comments:
        c.html_content = text2html(c.content);
    blog.html_content = markdown2.markdown(blog.content);
    return {
        '__template__': 'blog.html',
        'blog': blog,
        'comments': comments
    };

# 博客列表
@get("/")
async def blogs(request):
    blogs = await Blog.findAll(orderBy='created_date desc');
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