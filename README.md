# MechDancer Lib

[![Build Status](https://www.travis-ci.org/MechDancer/mechdancerlib.svg?branch=master)](https://www.travis-ci.org/MechDancer/mechdancerlib)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f1b33196cf94481b1065151df1f4040)](https://www.codacy.com/app/berberman/mechdancerlib?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MechDancer/mechdancerlib&amp;utm_campaign=Badge_Grade)

mechdancerlib is a FTC robot library written by Kotlin. It provides structurize device tree of the robot, including the encapsulation and dispatching, which make program easier to use.

#### Chinese introduction

由 Kotlin 语言编写的 FTC 机器人程序库。

对于 FTC 比赛而言，FIRST 官方提供的库虽然易用（脱离了嵌入式开发范畴），但有些过于简单。在比赛程序的编写上，往往不能优雅的解决一些常用的需求。为了解决该问题，我们换了一种思路，重新定义了从单个设备到机器人整体。我们尽力保证库的独立性，使其不仅应用于 FTC 赛事机器人程序编写。不过为了在 FTC 程序编写中本库拆封即用，我们依赖了 *RobotCore* 中 `HardwareDevice` 和 `OpMode`。

## Getting started

### Add to your project

Find latest version in [releases](https://github.com/MechDancer/mechdancerlib/releases) or clone and build this project, you will get `mechdancerlib.aar` aar file and `mechdancerlib-sources.jar` sources jar in `/main/build/libs`, add them into `libs` folder in your *ftc_app* project. Afterwards, append: 

```groovy
implementation(name: 'mechdancerlib', ext: 'aar')
```
to `build.release.gradle` which lie in each module.

### Enable class filter and op mode register

If you want to use op mode auto register and class filter, something you have to do further are that:

* Find code snippet in `/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/interal/FtcRobotControllerActivity`:
```java
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		....
		
		ClassManagerFactory.registerFilters();
         // You should add ↓
		ClassManager.getInstance().registerFilter(MechDancerOpModeRegister.INSTANCE);
		ClassManager.getInstance().registerFilter(ClassFilterProvider.INSTANCE); 
		ClassManagerFactory.processAllClasses();
		cfgFileMgr = new RobotConfigFileManager(this);
		
        ....
	}
```
​	Then add `ClassManager.getInstance().registerFilter(MechDancerOpModeRegister.INSTANCE);` as above.
​    
* Find code snippet in the `FtcRobotControllerActivity` mentioned above:
```java
    protected OpModeRegister createOpModeRegister() {
        return new FtcOpModeRegister();
  }
```
​	Then replace it with:
```java
    protected OpModeRegister createOpModeRegister() {
		return MechDancerOpModeRegister.INSTANCE::register;
	}
```

## Documentation

For design documentation, please see [here](https://www.mechdancer.org/Documentation/mechdancerlib/).

## Contribution

No expect......