package com.example.apt_process;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * create by cy
 * time : 2019/10/12
 * version : 1.0
 * Features :  {@link JavaFile} 使用例子
 */
public class javaPoetText {
    /**
     * package com.example.apttext;
     * <p>
     * class Clazz {
     * private int mField;
     * <p>
     * Clazz() {
     * mField = 1;
     * mField = 2;
     * }
     * <p>
     * public void method() {
     * System.out.println("xx");
     * }
     * }
     */
    public static void main(String[] args) {
        JavaFile javaFile = JavaFile.builder("com.example.apttext",
                TypeSpec.classBuilder("Clazz")
                        .addField(FieldSpec.builder(int.class, "mField", Modifier.PRIVATE)
                                .build())
                        .addMethod(MethodSpec.constructorBuilder().addStatement("mField = 1").addStatement("mField = 2").build())
                        .addMethod(MethodSpec.methodBuilder("method")
                                .addModifiers(Modifier.PUBLIC)
                                .returns(void.class)
                                .addStatement("System.out.println(\"xx\")")
                                .build())
                        .build())
                .build();
        System.out.println(javaFile.toString());
    }
}
