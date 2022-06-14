package com.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Time:2022/6/13 10:43
 * Author:
 * Description:
 */
public class MainActivityVisitor extends ClassVisitor {

    private String TAG = "MainActivityVisitor";

    public MainActivityVisitor(int api) {
        super(api);
    }

    public MainActivityVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println(TAG + "visit------ name:" + name);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(TAG + "visitMethod------ " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("onCreate")) {
            return new MainActivityOnCreateAdapter(Opcodes.ASM6, mv);
        }
        return mv;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println(TAG + "visitField------ " + name);
        return super.visitField(access, name, descriptor, signature, value);
    }
}
