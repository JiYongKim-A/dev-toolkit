package dev.toolkit.effective_java.service_provider_framework;

/**
 * [ 서비스 인터페이스 정의 ] ( SPF(Service Provider Framework) 필수 요소 )
 * 서비스의 동작을 정의하는 인터페이스
 * 클라이언트가 직접적으로 서비스를 실행할때 사용하는 인터페이스
 */

public interface MyService {
    void execute();
}
