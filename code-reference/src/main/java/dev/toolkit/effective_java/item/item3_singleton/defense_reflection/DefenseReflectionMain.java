package dev.toolkit.effective_java.item.item3_singleton.defense_reflection;

public class DefenseReflectionMain {

}
class RemoteControl{
    private static final RemoteControl INSTANCE = new RemoteControl();
    private RemoteControl() {
        if (INSTANCE != null) { // 리플렉션 방어
            throw new IllegalStateException("Cannot create instance via reflection.");
        }
    }

    public static RemoteControl getInstance() {
        return INSTANCE;
    }
}