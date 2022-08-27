package com.hld.koinloader

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import org.gradle.api.Project

class KoinLoaderTransform extends Transform {
    private Project project

    private ClassPool classPool

    private String outFilePath = null

    KoinLoaderTransform(Project project) {
        this.project = project
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        if (project.plugins.withType(AppPlugin)) { // 是application项目,需要加载基础jar包
//            classPool = ClassPool.default
            classPool = new ClassPool(true)
            project.android.bootClasspath.each {
                classPool.appendClassPath(it.absolutePath)
            }
        }

        // 1.拿到输入
        transformInvocation.inputs.each {
            copyDirs(transformInvocation, it) // 这里复制我们的源代码
            copyJars(transformInvocation, it) // 这里复制所有jar包
        }

        if (project.plugins.withType(AppPlugin)) { // 是application项目
            println "==================app项目"
            list.each {
                println "========list:" + it
            }
            addClass()
        }
    }

    private void addClass() {
        if (!project.plugins.withType(AppPlugin)) { // 不是application项目，不管
            return
        }
        if (outFilePath == null) {
            return
        }
        CtClass ctClass = classPool.makeClass("com.hld.koin.loader.KoinLoaderStaticReflect")
        ctClass.defrost()
        CtMethod methodLoadByReflect = CtNewMethod.make("\n" +
                "    private static java.util.List loadByReflect(String providerClsStr) {\n" +
                "        try {\n" +
                "            Class cls = Class.forName(providerClsStr);\n" +
                "            java.lang.reflect.Method method = cls.getDeclaredMethod(\"getModules\",null);\n" +
                "            Object object = method.invoke(cls.newInstance(),null);\n" +
                "            if (object instanceof java.util.List) {\n" +
                "                return (java.util.List) object;\n" +
                "            }\n" +
                "        } catch (ClassNotFoundException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (java.lang.reflect.InvocationTargetException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (NoSuchMethodException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (IllegalAccessException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (InstantiationException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        return null;\n" +
                "    }", ctClass)
        ctClass.addMethod(methodLoadByReflect)

        StringBuilder sb = new StringBuilder()
        sb.append("java.util.List list=new java.util.LinkedList();\n")
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i)
            sb.append("java.util.List tempList$i = loadByReflect(\"$item\");")
            sb.append("if(tempList$i != null) {")
            sb.append("list.containsAll(tempList$i);\n")
            sb.append("}")
        }
        sb.append("return list;\n")
        CtMethod methodLoad = CtNewMethod.make(
                "public static java.util.List load(){\n" +
                        sb.toString() +
                        "}",
                ctClass
        )
        println "======outFilePath:" + outFilePath
        ctClass.addMethod(methodLoad)
        ctClass.writeFile(outFilePath)
        ctClass.detach()
    }

    /**
     * 查询包名为com.hld.koin.loader.providers的class
     */
    private void findFileDir(File file, String dirPath, String outDirPath) {
        if (file.isDirectory()) { // 如果是文件夹，继续遍历
            file.listFiles().each {
                findFileDir(it, dirPath, outDirPath)
            }
        } else { // 不是文件夹，是文件，则检查
            checkOneFile(file, dirPath, outDirPath)
        }
    }

    private static List<String> list = new LinkedList<>()
    /**
     * 检查文件的包名是不是com.hld.koin.loader.providers的class
     */
    private void checkOneFile(File file, String dirPath, String outDirPath) {
        String tempPath = file.absolutePath
        if (!tempPath.endsWith(".class")) { // 不是class文件，不管
            return
        }
        // 删除末尾文件名
        tempPath = tempPath.replace("\\" + file.getName(), "")
                .replace("/" + file.getName(), "")
        // 把\转为.
        tempPath = tempPath.replace("\\", ".")
                .replace("/", ".")
        println("------path:" + tempPath)
        if (tempPath.endsWith("com.hld.koin.loader.providers")) { // 判断是否是这个包名，如果是，表示需要处理
            String className = file.getName().replace(".class", "")
            list.add("com.hld.koin.loader.providers." + className)
        }
    }

    private void copyDirs(TransformInvocation transformInvocation, TransformInput transformInput) {
        transformInput.directoryInputs.each { // 文件夹
            classPool.insertClassPath(it.file.absolutePath)
            // 2.查询输出的文件夹
            def dest = transformInvocation.outputProvider.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.DIRECTORY
            )

            outFilePath = dest.absolutePath
            findFileDir(it.file, it.file.absolutePath, dest.absolutePath)
            // 3.文件copy，进入下一个步骤
            FileUtils.copyDirectory(it.file, dest)
        }
    }

    private void copyJars(TransformInvocation transformInvocation, TransformInput transformInput) {
        transformInput.jarInputs.each { // jar包
            // 2.查询输出的文件夹
            def dest = transformInvocation.outputProvider.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.JAR
            )
            // 3.文件copy，进入下一个步骤
            FileUtils.copyFile(it.file, dest)
        }
    }

    @Override
    String getName() {
        return "KoinLoaderTransform"
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
