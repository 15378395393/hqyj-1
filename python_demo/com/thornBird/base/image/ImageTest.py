# -*- coding: utf-8 -*-
from PIL import Image, ImageFilter;

# 打开图片文件
img = Image.open("test.jpg");
# 获得图片尺寸
w, h = img.size;
print("Image size is %s * %s" % (w, h));

# ---- 图片尺寸放缩 ----
img.thumbnail((w//2, h//2));
# 把放缩后的图片用png保存
img.save("test2.jpg", "png");

# ---- 使用模糊滤镜 ----
img2 = img.filter(ImageFilter.BLUR);
img2.save("test3.jpg", "jpeg");

# ---- 生成随机字母图片 ----
from PIL import Image, ImageDraw, ImageFont, ImageFilter;
import random;

# 随机字母
def randomChar():
    return chr(random.randint(65, 90));
# 随机颜色一
def randomColor1():
    return (random.randint(64, 255), random.randint(64, 255), random.randint(64, 255));
# 随机颜色二
def randomColor2():
    return (random.randint(32, 127), random.randint(32, 127), random.randint(32, 127));
# 随机图片
def randomImage():
    width = 60 * 4;
    heigh = 60;
    # 创建image对象
    image = Image.new("RGB", (width, heigh), (255, 255, 255));
    # 创建font对象
    font = ImageFont.truetype("Arial.ttf", 36);
    # 创建draw对象
    draw = ImageDraw.Draw(image);
    # 填充每个像素
    for x in range(width):
        for y in range(heigh):
            draw.point((x, y), fill=randomColor1());
    # 填充文字
    for i in range(4):
        draw.text((60 * i + 10, 10), randomChar(), font=font, fill=randomColor2());
    # 使用模糊滤镜
    # image = image.filter(ImageFilter.BLUR);
    # 输出图片
    image.save("random.jpg", "jpeg");
randomImage();
