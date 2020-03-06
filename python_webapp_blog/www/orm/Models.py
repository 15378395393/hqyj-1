#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import time, uuid;
from www.orm.Orm import Model,StringField, FloatField, TextField, BooleanField;

# 利用时间和uuid生成primary key
def nextId():
    return "%015d%s000" % (int(time.time() * 1000), uuid.uuid4().hex);

# 注意属性命名方式，如果和数据库字段不对应，在orm中还得做转换
class User(Model):
    __table__ = "b_user";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    email = StringField(columnType="varchar(50)");
    password = StringField(columnType="varchar(50)");
    name = StringField(columnType="varchar(50)");
    admin = BooleanField();
    image = StringField(columnType="varchar(500)");
    created_date = FloatField(columnName="created_date", defaultValue=time.time());

class Blog(Model):
    __table__ = "b_blog";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    user_id = StringField(columnName="user_id", columnType="varchar(50)");
    user_name = StringField(columnName="user_name", columnType="varchar(50)");
    user_image = StringField(columnName="user_image", columnType="varchar(50)");
    name = StringField(columnType="varchar(50)");
    summary = StringField(columnType="varchar(200)");
    content = TextField();
    created_date = FloatField(columnName="created_date", defaultValue=time.time());

class Comment(Model):
    __table__ = "b_comment";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    blog_id = StringField(columnName="blog_id", columnType="varchar(50)");
    user_id = StringField(columnName="user_id", columnType="varchar(50)");
    user_name = StringField(columnName="user_name", columnType="varchar(50)");
    user_image = StringField(columnName="user_image", columnType="varchar(50)");
    content = TextField();
    created_date = FloatField(columnName="created_date", defaultValue=time.time());