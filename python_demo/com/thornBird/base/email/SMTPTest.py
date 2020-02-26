from email.mime.text import MIMEText;
import smtplib;

# ---- 文本邮件 ----
def textErmail():
    '''
    参数1：邮件内容；
    参数2：subtype，plain标识纯文本，'text/plain'；
    参数3：编码集；
    :return:
    '''
    msg = MIMEText("Hello HymanHu, 万物生长，山河无恙，佳期如许！", "plain", "utf-8");

    # 输入发送人信息、收件人地址
    fromAddress = input("From:");
    password = input("Password:");
    toAddress = input("To:");

    server = smtplib.SMTP(fromAddress, 25);
    # 打印出和SMTP服务器交互的所有信息
    server.set_debuglevel(1);
    server.login(fromAddress, password);
    server.sendmail(fromAddress, [toAddress], msg.as_string());
    server.quit();
textErmail();