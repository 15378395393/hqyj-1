#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
import asyncio, www.orm.Orm;
from www.orm.Models import User;

async def entityTest(loop):
    # 如果不加yield from，只是创建了协程，没有真正的调用
    await www.orm.Orm.createPool(loop, user="root", password="root", db="python_blog");
    user = User(name="admin", email="admin1@163.com", password="111111", image="about:blank");
    # await user.save();
    users = await User.findAll();
    for user in users:
        print(user);
    number = await User.findNumber("id");
    print(number);
    id = "001583558544013416b355a808a498f8ea8343594795d40000";
    user = await User.find(id);
    print(user);
    user.__setattr__("name", "HymanHu1");
    await user.update();
    # user.__setattr__("id", "0015834594686206ab5ffbd08fe427fb995f2a8279cb756000");
    # await user.remove();

loop = asyncio.get_event_loop();
loop.run_until_complete(entityTest(loop));
loop.close();