package com.tlcsdm.asm.demo3;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author: 唐 亮
 * @date: 2022/8/20 21:35
 * @since: 1.0
 */
public class EmployeeClassVisitor extends ClassVisitor {
    private String className;
    private String superName;

    public EmployeeClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        this.superName = superName;
        //super.visit(version, access, name, signature, superName, interfaces);
        super.visit(version, access, name + "$EnhancedByASM", signature, name, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("【ClassVisitor visitField 】access:" + access + ", name:" + name + ", desc:" + desc + "，signature:" + signature + ",value:" + value);
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("【ClassVisitor visitMethod】access:" + access + ", name:" + name + ", desc:" + desc + ", signature:" + signature + ", exceptions:" + exceptions);
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        //判断当前读取的方法
        if ("working".equals(name)) {
            //如果是working方法，则包装一个方法的Visitor
            wrappedMv = new EmployeeMethodVisitor(Opcodes.ASM5, mv);
        } else if ("<init>".equals(name)) {
            //如果是构造方法，处理子类中父类的构造函数调用
            wrappedMv = new EmployeeConstructorMethodVisitor(Opcodes.ASM5, mv, superName);
        }
        return wrappedMv;
    }
}
