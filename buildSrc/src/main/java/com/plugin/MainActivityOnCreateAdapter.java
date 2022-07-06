package com.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Time:2022/6/14 16:06
 * Author:
 * Description:
 */
public class MainActivityOnCreateAdapter extends MethodVisitor {

    public MainActivityOnCreateAdapter(int api) {
        super(api);
    }

    public MainActivityOnCreateAdapter(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("onCreate start");
    }

    @Override
    public void visitInsn(int opcode) {

        // 我要在方法尾部插入代码，我自己return
        if (opcode == Opcodes.RETURN) {
            System.out.println("方法执行到 return");
        } else {
            super.visitInsn(opcode);
        }
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        // 在代码尾部插入个方法
        mv.visitLdcInsn("xx");
        mv.visitLdcInsn("\u6267\u884c\u65b9\u6cd5\u7ed3\u675f\u63d2\u5165\u4ee3\u7801");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I",
            false);
        mv.visitInsn(Opcodes.POP);

        // 自己塞入个return
        mv.visitInsn(Opcodes.RETURN);

        // 保证最大栈够
        int returnMaxStack = maxStack;
        if (returnMaxStack < 2) {
            returnMaxStack = 2;
        }

        super.visitMaxs(returnMaxStack, maxLocals);
        System.out.println("maxStack：" + maxStack + "maxLocals:" + maxLocals);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("onCreate end");
    }
}
