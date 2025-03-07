package dev.toolkit.effective_java.item1_static_factory_method;

import java.util.HashMap;
import java.util.Map;

/**
 * [ 자주 사용되는 객체를 캐싱하여 재사용 ] - (객체 풀 사용)
 * 정적 팩터리 메서드를 활용하여
 * ("Red", "red")을 여러 번 생성하지 않고 메모리 절약.
 */
public class Color {
    private static final Map<String, Color> COLOR_CACHE = new HashMap<>();

    private String name;

    // private를 사용하여 외부 사용 제한
    private Color(String name) {
        this.name = name;
    }

    // 정적 팩터리 메서드
    public static Color of(String name) {
        // 객체 생성 전 추가 검증이나 캐싱 수행 가능
        return COLOR_CACHE.computeIfAbsent(
                name.toLowerCase(), Color::new);
    }

    public String getName() {
        return name;
    }
}

class ColorMain {
    public static void main(String[] args) {
        Color red = Color.of("Red");
        Color anotherRed = Color.of("Red");

        System.out.println(red.getName()); // red
        System.out.println(anotherRed.getName()); // red
        System.out.println(red == anotherRed); // true ( 같은 인스턴스 )
    }
}