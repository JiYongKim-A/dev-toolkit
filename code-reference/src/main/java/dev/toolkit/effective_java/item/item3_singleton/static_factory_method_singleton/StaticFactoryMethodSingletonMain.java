package dev.toolkit.effective_java.item.item3_singleton.static_factory_method_singleton;

public class StaticFactoryMethodSingletonMain {
    public static void main(String[] args) {
        RemoteControl instance1 = RemoteControl.getInstance();
        RemoteControl instance2 = RemoteControl.getInstance();
        System.out.println(instance1 == instance2); // ✅ true (같은 인스턴스 반환)
    }
}
class RemoteControl{
    private static final RemoteControl INSTANCE = new RemoteControl();
    private RemoteControl() {}

    public static RemoteControl getInstance() {
        return INSTANCE;
    }
}
