# acovid-19
Android动态化库，简单，无反射调用，使用系统公开API实现。

目前只支持Fragment。

## 原理

使用AndroidX + Navigation库和Fragment实现，Activity的支持将在后期版本开发。

动态化加载过程：

1、使用算定义的FragmentFactory实现如何加载Fragment的逻辑

2、acovid-19实现了Fragment加载后使用对应动态模块Context的构建逻辑

### 如何使用

第一步：您的Fragment继承自Covid19Fragment

第二步：继承Covid19NavHostFragment实现自己的NavHostFragment，主要是为了运行时查找Fragment所在的模块和动态加载，运行时使用正确的Context。

### 关于起名

由于是在covid-19期间在家开发，也希望这个库可以有covid-19一样的影响力^_^。

### 有问题可加微信

![weichat](./res/weichat.jpeg)



