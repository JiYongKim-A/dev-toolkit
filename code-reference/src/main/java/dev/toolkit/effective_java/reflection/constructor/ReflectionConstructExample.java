package dev.toolkit.effective_java.reflection.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

class Person {
    public String name;
    private int age;

    public Person() { // 기본 생성자
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(int age) {
        this.age = age;
    }

    private Person(String name, int age) { // private 생성자
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}

public class ReflectionConstructExample {

    public static void main(String[] args) {
        Class<?> clazz = Person.class;

        // public 생성자만 가져오기
        System.out.println("Public 생성자 : ");
        for (
                Constructor<?> constructor : clazz.getConstructors()) {
            System.out.println(constructor);
        }

        // 모든 생성자 가져오기 (private 포함)
        System.out.println("\n모든 생성자 : ");
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            System.out.println(constructor);
            System.out.println("생성자의 접근 제어자를 나타내는 int 값 : "+constructor.getModifiers()); // 메서드의 접근 제어자를 나타내는 int 값 반환
            if(Modifier.isPrivate(constructor.getModifiers())){
                System.out.println("Private 생성자 입니다.");
            }
            System.out.println();
        }

        try {
            // private 생성자 호출
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(String.class, int.class);
            declaredConstructor.setAccessible(true); // private 접근 가능하게 변경
            Person person = (Person) declaredConstructor.newInstance("철수", 10);
            System.out.println("\nPrivate 생성자를 통해 생성된 객체");
            System.out.println(person);
        } catch (NoSuchMethodException e) { // 요청한 이름과 매개변수 타입에 해당하는 생성자나 메서드를 클래스에서 찾지 못했을 때
            throw new RuntimeException(e);
        } catch (
                InvocationTargetException e) { // 리플렉션으로 메서드나 생성자를 호출하는 동안, 내부에서 실제 호출한 메서드가 예외를 발생시켰을 때
            throw new RuntimeException(e);
        } catch (InstantiationException e) { // 클래스를 인스턴스화하려고 했지만, 해당 클래스가 추상 클래스이거나 인터페이스이거나 인스턴스 생성이 불가능한 경우
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) { // 클래스, 메서드, 생성자, 필드 등에 접근할 권한이 없을 때 발생
            throw new RuntimeException(e);
        }
    }
}




