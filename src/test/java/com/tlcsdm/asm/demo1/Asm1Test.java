package com.tlcsdm.asm.demo1;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

/**
 * @author: 唐 亮
 * @date: 2022/8/11 19:41
 * @since: 1.0
 */
public class Asm1Test {

    /**
     * 读取一个类的信息
     */
    @Test
    public void test1() {
        ClassReader classReader = null;
        try {
            classReader = new ClassReader(User.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleClassVisitor simpleClassVisitor = new SimpleClassVisitor(Opcodes.ASM5);
        ClassNode classNode = new ClassNode();
        classReader.accept(simpleClassVisitor, 0);
    }

}
