package com.hld.koin.loader_compiler;

import com.google.auto.service.AutoService;
import com.hld.koin.loader.KoinLoaderConstant;
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
public class KoinLoaderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            return false;
        }
        List<String> listAddModule = new LinkedList<>();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(KoinModule.class);

        for (Element element : elements) {
            KoinModule testApt = element.getAnnotation(KoinModule.class);
            if (testApt != null) {
                TypeMirror superClassTypeMirror = element.asType();
                listAddModule.add("listModules.addAll(new " + superClassTypeMirror.toString() + "().getModules())");
            }
        }

        FieldSpec listModules = FieldSpec.builder(LinkedList.class, "listModules", Modifier.PRIVATE).build();

        MethodSpec.Builder builder = MethodSpec.methodBuilder("getModules")
                .addModifiers(Modifier.PUBLIC)
                .returns(List.class);
        builder.addStatement("listModules = new LinkedList<org.koin.core.module.Module>()");
        for (String item : listAddModule) {
            builder.addStatement(item);
        }
        MethodSpec beyond = builder
                .addStatement("return listModules")
                .build();

        String packageName = KoinLoaderConstant.KOIN_LOADER_PROVIDER_PACKAGE_NAME;
        String className = "KoinModuleProvider" + hashCode();

        TypeSpec hello = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(beyond)
                .addField(listModules)
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, hello)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
