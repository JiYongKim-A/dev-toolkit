package dev.toolkit.effective_java.service_provider_framework;

/**
 * [ 서비스 제공자 인터페이스 (SPI) 구현체 ] - ( SPF(Service Provider Framework) 선택 요소 )
 * 실제 서비스 구현체 객체를 생성하는 방식 정의
 */
public class MyServiceImplAProvider implements MyServiceProvider{
    private static final MyService INSTANCE = new MyServiceImplA();

    @Override
    public Class<? extends MyService> getServiceType() {
        return MyServiceImplA.class;
    }

    @Override
    public MyService create() {
        return new MyServiceImplA();
    }

}
