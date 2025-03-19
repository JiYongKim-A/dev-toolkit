package dev.toolkit.effective_java.attach.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    private int multiply(int a, int b) {
        return a * b;
    }
}

class ReflectionMethodExample {
    public static void main(String[] args) {
        Class<?> clazz = Calculator.class;
        Calculator calculator = new Calculator();

        System.out.println("Public 메서드");
        for (Method method : clazz.getMethods()) {
            System.out.println(method);
        }

        System.out.println("\n모든 메서드");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method);
            System.out.println("메서드의 접근 제어자를 나타내는 int 값 : "+method.getModifiers()); // 메서드의 접근 제어자를 나타내는 int 값 반환
            if(Modifier.isPrivate(method.getModifiers())){
                System.out.println("Private Method 입니다.");
            }
            System.out.println();
        }

        try {
            Method privateMethod = clazz.getDeclaredMethod("multiply", int.class, int.class);
            privateMethod.setAccessible(true); // private 접근 가능하게 변경
            int result = (int) privateMethod.invoke(calculator, 9, 9); // 리플렉션을 통해 동적으로 메서드를 호출
            System.out.println("\nprivate 메서드 실행 결과 : " + result);
        } catch (InvocationTargetException e) { // 리플렉션으로 메서드나 생성자를 호출하는 동안, 내부에서 실제 호출한 메서드가 예외를 발생시켰을 때
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) { // 요청한 이름과 매개변수 타입에 해당하는 생성자나 메서드를 클래스에서 찾지 못했을 때
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) { // 클래스, 메서드, 생성자, 필드 등에 접근할 권한이 없을 때 발생
            throw new RuntimeException(e);
        }
    }
}