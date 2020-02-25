import chardet;

# ---- chardet检测编码 ----
# 参数书bytes类型
print(chardet.detect(b'hello world')); # ascii
print(chardet.detect(u'你好世界'.encode())); # utf-8
print(chardet.detect('离离原上草，一岁一枯荣'.encode('gbk'))); # GB2312
print(chardet.detect( '最新の主要ニュース'.encode('euc-jp'))); # EUC-JP
