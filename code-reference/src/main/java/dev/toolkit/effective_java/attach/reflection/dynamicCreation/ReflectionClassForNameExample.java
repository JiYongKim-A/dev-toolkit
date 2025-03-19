package dev.toolkit.effective_java.attach.reflection.dynamicCreation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Coffee {
    public Coffee() {
        System.out.println("Coffee 객체 생성 완료!");
    }
}

/**
 * [ Class.forName()을 사용한 동적 객체 생성 ]
 */
class ReflectionClassForNameExample {
    public static void main(String[] args) {
        Class<?> clazz;
        try {
            clazz = Class.forName("dev.toolkit.effective_java.attach.reflection.dynamicCreation.Coffee");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            Object instance1 = clazz.getDeclaredConstructor().newInstance(); // 생성자를 선택하여 인스턴스화 진행
            System.out.println("instance1 : "+ instance1);

            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            Object instance2 = declaredConstructor.newInstance(); // 생성자를 선택하여 인스턴스화 진행
            System.out.println("instance2 : "+instance2);
        } catch (NoSuchMethodException e) { // 요청한 이름과 매개변수 타입에 해당하는 생성자나 메서드를 클래스에서 찾지 못했을 때
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) { // 리플렉션으로 메서드나 생성자를 호출하는 동안, 내부에서 실제 호출한 메서드가 예외를 발생시켰을 때
            throw new RuntimeException(e);
        } catch (InstantiationException e) { // 클래스를 인스턴스화하려고 했지만, 해당 클래스가 추상 클래스이거나 인터페이스이거나 인스턴스 생성이 불가능한 경우
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) { // 클래스, 메서드, 생성자, 필드 등에 접근할 권한이 없을 때 발생
            throw new RuntimeException(e);
        }
    }
}
