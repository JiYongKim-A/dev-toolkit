package dev.toolkit.effective_java.attach.supplier;

import java.util.List;
import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        System.out.println("------------------------------------------------------");
        // ✅ Supplier 사용: 문자열 공급
        // Supplier는 값을 제공하는 역할을 하는 함수형 인터페이스.
        // 여기서는 람다 표현식을 사용하여 문자열을 반환하는 Supplier를 생성.
        Supplier<String> stringSupplier = () -> "String Supplier!";
        System.out.println(stringSupplier.get()); // "String Supplier!" 출력
        System.out.println("------------------------------------------------------");

        // ✅ Supplier 사용: 생성자 참조
        // Supplier를 사용하여 Dog 객체를 생성.
        // 메서드 참조(Dog::new)를 사용하여 new Dog()를 호출하는 역할을 함.
        Supplier<Dog> dogSupplier = Dog::new;
        Dog dog = dogSupplier.get(); // new Dog()와 동일한 동작

        // ✅ Supplier 사용: 인스턴스 메서드 참조
        // dog.getName()을 Supplier로 래핑하여 필요할 때 호출할 수 있도록 만듦.
        Supplier<String> dogNameSupplier = dog::getName;

        // ✅ 메서드 참조를 사용한 Runnable 생성
        // dogNameSupplier::get 은 dogNameSupplier.get()을 실행하는 Runnable을 만든 것.
        // 즉, get()을 실행하지만 반환값을 무시하는 형태가 됨.
        Runnable runnable = dogNameSupplier::get;
        runnable.run(); // 하지만 실행해도 출력되지 않음 (결과를 반환할 곳이 없기 때문)

        // ✅ Supplier에서 직접 get() 호출
        String dogName = dogNameSupplier.get();// "BBOBBI" 반환
        System.out.println(dogName); // "BBOBBI" 출력
        System.out.println("------------------------------------------------------");

        // ✅ `Supplier`를 사용하여 싱글턴 객체 가져오기
        // LazySingleton::getInstance는 LazySingleton.getInstance() 메서드를 참조하는 Supplier.
        // getInstance()가 static이 아니면 컴파일 에러 발생 (클래스 이름으로 참조하려면 static 필요)
        Supplier<LazySingleton> lazySingletonSupplier = LazySingleton::getInstance;

        // Supplier를 통해 LazySingleton 객체를 필요할 때만 생성 가능
        LazySingleton lazySingleton1 = lazySingletonSupplier.get();
        LazySingleton lazySingleton2 = lazySingletonSupplier.get();

        // 같은 인스턴스를 반환하는지 확인 (싱글턴 패턴 유지됨)
        System.out.println(lazySingleton1 == lazySingleton2); // true (같은 객체)
        System.out.println("------------------------------------------------------");


        Supplier<List<String>> nameListSupplier = () -> List.of("chulsoo", "suji", "yuri");
        List<String> names = nameListSupplier.get();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("------------------------------------------------------");

    }
}

// ✅ 기본적인 Dog 클래스
class Dog {
    // 기본 생성자
    Dog() {
    }

    // 개의 이름을 반환하는 메서드
    public String getName() {
        return "BBOBBI";
    }

    // 개가 짖는 동작
    public void bark() {
        System.out.println("bow");
    }
}

// ✅ Lazy Singleton 패턴 적용된 클래스
class LazySingleton {
    private static LazySingleton INSTANCE; // 정적 필드로 단일 인스턴스를 저장

    // private 생성자로 외부에서 인스턴스 생성 방지
    private LazySingleton() {
    }

    // 객체가 필요할 때만 생성하는 Lazy Initialization 방식
    public static LazySingleton getInstance() {
        if (INSTANCE == null) { // 첫 호출 시 객체 생성
            INSTANCE = new LazySingleton();
        }
        return INSTANCE; // 이후에는 동일한 인스턴스를 반환
    }
}
