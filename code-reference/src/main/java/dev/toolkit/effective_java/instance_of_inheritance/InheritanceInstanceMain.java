package dev.toolkit.effective_java.instance_of_inheritance;

public class InheritanceInstanceMain {
    public static void main(String[] args) {
        Child child = new Child();
    }

}

class Parent {
    int parentInstanceField = parentInitialize();

    public Parent() {
        System.out.println("Parent 클래스 생성자 실행");
    }

    public int parentInitialize() {
        System.out.println("Parent 인스턴스 필드 초기화 실행");
        return 10;
    }
}

class Child extends Parent {
    int childInstanceField = childInitialize();
    public Child() {
        System.out.println("Child 클래스 생성자 실행");
    }

    public int childInitialize() {
        System.out.println("Child 인스턴스 필드 초기화 실행");
        return 10;
    }
}
