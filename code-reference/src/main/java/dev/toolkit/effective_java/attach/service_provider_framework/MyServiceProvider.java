package dev.toolkit.effective_java.attach.service_provider_framework;

/**
 * [ 서비스 제공자 인터페이스 (SPI) 정의 ] - ( SPF(Service Provider Framework) 선택 요소 )
 * 구현체가 자신의 인스턴스를 생성하는 방법을 정의 인터페이스
 * 클라이언트 사용하는 서비스를 제공하기 위해 필요한 구현체들 즉 인스턴스를 생성할 때,
 * 리플랙션 없이도 공통된 방식으로 구현체를 생성
 */
public interface MyServiceProvider {

    Class<? extends MyService> getServiceType();

    MyService create();


}
