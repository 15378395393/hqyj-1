#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";

# 标准错误
class StandardError(Exception):
    def __init__(self, *args):
        self.args = args;

'''
API标准错误
the base APIError which contains error(required), data(optional) and message(optional).
'''
class APIError(Exception):
    def __init__(self, error, data='', message=''):
        super(APIError, self).__init__(message);
        self.error = error;
        self.data = data;
        self.message = message;

'''
输入值错误
Indicate the input value has error or invalid. The data specifies the error field of input form.
'''
class APIValueError(APIError):
    def __init__(self, field, message=''):
        super(APIValueError, self).__init__('value:invalid', field, message)

'''
资源未找到错误
Indicate the resource was not found. The data specifies the resource name.
'''
class APIResourceNotFoundError(APIError):
    def __init__(self, field, message=''):
        super(APIResourceNotFoundError, self).__init__('value:notfound', field, message);

'''
没有权限错误
Indicate the api has no permission.
'''
class APIPermissionError(APIError):
    def __init__(self, message=''):
        super(APIPermissionError, self).__init__('permission:forbidden', 'permission', message);