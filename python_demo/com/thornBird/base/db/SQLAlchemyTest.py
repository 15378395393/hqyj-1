# -*- coding: utf-8 -*-
from sqlalchemy import Column, String, INTEGER, ForeignKey, create_engine, and_, or_;
from sqlalchemy.orm import sessionmaker, relationship;
from sqlalchemy.ext.declarative import declarative_base;
import random;

# 创建数据库引擎，初始化DBSession
def initDBSession():
    engine = create_engine("mysql+mysqlconnector://root:root@localhost:3306/test");
    DBSession = sessionmaker(bind=engine);
    return DBSession();

# 创建对象基类
Base = declarative_base();
# 定义User实体类
class User(Base):
    # 表名
    __tablename__ = "user";
    # 表结构
    id = Column(INTEGER, primary_key=True);
    name = Column(String(20));

# 创建user
def createUser(user):
    session = initDBSession();
    session.add(user);
    session.commit();
    session.close();
# 查询user list
def queryUsers():
    session = initDBSession();
    users = session.query(User).all();
    session.close();
    for user in users:
        print(user.id, user.name);
    return users;
# 根据条件查询
def queryUser(user):
    session = initDBSession();
    # 条件查询连接and_、or_，返回one()、all()
    users = session.query(User).filter(or_(User.id == user.id, User.name.like("%" + user.name + "%"))).all();
    session.close();
    for user in users:
        print(user.id, user.name);
    return users;

user = User(name="HymanHu" + str(random.randint(0, 1000)));
createUser(user);
queryUsers();
queryUser(User(id=1, name="Hyman"));

class Country(Base):
    __tablename__ = "m_country";
    countryId = Column(INTEGER, primary_key=True, name="country_id");
    countryName = Column(String(45), name="country_name");
    # 一方，绑定类名
    cities = relationship('City');

class City(Base):
    __tablename__ = "m_city";
    cityId = Column(INTEGER, primary_key=True, name="city_id");
    cityName = Column(String(45), name="city_name");
    # 多方，外键绑定表名.列名
    countryId = Column(INTEGER, ForeignKey("m_country.country_id"), name="country_id");

def queryCountry():
    session = initDBSession();
    country = session.query(Country).filter(Country.countryId == 522).one();
    print(country.countryId, country.countryName);
    for city in country.cities:
        print(city.cityId, city.cityName);
    # 加载cities是lazy load，需要在加载完成后关闭Session
    session.close();
    return country;
queryCountry();


