package com.apkfuns.hiresguard

import com.tencent.mm.resourceproguard.InputParam
import com.tencent.mm.resourceproguard.Main;
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction;

/**
 * Created by pengwei on 16/7/26.
 */
public class HiResGuardTask extends DefaultTask {
    def HiResGuardExtension configuration
    def android
    @Optional
    def output
    @Optional
    def variant;

    HiResGuardTask() {
        description = 'Assemble Resource Proguard APK'
        group = 'hiresguard'
        outputs.upToDateWhen { false }
        configuration = project.resGuardOption
        android = project.extensions.android
    }

    @TaskAction
    def resGuard() {
        def String absPath = output.outputFile.getAbsolutePath()
        def signConfig = variant.apkVariantData.variantConfiguration.signingConfig
        def String packageName = variant.apkVariantData.variantConfiguration.applicationId
        def ExecutorExtension sevenZip = project.extensions.findByName("sevenzip") as ExecutorExtension
        ArrayList<String> whiteListFullName = new ArrayList<>();
        configuration.whiteList.each { res ->
            if (res.startsWith("R")) {
                whiteListFullName.add(packageName + "." + res)
            } else {
                whiteListFullName.add(res)
            }
        }
        InputParam.Builder builder = new InputParam.Builder()
                .setMappingFile(configuration.mappingFile)
                .setWhiteList(whiteListFullName)
                .setUse7zip(configuration.use7zip)
                .setMetaName(configuration.metaName)
                .setKeepRoot(configuration.keepRoot)
                .setCompressFilePattern(configuration.compressFilePattern)
                .setZipAlign(getZipAlignPath())
                .setSevenZipPath(sevenZip.path)
                .setOutBuilder(useFolder(output.outputFile))
                .setApkPath(absPath)
                .setUseSign(configuration.useSign);
        if (configuration.useSign) {
            if (signConfig == null) {
                throw new GradleException("can't found signConfig for build")
            }
            builder.setSignFile(signConfig.storeFile)
                    .setKeypass(signConfig.keyPassword)
                    .setStorealias(signConfig.keyAlias)
                    .setStorepass(signConfig.storePassword)
        }
        Main.gradleRun(builder.create())
        println("123*********************************")
    }

    static def useFolder(file) {
        //remove .apk from filename
        def fileName = file.name[0..-5]
        return "${file.parent}/AndResGuard123_${fileName}/"
    }

    def getZipAlignPath() {
        return "${android.getSdkDirectory().getAbsolutePath()}/build-tools/${android.buildToolsVersion}/zipalign"
    }
}
