package dev.toolkit.effective_java.item.item3_singleton.enum_singleton;

public class EnumSingletonMain {
    public static void main(String[] args) {
        RemoteControl instance1 = RemoteControl.INSTANCE;
        RemoteControl instance2 = RemoteControl.INSTANCE;
        System.out.println(instance1 == instance2); // ✅ true (같은 인스턴스 반환)
        instance1.contorl();
        instance2.contorl();
    }
}
enum RemoteControl{
    INSTANCE;

    public void contorl() {
        System.out.println("RemoteControl control");
    }
}
