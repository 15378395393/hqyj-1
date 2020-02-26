import turtle;

# ---- 绘制矩形 ----
def drawRectangle():
    # 设置画布大小
    turtle.screensize(800, 600, "green");
    t = turtle.Pen();
    # 设置笔刷宽度
    t.width(1);
    for i in range(100):
        # 设置笔刷颜色
        t.pencolor("red" if (i % 2 == 0) else "blue");
        # 向前
        t.forward(i);
        # 左转
        t.left(90);
    turtle.done();
# drawRectangle();

# ---- 绘制太阳花 ----
def drawTaiyanghua():
    # 设置画布大小
    turtle.screensize(800, 600, "green");
    t = turtle.Pen();
    t.width(1);
    t.pencolor("red");
    t.fillcolor("yellow");
    # 填充颜色
    t.begin_fill();
    for i in range(50):
        t.forward(200);
        t.left(170);
    t.end_fill();
    turtle.done();
# drawTaiyanghua();

# ---- 绘制五角星 ----
def drawWujiaoxing():
    # 设置画布大小
    turtle.screensize(800, 600, "green");
    # 设置画笔相关属性
    t = turtle.Pen();
    t.pensize(4);
    t.pencolor("red");
    t.fillcolor("yellow");
    t.begin_fill();
    # 设置画笔轨迹
    for i in range(5):
        t.forward(200);
        t.left(144);
    t.end_fill();

    # 提起笔移动，不绘制图形，用于另起一个地方绘制
    t.penup();
    t.goto(-150, -120);
    t.color("violet");
    t.write("Wellcome to china.", font=('Arial', 40, 'normal'));

    # 保持画布不消失
    turtle.done();
# drawWujiaoxing();

# ---- 绘制玫瑰花 ----
def drawFlower():
    # 设置画布大小颜色
    turtle.screensize(800, 600, "white");

    # 画笔
    t = turtle.Pen();
    t.speed(5);
    # 画笔起始位置
    t.penup();
    t.left(90);
    t.fd(200);
    t.pendown();
    t.right(90);

    # 花蕊
    t.fillcolor("red");
    t.begin_fill();
    t.circle(10, 180);
    t.circle(25, 110);
    t.left(50);
    t.circle(60, 45);
    t.circle(20, 170);
    t.right(24);
    t.fd(30);
    t.left(10);
    t.circle(30, 110);
    t.fd(20);
    t.left(40);
    t.circle(90, 70);
    t.circle(30, 150);
    t.right(30);
    t.fd(15);
    t.circle(80, 90);
    t.left(15);
    t.fd(45);
    t.right(165);
    t.fd(20);
    t.left(155);
    t.circle(150, 80);
    t.left(50);
    t.circle(150, 90);
    t.end_fill();

    # 花瓣1
    t.left(150);
    t.circle(-90, 70);
    t.left(20);
    t.circle(75, 105);
    t.setheading(60);
    t.circle(80, 98);
    t.circle(-90, 40);

    # 花瓣2
    t.left(180);
    t.circle(90, 40);
    t.circle(-80, 98);
    t.setheading(-83);

    # 叶子1
    t.fd(30);
    t.left(90);
    t.fd(25);
    t.left(45);
    t.fillcolor("green");
    t.begin_fill();
    t.circle(-80, 90);
    t.right(90);
    t.circle(-80, 90);
    t.end_fill();
    t.right(135);
    t.fd(60);
    t.left(180);
    t.fd(85);
    t.left(90);
    t.fd(80);

    # 叶子2
    t.right(90);
    t.right(45);
    t.fillcolor("green");
    t.begin_fill();
    t.circle(80, 90);
    t.left(90);
    t.circle(80, 90);
    t.end_fill();
    t.left(135);
    t.fd(60);
    t.left(180);
    t.fd(60);
    t.right(90);
    t.circle(200, 60);

    turtle.done();
drawFlower();

# https://blog.csdn.net/secret_lee/article/details/80487739
# https://segmentfault.com/a/1190000017867847