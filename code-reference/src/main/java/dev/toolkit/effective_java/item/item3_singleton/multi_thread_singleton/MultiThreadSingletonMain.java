package dev.toolkit.effective_java.item.item3_singleton.multi_thread_singleton;

public class MultiThreadSingletonMain {
    public static void main(String[] args) {
        ThreadLocalRemoteControl instance1 = ThreadLocalRemoteControl.getInstance();
        ThreadLocalRemoteControl instance2 = ThreadLocalRemoteControl.getInstance();
        System.out.println(instance1 == instance2);// ✅ 같은 스레드에서는 true


        new Thread(() -> {
            ThreadLocalRemoteControl instance3 = ThreadLocalRemoteControl.getInstance();
            System.out.println(instance1 == instance3); // ✅ 다른 스레드에서는 false
        }).start();
    }
}
class ThreadLocalRemoteControl{
    private static final ThreadLocal<ThreadLocalRemoteControl> INSTANCE = ThreadLocal.withInitial(ThreadLocalRemoteControl::new);
    private ThreadLocalRemoteControl() {}

    public static ThreadLocalRemoteControl getInstance() {
        return INSTANCE.get();
    }
}
