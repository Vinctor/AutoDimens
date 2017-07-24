# AutoDimens  ![Download](https://api.bintray.com/packages/xcht1209/maven/autoDimens/images/download.svg)
布局适配  自动生成&lt;values-heightxwidth>的插件

## 预览
图片中,依次为

1.sony S39h(854x480)

2.魅蓝1(1280x768)

3.LG G3(2560x1440  2K屏幕)

4.模拟器Nexus5(1920x1080,demo中的基准分辨率)

![](screenshot/multi.jpg)![](screenshot/nexus5.jpg)

## 使用

基于这篇[文章](http://blog.csdn.net/lmj623565791/article/details/45460089)编写,通过对基准设计文件的分辨率 进行适当的一定比例的倍增或倍减

通过```gralde插件```的方式自动生成values文件 进行适配

1.[(demo)](https://github.com/Vinctor/AutoDimens/blob/master/build.gradle)在项目根目录中```build.gradle```文件中添加

>classpath: classpath "com.vinctor:autoDimens:x.y.z"

 ![Download](https://api.bintray.com/packages/xcht1209/maven/autoDimens/images/download.svg)
 
2.在```applicaion```module中添加

>apply plugin: 'autodimens'


 

