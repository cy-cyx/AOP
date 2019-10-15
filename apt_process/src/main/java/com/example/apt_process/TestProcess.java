package com.example.apt_process;


import com.example.apt_lib.AutoCreat;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class TestProcess extends AbstractProcessor {

    Messager mMessager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(AutoCreat.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.RELEASE_7;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Object[] objects = annotations.toArray();
        Name simpleName = ((TypeElement) objects[0]).getSimpleName();
        mMessager.printMessage(Diagnostic.Kind.NOTE, simpleName);

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AutoCreat.class)) {
            TypeElement annotatedElement1 = (TypeElement) annotatedElement;
            List<? extends Element> enclosedElements = annotatedElement1.getEnclosedElements();
            for (Element a : enclosedElements) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, a.getSimpleName());
                if (a instanceof VariableElement) {
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "changliang");
                } else if (a instanceof ExecutableElement) {
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "fanfa");
                    List<? extends Element> b = a.getEnclosedElements();
                    List<? extends VariableElement> parameters = ((ExecutableElement) a).getParameters();
                    for (VariableElement d : parameters) {
                        mMessager.printMessage(Diagnostic.Kind.NOTE, d.getSimpleName());
                    }
                    for (Element c : b) {
                        mMessager.printMessage(Diagnostic.Kind.NOTE, c.getSimpleName());
                    }
                }
            }
        }

        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, roundEnv.getElementsAnnotatedWith(AutoCreat.class).size())
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.apttext", helloWorld)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
