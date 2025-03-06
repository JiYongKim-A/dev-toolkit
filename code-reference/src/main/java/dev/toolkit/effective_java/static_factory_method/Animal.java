package dev.toolkit.effective_java.static_factory_method;

/**
 * [ 인터페이스 기반 정적 팩터리 메서드 ]
 * 정적 펙터리 메서드를 활용하여
 * 객체 생성을 한곳에서 관리할 수 있으며,
 * <p>
 * 정적 팩터리 메서드에서 반환 타입을 인터페이스로 구성하여
 * 어떤 객체를 반환할지는 나중에 결정할 수 있다 즉
 * 구체적 구현체를 숨길 수 있다 + 새로운 구현체가 구현되더라도 기존 코드 수정의 영향을 낮춘다.
 * ->  인터페이스 기반의 유연한 설계 가능.
 */
public interface Animal {
    void makeSound();
}

class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }
}

class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
}

class AnimalFactory {
    //정적 펙터리 메서드
    public static Animal createDog() {
        return new Dog();
    }

    //정적 펙터리 메서드
    public static Animal createCat() {
        return new Cat();
    }

    public static Animal createAnimal(String type) {
//        return "dog".equalsIgnoreCase(type) ? new Dog() : // 삼항 연산자 return
//                "cat".equalsIgnoreCase(type) ? new Cat() :
//                        null;

        return switch (type.toLowerCase()) { // **Java 12+**에서 지원하는 switch 표현식을 활용하여 가독성을 개선
            case "dog" -> new Dog();
            case "cat" -> new Cat();
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }


}

class AnimalMain {
    public static void main(String[] args) {
        Animal dog1 = AnimalFactory.createDog();
        Animal dog2 = AnimalFactory.createAnimal("DOG");

        Animal cat1 = AnimalFactory.createCat();
        Animal cat2 = AnimalFactory.createAnimal("CAT");

        Animal unknown = AnimalFactory.createAnimal("pikachu"); // IllegalArgument Exception


        // Animal 인터페이스만을 사용 - 실제 구현체가 무엇인지 알지 못함
        dog1.makeSound(); // Bark!!
        dog2.makeSound(); // Bark!!

        cat1.makeSound(); // Meow
        cat2.makeSound(); // Meow

    }
}