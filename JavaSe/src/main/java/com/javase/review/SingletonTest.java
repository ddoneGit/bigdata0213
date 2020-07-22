package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/21-23:13
 */
public class SingletonTest {
    public static void main(String[] args) {
        Student i1 = Student.getInstance();
        Student i2 = Student.getInstance();
        System.out.println(i1==i2);
    }

}
class People{
    //饿汉式
    //两个private,一个public
    private  People(){
        //①私有化构造器
    }
    private static People people= new People();
    //2.内部创建当前类的实例
    public static People getInstance(){
        //3.提供公共的,静态的,返回当前类实例的方法
        return people;
    }
}
class Student{
    private Student() {
    }
    private static Student student = null;
    public static Student getInstance(){
        if(student==null){
            student = new Student();
        }
        return student;
    }
//懒汉式
}
