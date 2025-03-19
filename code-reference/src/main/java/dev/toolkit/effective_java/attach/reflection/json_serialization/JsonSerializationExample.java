package dev.toolkit.effective_java.attach.reflection.json_serialization;

import java.lang.reflect.Field;

class User {
    private String name = "철수";
    private int age = 10;
}

/**
 * [ JSON 직렬화/역직렬화 예제 ]
 */
public class JsonSerializationExample {
    public static String serializeToJson(Object obj) {
        StringBuilder sb = new StringBuilder("{");
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true); // private 접근 가능하도록 변경
                sb.append("\"").append(field.getName()).append("\":")
                        .append("\"").append(field.get(obj)).append("\",");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        sb.deleteCharAt(sb.length() - 1).append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        User user = new User();
        System.out.println(serializeToJson(user));
    }
}
