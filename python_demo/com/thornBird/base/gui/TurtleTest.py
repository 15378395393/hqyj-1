import turtle;

# ---- 绘制长方形 ----
def drawRectangle():
    t = turtle.Pen();
    for i in range(100):
        t.forward(i);
        t.left(90);
drawRectangle();