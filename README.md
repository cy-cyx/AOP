编写于20191012 

1、Gradle的兼容性bug 参考
[https://blog.csdn.net/allenli0413/article/details/90602402]()

`com.android.tools.build:gradle:3.3.2 以下`

`https\://services.gradle.org/distributions/gradle-4.10.1-all.zip 以下`

这个问题已经解决：https://www.jianshu.com/p/098a9573f2c0

主要是因为Gradle4.x 升级到Gradle5.x 的区别

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
