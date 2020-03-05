#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import aiomysql;
from www.globalLog import LOGGER;

# log sql 语句
def logSql(sql, args):
    LOGGER.debug("Sql: %s, Args: %s" % (sql, args));

class StandardError(Exception):
    def __init__(self, *args):
        self.args = args;

# 创建连接池
async def createPool(loop, **kw):
    LOGGER.info("Create database connection pool...");
    global __pool;
    __pool = await aiomysql.create_pool(
        host=kw.get("host", "localhost"),
        port=kw.get("port", 3306),
        user=kw.get("user"),
        password=kw.get("password"),
        db=kw.get("db"),
        charset=kw.get("charset", "utf8"),
        autocommit=kw.get("autocommit", True),
        maxsize=kw.get("maxsize", 10),
        minsize=kw.get("minsize", 1),
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
            LOGGER.debug("Rows returned: %s" % len(rs));
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

# 创建sql语句中的问号字符串
def createArgsString(n):
    l = [];
    for item in range(n):
        l.append("?");
    return ", ".join(l);

'''
定义列类
columnName ---- 列名
columnType ---- 列类型
primaryKey ---- 是否主键
defaultValue ---- 默认值
'''
class Field(object):
    def __init__(self, columnName, columnType, primaryKey, defaultValue):
        self.columnName = columnName;
        self.columnType = columnType;
        self.primaryKey = primaryKey;
        self.defaultValue = defaultValue;
    def __str__(self):
        return "<%s, %s:%s>" % (self.__class__.__name__, self.columnName, self.columnType);
    # __repr__ 类似于__str__，用于调试
    __repr__ = __str__;
class StringField(Field):
    def __init__(self, columnName=None, columnType="varchar(100)", primaryKey=False, defaultValue=None):
        super().__init__(columnName, columnType, primaryKey, defaultValue);
class IntegerField(Field):
    def __init__(self, columnName=None, primaryKey=False, defaultValue=0):
        super().__init__(columnName, "bigint", primaryKey, defaultValue);
class FloatField(Field):
    def __init__(self, columnName=None, primaryKey=False, defaultValue=0.0):
        super().__init__(columnName, "real", primaryKey, defaultValue);
class TextField(Field):
    def __init__(self, columnName=None, defaultValue=None):
        super().__init__(columnName, "text", False, defaultValue);
class BooleanField(Field):
    def __init__(self, columnName=None, defaultValue=False):
        super().__init__(columnName, "boolean", False, defaultValue);

# 创建类模板，Model元类
class ModelMetaclass(type):
    def __new__(typ, className, parentClassList, classAttrs):
        if className == "Model":
            return type.__new__(typ, className, parentClassList, classAttrs);
        # 获取表名，先从model属性中查找__table__，没有则用model的名字
        tableName = classAttrs.get("__table__", None) or className;
        LOGGER.debug("Model: %s, Table: %s" % (className, tableName));

        # 从classAttrs中找到model主键、属性，build mappings
        mappings = dict();
        # 主键列
        primaryKey = None;
        # 考虑到entity属性和列名不一致的情况，用下面两个变量来装载
        # 非主键列列表
        fields = [];
        # 非主键属性名列表
        properties = [];
        for key, value in classAttrs.items():
            if isinstance(value, Field):
                LOGGER.debug("  Found mapping %s ----> %s" % (key, value));
                mappings[key] = value;
                if value.primaryKey:
                    if primaryKey:
                        raise StandardError("Duplicate primary key for field: %s" % key);
                    # primaryKey = key;
                    primaryKey = value.columnName if value.columnName else key;
                else:
                    properties.append(key);
                    # fields.append(key);
                    fields.append(value.columnName if value.columnName else key);
        if not primaryKey:
            raise StandardError("Primary key not found.");

        # 移除classAttrs中已经装载到mappings的属性
        for key in mappings.keys():
            classAttrs.pop(key);

        # 将fields进行包装，每个元素变成'%**'
        escapedFields = list(map(lambda item : '`%s`' % item, fields));

        # 包装元类属性
        classAttrs["__table__"] = tableName; # 表名
        classAttrs["__primary_key__"] = primaryKey; # 主键
        classAttrs["__fields__"] = fields; # 除主键外的列名
        classAttrs["__properties__"] = properties; # 非主键属性名列表
        classAttrs["__mappings__"] = mappings; # 属性和列的映射关系
        # 增删改查语句
        classAttrs["__select__"] = "select `%s`, %s from `%s`" \
            % (primaryKey, ', '.join(escapedFields), tableName);
        classAttrs["__insert__"] = 'insert into `%s` (%s, `%s`) values (%s)' \
            % (tableName, ', '.join(escapedFields), primaryKey, createArgsString(len(escapedFields) + 1));
        classAttrs["__update__"] = "update %s set %s where `%s` = ?" \
            % (tableName, ', '.join(map(lambda f : '`%s`=?' % (mappings.get(f).columnName or f), properties)), primaryKey);
        classAttrs['__delete__'] = "delete from `%s` where `%s`=?" % (tableName, primaryKey);
        return type.__new__(typ, className, parentClassList, classAttrs);

# 创建所有实体类父类Model
class Model(dict, metaclass=ModelMetaclass):
    def __init__(self, **kw):
        super(Model, self).__init__(**kw);
    def __getattr__(self, key):
        try:
            return self[key];
        except KeyError:
            raise AttributeError(r"'Model' object has no attribute '%s'" % key);
    def __setattr__(self, key, value):
        self[key] = value;
    def getValue(self, key):
        return getattr(self, key, None);
    # 获取不到值时，返回默认值
    def getValueOrDefault(self, key):
        value = getattr(self, key, None);
        if value is None:
            field = self.__mappings__[key];
            if field.defaultValue is not None:
                '''
                StringField(columnType="varchar(50)", primaryKey=True, defaultValue=nextId);
                defaultValue()相当于调用nextId(),返回的是字符串
                defaultValue相当于调用nextId，返回的是function nextId，在解析的时候会报错
                '''
                value = field.defaultValue() if callable(field.defaultValue) else field.defaultValue;
                LOGGER.debug('using default value for %s: %s' % (key, str(value)));
                setattr(self, key, value);
        return value;

    # 定义各种常用实体bean操作方法
    @classmethod
    async def findAll(cls, where=None, args=[], **kw):
        sql = [cls.__select__];
        if where:
            sql.append("where");
            sql.append(where);
        orderBy = kw.get("orderBy", None);
        if orderBy:
            sql.append("order by");
            sql.append(orderBy);
        limit = kw.get("limit", None);
        if limit is not None:
            if isinstance(limit, int):
                sql.append("limit");
                args.append(limit);
            elif isinstance(limit, tuple) and len(limit) == 2:
                sql.append("?, ?");
                args.extend(limit);
            else:
                raise ValueError('Invalid limit value: %s' % str(limit));
        rs = await select(" ".join(sql), args);
        return [cls(**r) for r in rs];

    'select count(*)'
    @classmethod
    async def findNumber(cls, selectField, where=None, args=None):
        sql = ['select %s _num_ from `%s`' % (selectField, cls.__table__)];
        if where:
            sql.append('where');
            sql.append(where);
        rs = await select(' '.join(sql), args, 1);
        if len(rs) == 0:
            return None
        return rs[0]['_num_'];

    ' find object by primary key. '
    @classmethod
    async def find(cls, pk):
        rs = await select('%s where `%s`=?' % (cls.__select__, cls.__primary_key__), [pk], 1);
        if len(rs) == 0:
            return None;
        return cls(**rs[0]);

    async def save(self):
        args = list(map(self.getValueOrDefault, self.__properties__));
        args.append(self.getValueOrDefault(self.__primary_key__));
        rows = await execute(self.__insert__, args);
        if rows != 1:
            LOGGER.debug('failed to insert record: affected rows: %s' % rows);

    async def update(self):
        args = list(map(self.getValue, self.__properties__));
        args.append(self.getValue(self.__primary_key__));
        rows = await execute(self.__update__, args);
        if rows != 1:
            LOGGER.warn('failed to update by primary key: affected rows: %s' % rows);

    async def remove(self):
        args = [self.getValue(self.__primary_key__)];
        rows = await execute(self.__delete__, args);
        if rows != 1:
            LOGGER.warn('failed to remove by primary key: affected rows: %s' % rows);


