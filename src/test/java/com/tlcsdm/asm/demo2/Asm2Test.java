package com.tlcsdm.asm.demo2;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author: 唐 亮
 * @date: 2022/8/11 19:41
 * @since: 1.0
 */
public class Asm2Test {

    /**
     * ClassReader主要功能就是读取字节码文件，然后把读取的数据通知ClassVisitor,字节码文件可以多种方式传入：
     * public ClassReader(final InputStream inputStream)：字节流的方式；
     * public ClassReader(final String className)：文件全路径；
     * public ClassReader(final byte[] classFile)：二进制文件；
     * <p>
     * ClassReader的accept方法处理接收一个访问者，还包括另外一个parsingOptions参数，选项包括：
     * SKIP_CODE：跳过已编译代码的访问（如果您只需要类结构，这可能很有用）；
     * SKIP_DEBUG：不访问调试信息，也不为其创建人工标签；
     * SKIP_FRAMES：跳过堆栈映射帧；
     * EXPAND_FRAMES：解压缩这些帧；
     */
    @Test
    public void test1() {
        ClassReader classReader = null;
        try {
            classReader = new ClassReader(TestService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassWriter classVisitor = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(classVisitor, 0);
    }

    /**
     * ClassWriter继承于ClassVisitor，主要用来生成类，可以单独使用
     * 通过ClassWriter生成一个字节码文件，然后转换成字节数组，最后通过FileOutputStream输出到文件中
     * 在实例化ClassWriter需要提供一个参数flags，选项包括：
     * COMPUTE_MAXS：将为你计算局部变量与操作数栈部分的大小；还是必须调用 visitMaxs，但可以使用任何参数：它们将被忽略并重新计算；使用这一选项时，仍然必须自行计算这些帧；
     * COMPUTE_FRAMES：一切都是自动计算；不再需要调用 visitFrame，但仍然必须调用 visitMaxs（参数将被忽略并重新计算）；
     * 0：不会自动计算任何东西；必须自行计算帧、局部变量与操作数栈的大小；
     */
    @Test
    public void test2() throws IOException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "pkg/Comparable", null, "java/lang/Object", new String[]{"pkg/Mesurable"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, -1).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();

        //输出
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\testPlace\\Comparable.class");
        fileOutputStream.write(b);
        fileOutputStream.close();
    }

    /**
     * 转换操作
     * 在类读取器和类写入器之间引入一个 ClassVisitor，把三者整合起来，大致代码结构如下所示：
     */
    @Test
    public void test3() throws IOException {
        ClassReader classReader = new ClassReader(TestService.class.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new AddFieldAdapter(classWriter, ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "ids", "I");
        classReader.accept(classVisitor, 0);

        byte[] b = classWriter.toByteArray();
        //输出
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\testPlace\\Comparable.class");
        fileOutputStream.write(b);
        fileOutputStream.close();
    }

    /**
     * MethodVisitor
     */
    @Test
    public void test4() throws IOException {
        ClassReader classReader = new ClassReader(TestService.class.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, 0);

        byte[] b = classWriter.toByteArray();
        //输出
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\testPlace\\Comparable.class");
        fileOutputStream.write(b);
        fileOutputStream.close();
    }

}
