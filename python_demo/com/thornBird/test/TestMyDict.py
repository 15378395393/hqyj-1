import unittest;
from com.thornBird.test.MyDict import MyDict;

class TestMyDict(unittest.TestCase):
    # 在调用每一个测试方法前执行
    def setUp(self):
        print("Before Test");
    # 在调用每一个测试方法后执行
    def tearDown(self):
        print("After Test");
    def testInit(self):
        print("Test 1");
        d = MyDict(a = 1, b = "aaa");
        self.assertEqual(d.a, 1);
        self.assertEqual(d.b, "aaa");
        self.assertTrue(isinstance(d, dict));
    def testDictError(self):
        print("Test 2");
        d = MyDict();
        # 断言错误,d.testKey访问不存在的key时，我们期待抛出AttributeError
        with self.assertRaises(AttributeError):
            value = d.testKey;

# 加上这两行代码，我们就能将该测试文件当作Python脚本运行
if __name__ == '__main__':
    unittest.main();
