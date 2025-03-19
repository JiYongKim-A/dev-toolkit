package dev.toolkit.effective_java.attach.reflection.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


class SecretClass {
    public String publicField = "Public 필드";
    private String privateField = "private 필드";
    public final String finalField = "Final 필드";

    @Override
    public String toString() {
        return "SecretClass{" +
                "publicField='" + publicField + '\'' +
                ", privateField='" + privateField + '\'' +
                ", finalField='" + finalField + '\'' +
                '}';
    }
}


public class ReflectionFieldExample {
    public static void main(String[] args) {
        SecretClass secret = new SecretClass();
        Class<?> clazz = secret.getClass();

        // public 필드 가져오기
        System.out.println("Public 필드 :");
        for (Field field : clazz.getFields()) {
            System.out.println(field);
        }

        // 모든 필드 가져오기 (private 포함)
        System.out.println("\n모든 필드 : ");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field);
            System.out.println("필드 접근 제어자를 나타내는 int 값 : "+field.getModifiers()); // 메서드의 접근 제어자를 나타내는 int 값 반환
            if(Modifier.isPrivate(field.getModifiers())){
                System.out.println("Private 생성자 입니다.");
            }
            System.out.println();
        }

        System.out.println("\nPrivate 필드 변경 후");
        try {
            // private 필드 조작
            Field privateField = clazz.getDeclaredField("privateField");
            privateField.setAccessible(true); // private 접근 가능하게 변경
            privateField.set(secret, "변경된 priavet 값");
            System.out.println(secret);
        } catch (NoSuchFieldException e) { // 요청한 이름과 매개변수 타입에 해당하는 생성자나 메서드를 클래스에서 찾지 못했을 때
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) { // 클래스, 메서드, 생성자, 필드 등에 접근할 권한이 없을 때 발생
            throw new RuntimeException(e);
        }
    }
}