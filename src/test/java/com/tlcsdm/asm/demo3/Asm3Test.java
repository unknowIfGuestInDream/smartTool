package com.tlcsdm.asm.demo3;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: 唐 亮
 * @date: 2022/8/11 19:41
 * @since: 1.0
 */
public class Asm3Test {

    /**
     * 读取一个类的信息
     */
    @Test
    public void test1() throws IOException {
        //1.定义ClassReader
        String sourceClassName = "com.tlcsdm.asm.demo3.Employee";
        ClassReader classReader = new ClassReader(sourceClassName);
        //2.定义ClassWriter
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //3.定义ClassVisitor
        ClassVisitor classVisitor = new EmployeeClassVisitor(classWriter);

        // 定义classVisitor输入数据,
        // SKIP_DEBUG 如果设置了此标志，则这些属性既不会被解析也不会被访问
        // EXPAND_FRAMES 依次调用ClassVisitor 接口的各个方法
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        // 将最终修改的字节码以byte数组形式返回
        byte[] bytes = classWriter.toByteArray();

        String targetClassName = "com.tlcsdm.asm.demo3.Employee$EnhancedByASM";
        Class<?> clazz = new EmployeeClassLoader().defineClassFromClassFile(targetClassName, bytes);
        System.out.println("【EmployeeOw2AsmTest】clazz：" + clazz);

        // 通过文件流写入方式覆盖原先的内容，实现class文件的改写
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\testPlace\\Employee$EnhancedByASM.class");
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

}
