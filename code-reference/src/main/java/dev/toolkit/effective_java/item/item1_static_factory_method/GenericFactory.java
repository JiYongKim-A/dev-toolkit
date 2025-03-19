package dev.toolkit.effective_java.item.item1_static_factory_method;

import java.util.ArrayList;
import java.util.List;

/**
 * [ 타입 추론(Type Inference) 활용 ] - (제네릭 활용)
 * 1. 제네릭(Generic)만 사용하면 → 타입 추론을 통해 객체 생성 시 타입 명시를 생략할 수 있다.
 * 2. 정적 팩터리 메서드(Static Factory Method)만 사용하면 → 생성자 호출 대신 의미 있는 이름을 부여한 정적 메서드를 호출할 수 있고, 생성 시 타입 명시를 줄일 수 있다.
 * 3. 제네릭 + 정적 팩터리 메서드를 함께 사용하면 → 객체 생성 시 타입 추론이 더 강력하게 적용되며, 코드 가독성과 재사용성이 높아진다.
 */

public class GenericFactory {
    public static <T> List<T> createEmptyList() {
        return new ArrayList<>();
    }
}

class GenericFactoryMain {
    public static void main(String[] args) {
        List<Integer> integerList = GenericFactory.createEmptyList(); // 제너릭 + 정적 팩터리 메서드를 통해 타입 추론 극대화
        List<String> stringList = GenericFactory.createEmptyList();

        integerList.add(Integer.MAX_VALUE);
        stringList.add("letter");
    }
}

