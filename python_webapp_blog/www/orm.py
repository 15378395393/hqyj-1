#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, aiomysql, logging;

# log sql 语句
def logSql(sql, args):
    logging.debug("Sql: %s, Args: %s" % (sql, args));

class StandardError(Exception):
    def __init__(self, *args):
        self.args = args;

# 创建连接池
async def createPool(loop, **kw):
    logging.info("Create database connection pool...");
    global __pool;
    __pool = await aiomysql.create_pool(
        host=kw.get("host", "localhost"),
        port=kw.get("port", 3306),
        user=kw.get("user"),
        password=kw.get("password"),
        db=kw.get("db"),
        charSet=kw.get("charSet", "utf8"),
        autoCommit=kw.get("autoCommit", True),
        maxSize=kw.get("maxSize", 10),
        minSize=kw.get("minSize", 1),
        loop=loop
    );

'''
select方法
sql ---- 查询sql语句
args ---- 查询参数
size ---- 查询结果集大小
'''
async def select(sql, args, size=None):
    logSql(sql, args);
    # 引用全局变量__pool
    global __pool;
    # 从连接池中得到连接，是asyncio.coroutine类型
    async with __pool.get() as conn:
        # 从连接中获得游标，是asyncio.coroutine类型
        async with conn.cursor(aiomysql.DictCursor) as cursor:
            # 执行sql语句
            await cursor.execute(sql.replace("?", "%s"), args or ());
            # 得到结果集
            if size:
                rs = await cursor.fetchmany(size);
            else:
                rs = await cursor.fetchall();
            logging.debug("Rows returned: %s" % len(rs));
            return rs;

# 增删改操作
async def execute(sql, args, autoCommit=True):
    logSql(sql, args);
    global __pool;
    async with __pool.get() as conn:
        # 如果不是自动提交事务，则需要手动开启
        if not autoCommit:
            await conn.begin();
        try:
            async with conn.cursor(aiomysql.DictCursor) as cursor:
                await cursor.execute(sql.replace("?", "%s"), args or ());
                affected = cursor.rowcount;
                if not autoCommit:
                    conn.commit();
        except BaseException as e:
            if not autoCommit:
                conn.rollback();
            raise;
        return affected;

'''
定义列类
columnName ---- 列名
columnType ---- 列类型
primaryKey ---- 是否主键
defaultValue ---- 默认值
'''
class Field(object):
    def __init__(self, columnName, columnType, primaryKey, defaultValue):
        self.columnNmae = columnName;
        self.columnType = columnType;
        self.primaryKey = primaryKey;
        self.defaultValue = defaultValue;
    def __str__(self):
        return "<%s, %s:%s>" % (self.__class__.__name__, self.columnType, self.columnNmae);
    # __repr__ 类似于__str__，用于调试
    __repr__ = __str__;
class StringField(Field):
    def __init__(self, columnName=None, columnType="varchar(100)", primaryKey=False, defaultValue=None):
        super().__init__(self, columnName, columnType, primaryKey, defaultValue);
class IntegerField(Field):
    def __init__(self, columnName=None, primaryKey=False, defaultValue=0):
        super().__init__(self, columnName, "bigint", primaryKey, defaultValue);
class FloatField(Field):
    def __init__(self, columnName=None, primaryKey=False, defaultValue=0.0):
        super().__init__(self, columnName, "real", primaryKey, defaultValue);
class TextField(Field):
    def __init__(self, columnName=None, defaultValue=None):
        super().__init__(self, columnName, "text", False, defaultValue);
class BooleanField(Field):
    def __init__(self, columnName=None, defaultValue=False):
        super().__init__(self, columnName, "boolean", False, defaultValue);

class ModelMetaclass(type):
    def __new__(typ, className, parentClassList, classAttrs):
        if className == "Model":
            return type.__new__(typ, className, parentClassList, classAttrs);
        # 获取表名，先从model属性中查找__table__，没有则用model的名字
        tableName = classAttrs.get("__table__", None) or className;
        logging.debug("Model: %s, Table: %s" % (className, tableName));

        # 从classAttrs中找到model主键、属性，build mappings
        mappings = dict();
        # 主键列
        primaryKey = None;
        # 非主键列列表
        fields = [];
        for key, value in classAttrs.items():
            if isinstance(value, Field):
                logging.info("  Found mapping %s ----> %s" % (key, value));
                mappings[key] = value;
                if value.primaryKey:
                    if primaryKey:
                        raise StandardError("Duplicate primary key for field: %s" % key);
                        primaryKey = key;
                    else:
                        fields.append(key);
        if not primaryKey:
            raise StandardError("Primary key not found.");

        # 移除classAttrs中已经装载到mappings的属性
        for key in mappings.keys():
            classAttrs.pop(key);



