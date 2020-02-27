# -*- coding: utf-8 -*-
from email.parser import Parser;
from email.utils import parseaddr;
from email.header import decode_header;
import poplib;

# 邮件的内容也是str，还需要检测编码，否则，非UTF-8编码的邮件都无法正常显示
def guessCharset(msg):
    charset = msg.get_charset();
    if charset is None:
        contentType = msg.get('Content-Type', '').lower();
        pos = contentType.find('charset=');
        if pos >= 0:
            charset = contentType[pos + 8:].strip();
    return charset;

# 邮件的Subject或者Email中包含的名字都是经过编码后的str，要正常显示，就必须decode
def decodeStr(s):
    # 返回一个list，有Cc、Bcc多个邮件地址，我们只取第一个
    value, charset = decode_header(s)[0];
    if charset:
        value = value.decode(charset);
    return value;

# 解析邮件返回信息，indent用于缩进显示:
def printInfo(msg, indent=0):
    if indent == 0:
        for header in ['From', 'To', 'Subject']:
            value = msg.get(header, '');
            if value:
                if header == 'Subject':
                    value = decodeStr(value);
                else:
                    hdr, addr = parseaddr(value);
                    name = decodeStr(hdr);
                    value = u'%s <%s>' % (name, addr);
            print('%s%s: %s' % ('  ' * indent, header, value));
    if (msg.is_multipart()):
        parts = msg.get_payload();
        for n, part in enumerate(parts):
            print('%spart %s' % ('  ' * indent, n));
            print('%s--------------------' % ('  ' * indent));
            printInfo(part, indent + 1);
    else:
        content_type = msg.get_content_type();
        if content_type=='text/plain' or content_type=='text/html':
            content = msg.get_payload(decode=True);
            charset = guessCharset(msg);
            if charset:
                content = content.decode(charset);
            print('%sText: %s' % ('  ' * indent, content + '...'));
        else:
            print('%sAttachment: %s' % ('  ' * indent, content_type));

def receiveEmail():
    # emailAddress = input("Email Address:");
    # password = input("Password:");
    # popServer = input("POP Server:");
    emailAddress = "898899721@qq.com";
    password = "ibqfqoxrfmfubecf";
    popServer = "pop.qq.com";

    # 连接到pop3服务器
    server = poplib.POP3(popServer);
    # 打开调试信息
    server.set_debuglevel(1);
    # 打印pop3服务器欢迎信息
    print(server.getwelcome().decode("utf-8"));
    # 验证登录用户
    server.user(emailAddress);
    server.pass_(password);

    # 打印邮箱邮件数量、占空间大小
    print("MailBox stat: MailCount(%s), MailSize(%s)" % server.stat());
    # 返回tuple，共三个元素，第二个元素是所有邮件的编号list
    # 类似：(b'+OK', [b'1 1787', b'2 1824'...], 327)
    resp, mails, octets = server.list();
    print(mails);
    # 获取最新的一封邮件，索引从1开始
    # mailLines存储了邮件每一行
    index = len(mails);
    resp, mailLines, octets = server.retr(index);
    mailContent = b'\r\n'.join(mailLines).decode("utf-8");
    # 将邮件内容解析为Message对象，有可能是MIMEMultipart
    mail = Parser().parsestr(mailContent);
    printInfo(mail);
    # 可以根据邮件索引号直接从服务器删除邮件:
    # server.dele(index)

    # 关闭连接
    server.quit();
receiveEmail();