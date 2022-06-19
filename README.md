**该工程补充说明

该工程包括了APT（运行时编译）、SPI（服务提供方接口）、Transform、ASM、Aspectj

1、Gradle的兼容性bug 参考
[https://blog.csdn.net/allenli0413/article/details/90602402]()

`com.android.tools.build:gradle:3.3.2 以下`

`https\://services.gradle.org/distributions/gradle-4.10.1-all.zip 以下`

这个问题已经解决：[https://www.jianshu.com/p/098a9573f2c0]()

主要是因为Gradle4.x 升级到Gradle5.x 的区别

AutoService 主要用于生成META-INF.services下的文件

2、javaPoet例子

*com.example.apt_process.javaPoetText.class*

3、一个自动生成工厂模式的例子（apt的使用例子）

*ColorFactory*

4、一个打印类信息的例子 

*LogProcessor*

5、一个类似黄油刀的例子

*ViewProcessor*

参考资料：

[https://blog.csdn.net/qq_20521573/article/details/82321755]()

[https://blog.csdn.net/xx326664162/article/details/68490059]()

*PackageElement* 表示一个包程序元素。提供对有关包及其成员的信息的访问。

*ExecutableElement*  表示某个类或接口的方法、构造方法或初始化程序（静态或实例），包括注释类型元素。

*TypeElement*  表示一个类或接口程序元素。提供对有关类型及其成员的信息的访问。注意，枚举类型是一种类，而注解类型是一种接口。

*VariableElement*  表示一个字段、enum 常量、方法或构造方法参数、局部变量或异常参数。

6、Transform例子

增量构建和构建缓存：增量是上次的基础上的差量；缓存是保存成zip的形式，当作不止是上次的编译的复用（一般是变化不大的）

···

···

7、ASM [https://asm.ow2.io/]()

生成ASM代码插入代码 工具类 ASM Bytecode Viewer，注意有个坑，会提示找不到tools.jar，解决方案就直接去javac目录下，对应的class文件操作

8、AspectJ（app1目录下）

### 通配符：

* 代表任意数量的字符

.. 代表包结构下，任意子包数量；参数下，任意参数

+ 继承关系

例子：
```
    // MainActivity(继承BaseActivity)

    @Pointcut("execution(* android.com.aspectj.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *..MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *.*.*.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..))")
    public void activityOnCreatePointcut() {

    }
```

### call和execution

#### call

是在调用处插入代码

```
public class Activity1 extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在这里插入
        new TextAspectjCall().call();
    }
}
```

#### execution

在对应的方法处插入方法

```
public class TextAspectjCall {

    public void call() {
        // 在这里插入
    }
}
```

### within 和 whitincode

#### whitin 指定某个类 配合execution 使用

```
@Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..)) && within(android.com.aspectj.MainActivity)")
```

在所有继承BaseActivity中指定MainActivity，如果想排除即用 !(备注：可能该库仅支持&& 不支持||)

```
@Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..)) && !within(android.com.aspectj.MainActivity)")
```

#### whitincode 指定某个方法不插入 配合call 使用

```
@Pointcut("call(* android.com.aspectj.TextAspectjCall.call()) && withincode(* android.com.aspectj.MainActivity.onCreate(..))")
```

底层原理： transform

除Around以外，均为在相关位置插入代码，Around在相关类，新建一个新的方法（注意class直接拉入AS不展示该方法）