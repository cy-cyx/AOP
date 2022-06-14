package com.plugin;

import org.objectweb.asm.MethodVisitor;

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
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
    }
}
