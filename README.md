# HiResGuard
基于[AndResGuard](https://github.com/shwenzhang/AndResGuard)定制, 让适用于更多的场景

### 和AndResGuard区别
基于AndResGuard-core开发，自定义task实现参数自定义，不需要通过执行task来执行，android studio的直接运行也可以执行，
且不会多次编译运行(resguard依赖assemble，且只能压缩release版本)

### 使用方法
使用方法基本和AndResGuard一致，但注意gradle插件`HiResGuardPlugin`不一样，配置项`resGuardOption`也不一样。
```groovy
apply plugin: 'HiResGuardPlugin'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.apkfuns.hiresguard:hiresguard:1.0.0'
    }
}

// 配置属性和AndResGuard一致
resGuardOption {
    mappingFile = null
    use7zip = true
    useSign = true
    keepRoot = false
    whiteList = [
            "R.drawable.ic_launcher",
            "R.string.app_name",
            "R.layout.explorer_*",
            "R.string.explorer_*",
    ]
    compressFilePattern = [
            "*.png",
            "*.jpg",
            "*.jpeg",
            "*.gif",
            "resources.arsc"
    ]
    sevenzip {
        artifact = 'com.tencent.mm:SevenZip:1.1.9'
        //path = "/usr/local/bin/7za"
    }
}

// 使用方法
android {
    applicationVariants.all { variant ->
            variant.assemble.doLast {
                variant.outputs.each { output ->
                    resGuardTask.output = output
                    resGuardTask.variant = variant
                    resGuardTask.execute()
                }
            }
    
        }  
}
```

### 本地调试
上传到本地maven
```
./gradlew hiresguard:uploadArchives
```



