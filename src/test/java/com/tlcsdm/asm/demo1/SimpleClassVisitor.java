package com.tlcsdm.asm.demo1;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

/**
 * @author: 唐 亮
 * @date: 2022/8/20 21:01
 * @since: 1.0
 */
public class SimpleClassVisitor extends ClassVisitor {
    public SimpleClassVisitor(int i) {
        super(i);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + ", access:" + access + ", name:" + name + ", desc:" + desc + ", signature:" + signature + ", value:" + value);

        return super.visitField(access, name, desc, signature, value);
    }
}
