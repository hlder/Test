package com.hld.koin.loader_compiler;

import com.google.auto.service.AutoService;
import com.hld.koin.loader.KoinModule;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.hld.koin.loader.KoinModule"})
public class ProxyProcessor extends AbstractProcessor {
    private List<String> listAddModule = new LinkedList<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("=========ProxyProcessor process");
        if (set.isEmpty()) {
            return false;
        }

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(KoinModule.class);


        for (Element element : elements) {
            KoinModule testApt = element.getAnnotation(KoinModule.class);
            if (testApt != null) {
                TypeMirror superClassTypeMirror = element.asType();

                System.out.println("=========ProxyProcessor process2:" + superClassTypeMirror.toString());
                listAddModule.add("listModules.add(new " + superClassTypeMirror.toString() + "().getModules())");


                //1.先拿到class,和包名
                //2.创建class继承上面的class
                //3.重写父类class中的所有方法，并增加调用代理方法的代码
                //4.创建代码

            }
        }

        FieldSpec listModules = FieldSpec.builder(LinkedList.class, "listModules", Modifier.PRIVATE).build();

        MethodSpec.Builder builder = MethodSpec.methodBuilder("getModules")
                .addModifiers(Modifier.PUBLIC)
                .returns(List.class);
        for (String item : listAddModule) {
            builder.addStatement(item);
        }
        MethodSpec beyond = builder
                .addStatement("return listModules")
                .build();
        TypeSpec hello = TypeSpec.classBuilder("HelloWorld")
                .addMethod(beyond)
                .addField(listModules)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", hello)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
