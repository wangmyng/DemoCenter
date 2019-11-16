# DemoCenter
## 腾讯 Bugly
### 异常上报功能
介绍文档：https://bugly.qq.com/docs/introduction/bugly-introduction/?v=20180709165613

### 应用升级功能
介绍文档：https://bugly.qq.com/docs/introduction/app-upgrade-introduction/?v=20181014122344

可配置弹窗样式、目标人群、弹窗时机、是否强制更新；
### 热更新插件
...
### 符号表插件
...
## onConfigurationChanged
只有在Manifest中配置了configChanges属性才会在相应属性变化时调用这个方法;

想要监听屏幕旋转，需要同时配置 configChangesorientation和screenSize;

重写了此方法后，在Manifest中已监听的属性改变不会导致活动重建，onSaveInstantanceState不会执行;

需要注意，跳转到其他活动等情形导致当前活动被销毁后，onConfigurationChanged方法不会被调用，需要在onStart等时机通过getResources.getConfiguration.orientation == Configuration.xx来判断处理；

参考：https://www.jianshu.com/p/0127fb67516d