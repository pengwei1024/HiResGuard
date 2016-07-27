# HiResGuard
基于[AndResGuard](https://github.com/shwenzhang/AndResGuard)定制, 让适用于更多的场景

### 使用方法
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
applicationVariants.all { variant ->
        variant.assemble.doLast {
            variant.outputs.each { output ->
                resGuardTask.output = output
                resGuardTask.variant = variant
                resGuardTask.execute()
            }
        }

    }
```



