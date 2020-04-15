# DbViewer
## An Android database real-time viewer on mobile

### 介绍

在我们平时的Android开发中总是会使用到本地sqlite数据库，所以在开发期间对本地数据库进行实时数据的调试和查看是个很重要工作，我们可以通过AndroidStudio的Device File Explorer导出data目录下的.db文件再用navicat等桌面级的数据库查看工具打开，或者是使用AndroidStudio的插件例如SQLScout来帮助我们导入db文件并查看，但这几种方式都各有各的问题，比如无法直接打开我们通过SQLCipher加密过的数据库文件，又或者插件本身需要License，且这些方式都是从开发的角度查看数据库的，测试过程中发现问题也没有办法第一时间直接导出应用里的db文件定位问题，所以这里开源一个非常简单的小工具，可以实时在手机上查看当前应用的所有db文件并可以打开查看所有表以及表中的数据，包括使用SQLCipher加密过的数据库。

### 接入

`debugImplementation 'damiao.hr:dbviewer:0.0.1'`

可选仅在debug时依赖，DBViewer直接模仿了LeakCanary的集成方式，随应用一起安装，独立启一个Activity展示当前应用的数据库信息，全部使用AndroidSDK原生控件，对support或androidx版本无任何要求，可放心集成

### 配置

如果数据库未加密，那么仅添加依赖就可以使用DbViewer了，如果要查看加密数据库，则需要在应用的Manifest中添加对应的密码，例如，数据库的密码是123456，则在Manifest的application标签下添加

`<meta-data android:name="DB_PWD" android:value="MIAO_123456"/>`

DBViewer现在默认假如用户配置了密码，则对当前应用所有加密数据库都统一使用配置的密码打开数据库，若拥有复数个不同密码的数据库，则只能正常打开密码和配置对应的数据库，持有其他密码的数据库则会提示打开失败，在后面的版本中可能会更新对不同的数据库可设置不同的密码。
