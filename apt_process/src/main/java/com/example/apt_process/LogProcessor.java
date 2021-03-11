package com.example.apt_process;

import com.example.apt_lib.Log;
import com.google.auto.service.AutoService;

import java.util.HashSet;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * create by cy
 * time : 2019/10/14
 * version : 1.0
 * Features : 用于编译时打印类信息
 */
//@AutoService(Processor.class)
public class LogProcessor extends AbstractProcessor {

    private Messager mMessager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(Log.class.getCanonicalName());
        return annotationSet;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "print class");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Log.class)) {
            mMessager.printMessage(Diagnostic.Kind.NOTE,"into for");
            if (element instanceof TypeElement) {
                logClass((TypeElement) element);
            } else if (element instanceof ExecutableElement) {
                logFun((ExecutableElement) element);
            } else if (element instanceof VariableElement) {
                logParameter((VariableElement) element);
            }
        }
        return false;
    }

    private void logClass(TypeElement typeElement) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "class name:" + typeElement.getSimpleName() + "===start===");

        List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        for (Element element : enclosedElements) {
            if (element instanceof ExecutableElement) {
                logFun((ExecutableElement) element);
            } else if (element instanceof VariableElement) {
                logParameter((VariableElement) element);
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "class name:" + typeElement.getSimpleName() + "===end===");
    }

    private void logFun(ExecutableElement executableElement) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "fun name:" + executableElement.getSimpleName() + "===start===");

        List<? extends VariableElement> parameters = executableElement.getParameters();

        for (VariableElement variableElement : parameters) {
            logParameter(variableElement);
        }

        List<? extends TypeMirror> thrownTypes = executableElement.getThrownTypes();
        for (TypeMirror typeMirror : thrownTypes) {
            logThrown(typeMirror);
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "fun name:" + executableElement.getSimpleName() + "===end===");

    }

    private void logParameter(VariableElement variableElement) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "parameter name:" + variableElement.getSimpleName());
    }

    private void logThrown(TypeMirror typeMirror) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "thrown " + typeMirror.getClass().getSimpleName());
    }

    private void logReturn(TypeMirror typeMirror) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "return " + typeMirror.getClass().getSimpleName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.RELEASE_7;
    }
}
