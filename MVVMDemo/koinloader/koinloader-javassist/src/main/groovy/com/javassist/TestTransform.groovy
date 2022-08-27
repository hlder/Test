package com.javassist

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import org.gradle.api.Project

class TestTransform extends Transform {

    private Project project

    TestTransform(Project project) {
        this.project = project
    }

    private String lastDirPath=""

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println "=========================================================transform project:"+project.name+"======================================================================="

        project.android.bootClasspath.each {
            classPool.appendClassPath(it.absolutePath)
        }

        // 1.拿到输入
        transformInvocation.inputs.each {
            it.directoryInputs.each { // 文件夹
                classPool.insertClassPath(it.file.absolutePath)
                // 2.查询输出的文件夹
                def dest = transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY
                )
                lastDirPath = dest.absolutePath
                println("directory==============fileName dest:" + dest)
                findFile(it.file, it.file.absolutePath,dest.absolutePath)

                // 3.文件copy，进入下一个步骤
                FileUtils.copyDirectory(it.file, dest)
            }

            it.jarInputs.each { // jar包
                // 2.查询输出的文件夹
                def dest = transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.JAR
                )
                println("jar==============fileName dest:" + dest)

                // 3.文件copy，进入下一个步骤
                FileUtils.copyFile(it.file, dest)
            }
        }

        updateStatic()
    }

    private void updateStatic(){
        if(!project.plugins.withType(AppPlugin)){ // 不是application项目，不管
            return
        }
        CtClass ctClass =classPool.makeClass("com.test.TestStatic")
        ctClass.defrost()
        CtMethod m = CtNewMethod.make(
                "public static void a(){System.out.println(\"aaaaa\");}",
                ctClass
        )
        println "======lastDirPath:"+lastDirPath
        ctClass.addMethod(m)
        ctClass.writeFile(lastDirPath)
        ctClass.detach()

//        CtClass ctClass =classPool.get("com.hlder.mylibrary.TestStatic")
//        ctClass.defrost()
//        ctClass.getDeclaredMethods().each {
//            println "-----------method:" + it.name
//            it.insertBefore("{System.out.println(\"------------updateStatic------------\");}")
//        }
//        ctClass.writeFile(staticFileDirPath)
//        ctClass.detach()
//        String outFilePath = staticFileAbsolutePath.replace(staticFileDirPath,staticFileOutDirPath)
//        FileUtils.copyFile(new File(staticFileAbsolutePath), new File(outFilePath))
    }

    private void findFile(File file, String dirPath, String outDirPath) {
        if (file.isDirectory()) { // 如果是文件夹，继续遍历
            file.listFiles().each {
                findFile(it, dirPath,outDirPath)
            }
        } else {//如果是file直接处理
            doExeFile(file, dirPath,outDirPath)
        }
    }

    private ClassPool classPool=ClassPool.default
    private String staticFileDirPath = ""
    private String staticFileAbsolutePath = ""
    private String staticFileOutDirPath = ""

    private void doExeFile(File file, String dirPath, String outDirPath) {
        String path = file.absolutePath
        println("------path:"+path)
        if (path.contains("com\\hlder\\mylibrary\\TestStatic.class")) {
            staticFileDirPath = dirPath
            staticFileAbsolutePath = path
            staticFileOutDirPath = outDirPath
        }
    }

    @Override
    String getName() {
        return "TestTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }
}
