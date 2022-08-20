package com.tlcsdm.asm.demo3;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author: 唐 亮
 * @date: 2022/8/20 21:36
 * @since: 1.0
 */
public class EmployeeMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public EmployeeMethodVisitor(int Opcodes, MethodVisitor methodVisitor) {
        super(Opcodes, methodVisitor);
    }

    //MethodVisitor 中定义了不同的visitXXX()方法，代表的不同的访问阶段。
    //visitCode表示刚刚进入方法。
    @Override
    public void visitCode() {
        //添加一行System.currentTimeMillis()调用
        visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        //并且将其存储在局部变量表内位置为1的地方
        visitVarInsn(Opcodes.LSTORE, 1);
        //上面两个的作用就是在Studying方法的第一行添加 long start = System.currentTimeMillis()
    }

    //visitInsn 表示访问进入了方法内部
    @Override
    public void visitInsn(int opcode) {
        //通过opcode可以得知当前访问到了哪一步，如果是>=Opcodes.IRETURN && opcode <= Opcodes.RETURN 表明方法即将退出
        if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
            //加载局部变量表中位置为1的数据，也就是start的数据，并传入给下面的方法
            visitVarInsn(Opcodes.LLOAD, 1);
            //然后调用自定义的一个工具方法，用来输出耗时
            visitMethodInsn(Opcodes.INVOKESTATIC, "com/tlcsdm/Before", "end", "(J)V", false);
        }
        super.visitInsn(opcode);
    }

}

class EmployeeConstructorMethodVisitor extends MethodVisitor {
    //定义一个全局变量记录父类名称
    private String superClassName;

    public EmployeeConstructorMethodVisitor(int i, MethodVisitor methodVisitor, String superClassName) {
        super(i, methodVisitor);
        this.superClassName = superClassName;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean b) {
        //当开始初始化构造函数时，先访问父类构造函数,类似源码中的super()
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superClassName;
        }
        super.visitMethodInsn(opcode, owner, name, desc, b);
    }
}
