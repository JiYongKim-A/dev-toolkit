package dev.toolkit.effective_java.item1_static_factory_method;

/**
 * [ Boolean 값 캐싱 ]
 * 정적 팩터리 메서드를 활용하여
 * 매번 새로운 객체를 생성하는 것이 아니라,
 * 미리 만들어둔 인스턴스를 반환하여 메모리 절약.
 */
public class BooleanWrapper { // 불변 객체

    private static final BooleanWrapper TRUE_INSTANCE = new BooleanWrapper(true);
    private static final BooleanWrapper FALSE_INSTANCE = new BooleanWrapper(false);

    private final boolean value;

    // private 를 통해 외부 사용 제한
    private BooleanWrapper(boolean value) {
        this.value = value;
    }

    // 정적 펙터리 메서드
    public static BooleanWrapper of(boolean value) {
        return value ? TRUE_INSTANCE : FALSE_INSTANCE;
    }

    public boolean getValue() {
        return value;
    }

}

class BooleanWrapperMain {
    public static void main(String[] args) {
        BooleanWrapper trueWrapper = BooleanWrapper.of(Boolean.TRUE);
        BooleanWrapper falseWrapper = BooleanWrapper.of(Boolean.FALSE);
        BooleanWrapper anotherTrueWrapper = BooleanWrapper.of(Boolean.TRUE);

        System.out.println(trueWrapper.getValue()); // true
        System.out.println(falseWrapper.getValue()); // false
        System.out.println(anotherTrueWrapper == trueWrapper); // true (같은 인스턴스를 공유함)
    }
}

