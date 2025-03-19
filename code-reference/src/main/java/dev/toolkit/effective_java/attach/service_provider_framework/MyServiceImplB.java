package dev.toolkit.effective_java.attach.service_provider_framework;

/**
 * [ MyService 인터페이스의 구현체 B ]
 */
public class MyServiceImplB implements MyService{
    @Override
    public void execute() {
        System.out.println("Executing Service Implementation B");
    }
}
