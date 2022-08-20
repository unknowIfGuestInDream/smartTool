package com.tlcsdm.asm.demo2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author: 唐 亮
 * @date: 2022/8/20 21:28
 * @since: 1.0
 */
public class MyClassVisitor extends ClassVisitor implements Opcodes {
    public MyClassVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature,
                exceptions);
        if (!name.equals("<init>") && methodVisitor != null) {
            methodVisitor = new MyMethodVisitor(methodVisitor);
        }
        return methodVisitor;
    }
}
