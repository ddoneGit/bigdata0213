package com.javase.interview;

/**
 * @author ddone
 * @date 2020/7/25-13:45
 */
//单列模式
//    题目二：  (20分) 写一个Singleton出来。Singleton模式主要作用是保证在Java应用程序中，一个类Class只有一个实例存在。
public class Singleton {
    public static void main(String[] args) {
        Person p1 = Person.getInstance();
        Person p2 = Person.getInstance();
        System.out.println(p1 == p2);
        Human h1 = Human.getInstance();
        Human h2= Human.getInstance();
        System.out.println(h1==h2);
    }
}
class Person{
    private Person(){

    }
    private  static  Person person=new Person();

    public static Person getInstance(){
        return person;
    }

}

class Human{
    private Human(){

    }
    private  static Human human=null;

    public static Human getInstance(){
        if(human == null){
           human=new Human();
        }
        return human;
    }

}