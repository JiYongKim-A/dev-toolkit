package dev.toolkit.effective_java.attach.service_provider_framework;

/**
 * [ MyService 인터페이스의 구현체 A ]
 */
public class MyServiceImplA implements MyService{
    @Override
    public void execute() {
        System.out.println("Executing Service Implementation A");
    }
}
