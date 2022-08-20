package com.tlcsdm.asm.demo3;

/**
 * @author: 唐 亮
 * @date: 2022/8/20 21:35
 * @since: 1.0
 */
public class EmployeeClassLoader extends ClassLoader {

    public Class defineClassFromClassFile(String className,byte[] classFile) throws ClassFormatError{
        return defineClass(className, classFile, 0, classFile.length);
    }

    public Class<?> defineClassForName(String name, byte[] data) {
        return this.defineClass(name, data, 0, data.length);
    }
}
