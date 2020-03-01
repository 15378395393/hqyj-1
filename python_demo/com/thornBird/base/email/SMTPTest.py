# -*- coding: utf-8 -*-
from email import encoders;
from email.header import Header;
from email.mime.text import MIMEText;
from email.mime.multipart import MIMEMultipart;
from email.mime.base import MIMEBase;
from email.utils import parseaddr,formataddr;
import smtplib;

# format like: Mr Green <green@example.com>
# return =?utf-8?q?Mr_Green?= <green@example.com>
def formatAddr(x):
    name, addr = parseaddr(x);
    return formataddr((Header(name, "utf-8").encode(), addr));
print(formatAddr("Mr Green <green@example.com>"));

# ---- 文本邮件 ----
def sendErmail():
    # 输入发送人信息、收件人地址
    # fromAddress = input("From:");
    # password = input("Password:");
    # toAddress = input("To:");
    # smtpServer = input("SMTP Server:");
    fromAddress = "898899721@qq.com";
    # 在官网设置开启POP3/SMTP等服务，在此输入第三方授权码
    password = "ibqfqoxrfmfubecf";
    toAddress = "hujiangyx@163.com,hujiang_cd@hqyj.com";
    smtpServer = "smtp.qq.com";

    '''
    参数1：邮件内容；
    参数2：subtype，plain标识纯文本 ---- html网页 ---- alternative混合；
    参数3：编码集；
    :return:
    '''
    # 发送纯文本邮件
    # msg = MIMEText("Hello HymanHu, 万物生长，山河无恙，佳期如许！", "plain", "utf-8");
    # 发送html邮件
    # msg = MIMEText('<html><body><h1>Hello</h1>' +
    #                '<p>send by <a href="http://www.python.org">Python</a>...</p>' +
    #                '</body></html>', 'html', 'utf-8');
    # 发送附件邮件，注意subtype类型
    msg = MIMEMultipart("alternative");
    # 添加纯文本和html，并在html中嵌入图片
    msg.attach(MIMEText("Hello HymanHu, 万物生长，山河无恙，佳期如许！", "plain", "utf-8"));
    msg.attach(MIMEText('<html><body><h1>Hello</h1>' +
                        '<p><img src="cid:0"></p>' +
                        '</body></html>', 'html', 'utf-8'))
    # 添加附件
    with open("D:\\temp\\test.png", "rb") as f:
        # 设置附件的文件名、类型等:
        mime = MIMEBase("image", "png", filename="test.png");
        # 添加信息头
        mime.add_header('Content-Disposition', 'attachment', filename='test.png')
        mime.add_header('Content-ID', '<0>')
        mime.add_header('X-Attachment-Id', '0')
        # 把附件的内容读进来:
        mime.set_payload(f.read());
        # 用Base64编码:
        encoders.encode_base64(mime);
        # 添加到MIMEMultipart:
        msg.attach(mime);

    # 设置from、to、subject等信息
    msg["From"] = formatAddr("HymanHu <%s>" % fromAddress);
    msg["To"] = formatAddr("JiangHu <%s>" % toAddress);
    msg["Subject"] = Header("来自HymanHu的问候", "utf-8").encode();
    print(msg);

    server = smtplib.SMTP(smtpServer, 25);
    # 加密SMTP
    # server.starttls();
    # 打印出和SMTP服务器交互的所有信息
    server.set_debuglevel(1);
    server.login(fromAddress, password);
    # 发送单人
    # server.sendmail(fromAddress, [toAddress], msg.as_string());
    # 发送多人
    server.sendmail(fromAddress, toAddress.split(","), msg.as_string());
    server.quit();
sendErmail();
