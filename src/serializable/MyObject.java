package serializable;

import java.io.*;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package serializable
 * @description: 序列化与反序列化的单例模式实现
 * @Version 1.0
 * @create 2018-04-25 22:04
 **/
public class MyObject implements Serializable{

    private static final long serialVersionUID = 1L;

    private static class MyObjectHandler{
        private static final MyObject myObject = new MyObject();
    }
    private MyObject(){

    }
    protected Object readResolve(){
        System.out.println("调用了resolve方法！");
        return MyObjectHandler.myObject;
    }
    public static MyObject getInstance(){
        return MyObjectHandler.myObject;
    }

    public static void main(String[] args){
        try{
            MyObject myObject = MyObject.getInstance();
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/MyObject.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(myObject);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("myObject hashCode:"+myObject.hashCode());
            System.out.println("----------------------------------------");
            FileInputStream fileInputStream = new FileInputStream(new File("D:/MyObject.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            System.out.println("=======================readObject before=======================");
            MyObject myObject_1 = (MyObject)objectInputStream.readObject();
            System.out.println("=======================readObject after=======================");
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("myObject_1 hashCode:"+myObject_1.hashCode());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
