#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, time, uuid, www.orm;
from www.orm import Model,StringField, IntegerField, FloatField, TextField, BooleanField;

# 利用时间和uuid生成primary key
def nextId():
    return "%015d%s000" % (int(time.time() * 1000), uuid.uuid4().hex);

class User(Model):
    __table__ = "b_user";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    email = StringField(columnType="varchar(50)");
    password = StringField(columnType="varchar(50)");
    name = StringField(columnType="varchar(50)");
    admin = BooleanField();
    image = StringField(columnType="varchar(500)");
    createdDate = FloatField(columnName="created_date", defaultValue=time.time());

class Blog(Model):
    __table__ = "b_blog";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    userId = StringField(columnName="user_id", columnType="varchar(50)");
    userName = StringField(columnName="user_name", columnType="varchar(50)");
    userImage = StringField(columnName="user_image", columnType="varchar(50)");
    name = StringField(columnType="varchar(50)");
    summary = StringField(columnType="varchar(200)");
    content = TextField();
    createdDate = FloatField(columnName="created_date", defaultValue=time.time());

class Comment(Model):
    __table__ = "b_comment";
    id = StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
    blogId = StringField(columnName="blog_id", columnType="varchar(50)");
    userId = StringField(columnName="user_id", columnType="varchar(50)");
    userName = StringField(columnName="user_name", columnType="varchar(50)");
    userImage = StringField(columnName="user_image", columnType="varchar(50)");
    content = TextField();
    createdDate = FloatField(columnName="created_date", defaultValue=time.time());