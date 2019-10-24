package com.example.apt_process;

import com.example.apt_lib.ColorFactory;
import com.example.apt_lib.IColor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * create by cy
 * time : 2019/10/15
 * version : 1.0
 * Features : apt实现工厂模式自动生成
 */
@AutoService(Processor.class)
public class ColorFactoryProcessor extends AbstractProcessor {

    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(ColorFactory.class.getCanonicalName());
        return annotationSet;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "into create factory");
        ArrayList<Item> items = new ArrayList<>();
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(ColorFactory.class)) {
            TypeElement typeElement = (TypeElement) annotatedElement;

            ColorFactory annotation = annotatedElement.getAnnotation(ColorFactory.class);
            String canonicalName = typeElement.getQualifiedName().toString();
            Item item = new Item();
            item.clazz = canonicalName;
            item.id = annotation.id();
            items.add(item);
        }

        if (items.isEmpty()) return false;

        // 自动生成代码
        MethodSpec.Builder getColor = MethodSpec.methodBuilder("getColor")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(IColor.class);

        for (Item item : items) {
            getColor.beginControlFlow("if ($S.equals(id))", item.id)
                    .addStatement("return new $L()", item.clazz)
                    .endControlFlow();
        }

        getColor.addStatement("return null");

        JavaFile javaFile = JavaFile.builder("com.example.apttext",
                TypeSpec.classBuilder("ColorFactory")
                        .addMethod(getColor.build()).build()).build();

        mMessager.printMessage(Diagnostic.Kind.NOTE, javaFile.toString());


        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    private class Item {
        String clazz;
        String id;
    }
}
