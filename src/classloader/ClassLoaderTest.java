package classloader;

import java.io.InputStream;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package classloader
 * @description: 类加载器与instanceof关键字演示
 * @Version 1.0
 * @create 2018-05-21 23:08
 **/
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception{

        ClassLoader classLoader = new ClassLoader() {
            /**
             * Loads the class with the specified <a href="#name">binary name</a>.
             * This method searches for classes in the same manner as the {@link
             * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
             * machine to resolve class references.  Invoking this method is equivalent
             * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
             * false)</tt>}.
             *
             * @param name The <a href="#name">binary name</a> of the class
             * @return The resulting <tt>Class</tt> object
             * @throws ClassNotFoundException If the class was not found
             */
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    System.out.println(name.substring(name.lastIndexOf(".")+1)+".class");
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(null == is){
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name,bytes,0,bytes.length);
                }catch (Exception e){
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = classLoader.loadClass("classloader.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof classloader.ClassLoaderTest);
    }
}
