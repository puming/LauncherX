# LauncherX
在Google 5.0Launcher3的基础上做个性化修改，支持android studio单独编译。
添加了支持左屏和单双层动态切换的功能。

## 集成了ReactNative页面

#### 调试：

> $ cd Launcherx/react/

如果没有node_modules
> $ yarn install 或　npm install

> $ react-native start

#### 手动执行link的功能：

settings.gradle中添加
```
include ':react-native-video'
project(':react-native-video').projectDir = new File(rootProject.projectDir, 'react/node_modules/react-native-video/android')
```
module/build.gradle中添加
```
compile project(':react-native-video')
```

#### 打包：
> $ cd Launcherx/react/

> $ react-native bundle --platform android --dev false --entry-file index.js --bundle-output ../left/src/main/assets/index.android.bundle --assets-dest ../left/src/main/res/
