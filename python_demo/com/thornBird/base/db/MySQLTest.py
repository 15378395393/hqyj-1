# -*- coding: utf-8 -*-
import mysql.connector;

def mySQLTest():
    # 创建数据库连接
    conn = mysql.connector.connect(user="root", password="root", database="test");

    # 增删改操作
    cursor = conn.cursor();
    cursor.execute('DROP TABLE IF EXISTS `user`;');
    cursor.execute('create table user (id varchar(20) primary key, name varchar(20))');
    # 插入一行记录，注意MySQL的占位符是%s:
    cursor.execute('insert into user (id, name) values (%s, %s)', ['1', 'HymanHu']);
    cursor.execute('insert into user (id, name) values (%s, %s)', ['2', 'Jianghu']);
    print(cursor.rowcount);
    conn.commit();
    cursor.close();

    # 查询操作
    cursor = conn.cursor();
    # cursor.execute('select * from user where id = %s', ('1',));
    cursor.execute('select * from user');
    values = cursor.fetchall()
    print(values);
    cursor.close();

    # 关闭连接
    conn.close();
mySQLTest();