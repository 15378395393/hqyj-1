-- MySQL dump 10.11
--
-- Host: 127.0.0.1    Database: python_blog
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `b_blog`
--

DROP TABLE IF EXISTS `b_blog`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `b_blog` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_image` varchar(500) NOT NULL,
  `name` varchar(50) NOT NULL,
  `summary` varchar(200) NOT NULL,
  `content` mediumtext NOT NULL,
  `created_date` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `b_blog`
--

LOCK TABLES `b_blog` WRITE;
/*!40000 ALTER TABLE `b_blog` DISABLE KEYS */;
INSERT INTO `b_blog` VALUES ('0015838951741242b5604b95ad34e98b544569d67406840000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','万般滋味，皆是生活','万般滋味，皆是生活……','有人住高楼，有人在深沟，有人光万丈，有人一身锈，有人靠一张脸，一部片子能挣1000万，有人仅仅活着，就已经用尽全身力气，可是他们，面对命运的青脸獠牙，依然慨当以慷，微笑面对；\n每一个生命都有自己的打开方式，他无疑用的是最棒的一种！即使肩有重担，一条腿也要大步向前！',1583894716.68609),('0015838952251739e7d583ac4ed47bebdb590d8d7753b96000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','论跳楼，我只服俄罗斯这帮愣头青','论跳楼，我只服俄罗斯这帮愣头青……','即便已经做好了攻略，穆勒去俄罗斯旅行的时候还是被这帮子斯拉夫人吓尿了。崩溃的穆勒随后在REDDIT上发帖询问，俄罗斯人到底有几条命？有俄罗斯留学生回复穆勒，“俄罗斯人不死，他们只会在行为艺术中慢慢老去。”\n穆勒说，在回家的前一天晚上，几个穿阿迪达斯的光头毛子邀请他去屋顶来一场俄罗斯的传统游戏，他们一再强调这玩意儿很刺激，是俄罗斯小屁孩最喜欢玩的游戏，游戏的内容很简单，在脑中勾勒出彼得大帝的双头鹰徽记，重力势能会帮你完成接下来的步骤，数秒之后，你就能在雪堆里找回沙皇的荣光。那几个毛子不等穆勒思考就跳楼了，随后他们还让他赶快下来，穆勒觉得这是来自地狱的呼唤。“当时我就报警了，可接线员说现在没空，他们正在抓捕一名开着坦克跑UBER的男子。”我那时才回想起我的祖父临终前曾经告诫过我，一定要小心莫斯科的第一场雪。\nREDDIT上称呼该游戏为Russianjumping，据说当年只有俄国贵族才能玩。这个运动就像跳水，迈出去的勇气不值一提，关键是得看雪堆能被炸出多大的坑，能模拟出通古斯大爆炸的男子是每位社交圈女士的心头肉。其实Russian jumping不止是个游戏，在一些毛子眼中，它还是俄国艺术的基石。俄罗斯人的奇特艺术感都是在生死之间领悟出来的，俄罗斯转盘的死亡率太高，学好了Russian jumping，人人都是普希金。',1583894716.68609),('0015838952807527a6b4baeacaf4cddb9f146a695be7316000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','138亿年宇宙历史压缩到一年','138亿年宇宙历史压缩到一年……','如果138亿年的宇宙历史压缩到一年，人类出现在哪个月？5月？9月？著名科普作者、天文学家卡尔﹒萨根在《伊甸园的龙》中提出的宇宙年历，会让你惊掉下巴，让我们一起来看一看，宇宙、地球和人类的大事件，都出现在什么时刻吧！\n在这个日历里，日历的每一秒相当于438年，日历上的一小时相当于158万年，日历上的一天相当于3780万年，在这个宇宙年历里，一个人活到80岁，宇宙才过去了0.18秒；\n1月1日，大爆炸 ---- 5月1日，银河系诞生 ---- 9月9日，太阳系诞生 ---- 9月14日，地球诞生；\n9月25日左右，地球产生第一个生命 ---- 10月2日，地球上已知最古老的岩石 ---- 10月9日，地球最古老的化石（细菌和蓝藻） ---- 11月1日左右，有性生殖诞生（微生物） ---- 11月12日，最古老的光合植物化石 ---- 11月15日，真核生物（具有细胞核核的第一批细胞）开始繁荣兴旺 ---- 12月14日，多细胞生物诞生 ---- 12月17日，寒武纪大爆发，显生宙古生代寒武纪开始；\n12月18日，海洋浮游生物出现，三叶虫繁荣的时代 ---- 12月19日，第一批鱼类；第一批脊椎动物 ---- 12月20日，第一批维管植物 ---- 12月21日，海洋动物登陆 ---- 12月22日，第一批两栖动物；第一批有翼昆虫 ---- 12月23日，第一批树，第一批爬行动物 ---- 12月24日，恐龙出现 ---- 12月26日，第一批哺乳动物 ---- 12月27日，第一批鸟 ---- 12月28日，第一批花，恐龙灭绝 ---- 12月29日，第一批灵长动物 ---- 12月30日，灵长目的额叶开始进化得更高级，人科物种出现',1583894716.68609),('0015838953184772b7a730cf70140a3b9692255075217dd000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','程序员','程序猿……','老婆给老公打电话：下班顺路买是个包子，如果看到卖西瓜的，买一个。当晚老公捧着一个包子进了门，老婆怒道：你怎么就买了一个包子？老公答道：因为我看到了卖西瓜的；\n对程序员这个下班看日出，结婚看缘分的苦逼群体来说，他们的私生活真的很少，他们的直是你想象不到的，他们的心中只有产品经理；\n某程序员去交友网站找女朋友，朋友问他找的怎么样？他说，找到一个bug；\n一流的程序员靠数学，二流的程序员靠算法，末端的程序员靠百度，低端看高端就是黑魔法；\n对于程序员来说、没老婆不悲催，悲催的是，没老婆控制台还不停的提示你：Error:could not find the object。\nMy life, like ship, sink in the Java.\n程序员 ---- 女的拿来当男的用，男的拿来当畜生用！\nCan not find file wife.dll, don\'t worry, reload the house.exe or car.exe, this issure maybe be resolved.\n女程序员择偶：SELECT * FROM Man WHERE Marry = false and Gay = false and House = true and Car = true and Attribute in (handsome, gentry, magnanimous, qualities, knowledge, tender, care, romantic, lively, cute, foster baby) and Age between(24, 40) Order by bank savings desc\n程序员三大错觉：这个应该是没有bug的；这个今晚应该可以写完；这个应该满足设计师的需求了。',1583894716.68609),('001583897087550d0f29c79a393472e8655b9ee29123b37000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','一个女作家的海上日记','一个女作家的海上日记……','一位女作家，在船上写了几篇日记；\n第一天，船长十分热情带我参观甲板；\n第二天，船长带我到驾驶室去参观，我感觉船长人十分好；\n第三天，他带我到他的房间对我做出那种要求，这简直不象他这种人做出的；\n第四天，他更强烈地要求我，还说我不同意就把船弄沉；\n第五天，我救了四百多人；',1583895368.63038),('001583897131585a2251c6193b3419c9da6f1ae682d5399000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','男女婚恋商店','男女婚恋商店……','一家经营女性婚姻服务的商店，女人可以从这里找到心仪的男性，但只能选一次，还不能回头。\n一个女人到这里寻找老公；\n一楼写着：这里的男人有工作。女人看也不看就上了第二层楼；\n二楼写着：这里的男人有工作而且酷爱小孩。女人上了三楼；\n三楼写着：这里的男人有工作而且酷爱小孩，还很帅。哇！她叹道，但仍逼迫本身往上爬；\n四楼：这里的男人有工作而且热爱小孩，令人窒息的帅，还会帮忙做家务。哇！饶了我吧！女人叫道，我快站不住脚了！接着她仍然爬上了五楼；\n五楼：这里的男人有工作而且酷爱小孩，令人窒息的帅，还会帮忙做家务，更有着强烈的浪漫情怀。女人简直想留在这一层楼，但仍抱着满腹等待走向最高一层；\n第六楼呈现了一面宏大的电子告示板，上面写道：你是这层楼的第123456789位访客，这里不存在任何男人，这层楼的存在只是为了证明女人有多么不可能取悦。谢谢光临…… \n不久，一家专营男性婚姻服务的店在街对面开张，经营方法与前者一模一样。\n第一层的女人长得漂亮；\n第二层的女人长得漂亮并且有钱……\n结果，二层以上，第三层至六层的楼层从来没有男人上去过……',1583895368.63038),('0015839022106710113651b60aa4d10a38b21bdc9c08b7c000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','中美护照','中美护照……','美：不管你身处何方，美国政府和军队都是你强大的后盾。 ---- 出去了有人欺负你，招呼一声咱修理他。\n中：请严格遵守当地法律，并尊重那里的风俗习惯。 ---- 出去了老实点，听人家话，少给老子惹麻烦',1583902030.2294),('001583902307781e832383dfeae487da23625529cd45c49000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','易容术','易容术111……','燃炉沉香浸入清梦温存\n配碗药汤饮后梦死醉生\n蘸鲜血红尘\n走丝线飞针\n在你脸上描绘另一个人\n缝入皮相后的过往前尘\n身不由己淋漓着爱恨\n等容光焕新\n如世间新生\n却成为别人生命的延伸\n世上所有完美\n只惊艳在某一瞬\n好掩盖表皮之下\n参差细密的伤痕\n去祭奠每分每寸\n幻象代替的本真\n和睁开眼时已抽离的灵魂\n下次是哪张面皮\n与你相逢却不认\n当某个虚假容颜\n死去又重生\n为他人易容的人\n喜新厌旧得狠\n越轻易改变的\n越难以相珍\n凭这无双妙手技艺绝伦\n可任我倾一生痴狂妄尊\n背天理常伦\n罔顾相由心生\n篡改所谓宿命撰写的剧本\n世上所有完美\n只惊艳在某一瞬\n好掩盖表皮之下\n参差细密的伤痕\n去祭奠每分每寸\n幻象代替的本真\n和睁开眼时已抽离的灵魂\n下次是哪张面皮\n与你相逢却不认\n当某个虚假容颜\n死去又重生\n为他人易容的人\n喜新厌旧得狠\n越轻易改变的\n越难以相珍\n世上所有完美\n只惊艳在某一瞬\n好掩盖表皮之下\n参差细密的伤痕\n去祭奠每分每寸\n幻象代替的本真\n和睁开眼时已抽离的灵魂\n下次再相逢\n与你却不相认\n当虚假容颜\n死去又重生\n为他人易容的人\n喜新厌旧得狠\n越轻易改变的\n越难以相珍',1583902030.2294);
/*!40000 ALTER TABLE `b_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `b_comment`
--

DROP TABLE IF EXISTS `b_comment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `b_comment` (
  `id` varchar(50) NOT NULL,
  `blog_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_image` varchar(500) NOT NULL,
  `content` mediumtext NOT NULL,
  `created_date` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `b_comment`
--

LOCK TABLES `b_comment` WRITE;
/*!40000 ALTER TABLE `b_comment` DISABLE KEYS */;
INSERT INTO `b_comment` VALUES ('001583907413497dac333f39c004f16840e12d1ed3b509f000','001583902307781e832383dfeae487da23625529cd45c49000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','想起梦里跌跌撞撞',1583907384.71166),('001583907453833fa16e635a94b4074a5f19caad9e27c47000','001583902307781e832383dfeae487da23625529cd45c49000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','想起你的背影摇摇晃晃……',1583907384.71166),('001583907482164f903cb9307b44a1182656d4bcc4943bf000','0015839022106710113651b60aa4d10a38b21bdc9c08b7c000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','我还能说什么……',1583907384.71166),('0015839088742508d935376384f44e58dd0387b21db63db000','001583897131585a2251c6193b3419c9da6f1ae682d5399000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','有些像你的脸微红的模样……',1583908806.71499),('001583908904560940cb627fecf4c39aebfb8749746440b000','001583897131585a2251c6193b3419c9da6f1ae682d5399000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','习惯漫无目的四处流浪……',1583908806.71499),('001583911397192bf9b879f8efd4d7d814ed00c8a0931d4000','001583902307781e832383dfeae487da23625529cd45c49000','001583807210907c426a0c94fb542759e4ce50316619fed000','HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120','我透过木窗望着月色昏黄，如你最后一样温柔目光……',1583911303.03477);
/*!40000 ALTER TABLE `b_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `b_user`
--

DROP TABLE IF EXISTS `b_user`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `b_user` (
  `id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` varchar(500) NOT NULL,
  `created_date` double NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_email` (`email`),
  KEY `idx_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `b_user`
--

LOCK TABLES `b_user` WRITE;
/*!40000 ALTER TABLE `b_user` DISABLE KEYS */;
INSERT INTO `b_user` VALUES ('0015833978412550e57979ce50f45f19d6a0bb29eaee193000','admin@163.com','111111',0,'admin','about:blank',1583397840.23962),('001583398360340419b257cc212405189b3ff093d23fbce000','admin1@163.com','111111',0,'HymanHu1','about:blank',1583398359.31231),('001583807210907c426a0c94fb542759e4ce50316619fed000','hujiangyx@163.com','657c1a13e07dbe92e0ea4c6fd328bb2207012c57',1,'HymanHu','http://www.gravatar.com/avatar/2d478699374e7ec50390c7de30f76254?d=mm&s=120',1583807194.3309),('00158380732366456eb2b2d678549a2a686e4ac2de6878b000','hujiangyx1@163.com','084f4338acbf66c2700e7aa8a35cbefb29e55294',0,'HymanHu1','http://www.gravatar.com/avatar/3c837debab8d7e6f87cca40a08d09fea?d=mm&s=120',1583807194.3309),('001583807381155be7a43b8f32042b5837dbc669d871fa5000','hujiangyx2@163.com','cd9aae04c25ac4ff7fe23e09e550b9016f348d66',0,'HymanHu2','http://www.gravatar.com/avatar/e4e4aeae0202fff534e94a3193dec588?d=mm&s=120',1583807357.52323),('001583817742425be9b86e1f26544168d86af0e95cf8889000','hujiangyx3@163.com','4c8cf0530821c604fc80ed3f7e08844f17b6eaf9',0,'HymanHu3','http://www.gravatar.com/avatar/a1eb1f5139d4b09ef0e8ac589b352134?d=mm&s=120',1583817617.04104);
/*!40000 ALTER TABLE `b_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-11  8:37:35
