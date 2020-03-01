# -*- coding: utf-8 -*-
import requests;

# ---- 读取网页 ----
# 设置请求header内容
# r = requests.get("http://www.baidu.com",headers={'User-Agent':
#     'Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit'});
# 请求中设置cookie
# cs = {'token': '12345', 'status': 'working'}
# r = requests.get(url, cookies=cs)
# 设置请求参数、超时
r = requests.get("http://www.baidu.com", params={'ie': 'utf-8', 'word': 'python'}, timeout=2.5);
print(r.url); # https://www.douban.com/search?q=python&cat=1001
print(r.encoding); # 自动检测编码
print(r.status_code); # 返回状态码
print(r.headers); # headwes信息
print(r.headers['Content-Type']);
print(r.cookies["BDORZ"]); # 获取cookie
print(r.text); # 返回string内容
print(r.content); # 获得bytes对象
r = requests.get("https://yesno.wtf/api");
print(r.json()); # 获取json数据

# ---- 发送post/put等请求 ----
# post/form请求，默认application/x-www-form-urlencoded
# r = requests.post('https://accounts.douban.com/login', data={'form_email': 'abc@example.com', 'form_password': '123456'});
# post/json请求
# params = {'key': 'value'}
# r = requests.post(url, json=params) # 内部自动序列化为JSON
# post，上传文件，务必使用'rb'即二进制模式读取，这样获取的bytes长度才是文件的长度
# upload_files = {'file': open('report.xls', 'rb')}
# r = requests.post(url, files=upload_files)
# put/delete请求
# r = requests.put(url, files=upload_files)