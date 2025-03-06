package dev.toolkit.effective_java.static_factory_method;

/**
 * [ 명확한 인스턴스 반환 메서드명 ]
 * 정적 팩터리 메서드를 활용하여
 * 다양한 생성을 명확하게 표현이 가능
 * 객체 생성 전 추가적인 (검증, 초기화 로직 수행, 인스턴스 생성 전략 변경)등의 제어가 가능
 */
class Person {
    private String name;
    private int age;
    private boolean isEmployee;

    // private 를 통해 외부 사용 제한
    private Person(String name, int age, boolean isEmployee) {
        this.name = name;
        this.age = age;
        this.isEmployee = isEmployee;
    }

    // 정적 펙터리 메서드
    public static Person createAdult(String name, int age) {
        if (age < 19) {  // 객체 생성 전 추가 검증 수행 가능
            throw new IllegalArgumentException("성인은 19세 이상이여야 합니다.");
        }
        return new Person(name, age, false);
    }

    // 정적 펙터리 메서드
    public static Person createChild(String name, int age) {
        if (age > 18) {  // 객체 생성 전 추가 검증 수행 가능
            throw new IllegalArgumentException("미성년자는 18세 이하이여야 합니다.");
        }
        return new Person(name, age, false);
    }


    // 정적 펙터리 메서드
    public static Person createEmployee(String name, int age) {
        if (age < 19) {  // 객체 생성 전 추가 검증 수행 가능
            throw new IllegalArgumentException("19세 이상에만 직장인이 될 수 있습니다.");
        }
        return new Person(name, age, true);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isEmployee() {
        return isEmployee;
    }
}

class PersonMain {
    public static void main(String[] args) {
        Person adult1 = Person.createAdult("짱구", 19);
        Person adult2 = Person.createAdult("훈이", 18); // IllegalArgumentException

        Person child1 = Person.createChild("맹구", 19); // IllegalArgumentException
        Person child2 = Person.createChild("철수", 18);

        Person employee1 = Person.createEmployee("유리", 19);
        Person employee2 = Person.createEmployee("수지", 18); // IllegalArgumentException
    }
}



