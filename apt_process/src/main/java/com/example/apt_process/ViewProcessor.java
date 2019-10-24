package com.example.apt_process;

import com.example.apt_lib.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * create by cy
 * time : 2019/10/23
 * version : 1.0
 * Features :  实现类似黄油刀的东西
 */
@AutoService(Processor.class)
public class ViewProcessor extends AbstractProcessor {

    private Messager mMessager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(BindView.class.getCanonicalName());
        return annotationSet;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        TreeMap<String, ArrayList<Item>> binds = new TreeMap<>();
        ArrayList<String> clazzs = new ArrayList<>();

        mMessager.printMessage(Diagnostic.Kind.NOTE, "bindView");

        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            // parameter
            VariableElement variableElement = (VariableElement) annotatedElement;

            // class
            TypeElement enclosingElement = (TypeElement) variableElement.getEnclosingElement();
            TypeMirror typeMirror = enclosingElement.asType();
            TypeName targetType = TypeName.get(typeMirror);
            if (targetType instanceof ParameterizedTypeName) {
                targetType = ((ParameterizedTypeName) targetType).rawType;
            }

            BindView annotation = variableElement.getAnnotation(BindView.class);

            Name simpleName = enclosingElement.getSimpleName();

            ArrayList<Item> items = binds.get(simpleName.toString());
            if (items == null) {
                items = new ArrayList<>();
            }
            Item item = new Item();
            item.clazz = targetType;
            item.id = annotation.id();
            item.parameter = variableElement.getSimpleName().toString();

            items.add(item);
            if (!clazzs.contains(simpleName.toString())) {
                clazzs.add(simpleName.toString());
            }
            binds.put(simpleName.toString(), items);
        }

        for (String clazz : clazzs) {
            ArrayList<Item> items = binds.get(clazz);
            String className = clazz + "_bind";

            MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(items.get(0).clazz, "clazz")
                    .addParameter(ClassName.get("android.view", "View"), "view");

            for (Item item : items) {
                constructor.addStatement("clazz." + item.parameter + "=" + "view.findViewById(" + item.id + ")");
            }

            JavaFile javaFile = JavaFile.builder("com.example.apttext",
                    TypeSpec.classBuilder(className)
                            .addMethod(constructor.build()).build()).build();

            mMessager.printMessage(Diagnostic.Kind.NOTE, javaFile.toString());

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.RELEASE_7;
    }

    private class Item {
        TypeName clazz;
        String parameter;
        int id;
    }
}
