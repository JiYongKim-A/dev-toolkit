package dev.toolkit.effective_java.service_provider_framework;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [ 서비스 접근 API ] - ( SPF(Service Provider Framework) 필수 요소 )
 * 클라이언트는 이 API를 통해 구현체(서비스)를 선택해 사용할 수 있다.
 * 이 클래스는 두 가지 방식의 SPI 패턴을 제공
 * 1. 일반적인 SPI 패턴: 매번 새로운 인스턴스를 생성 (무상태 서비스에 적합)
 * 2. 싱글턴 SPI 패턴: 한 번 생성한 인스턴스를 캐싱해 재사용 (상태가 있거나 리소스가 무거운 서비스에 적합)
 */
public class MyServiceFactory {
    private static final ServiceLoader<MyServiceProvider> loader = ServiceLoader.load(MyServiceProvider.class);

    /**
     * [ 일반적인 SPI 패턴 (매번 새 인스턴스 생성) ] - 정적 팩토리 메서드 사용
     * - 각 요청마다 새로운 서비스 인스턴스를 생성한다.
     * - 상태가 없는 서비스에 적합하며, 멀티스레드 환경에서 각 요청이 독립적인 인스턴스를 사용
     * - 단점: 매번 새로운 객체를 생성하여 성능 저하 및 리소스 낭비 가능.
     *
     * @param serviceType 요청하는 서비스의 타입 (Class 객체)
     * @return 해당 타입의 서비스 인스턴스
     * @throws IllegalArgumentException 등록된 Provider 중 해당 타입이 없으면 발생
     */
    public static MyService getMyServiceOrdinary(Class<? extends MyService> serviceType) {
        for (MyServiceProvider provider : loader) {
            if (provider.getServiceType() == serviceType) {
                return provider.create();
            }
        }
        throw new IllegalArgumentException("Unknown Service Type : " + serviceType);
    }


    //------------------------------------------------------------------------------------------------------------------------------------


    // 싱글턴 패턴용 캐시: ConcurrentHashMap을 사용하여 개별 단일 연산(get, putIfAbsent 등)은 스레드 안전하게 처리.
    // 여기서는 서비스 타입을 키로 하여 해당 서비스 인스턴스를 캐싱.
    private static final Map<Class<? extends MyService>, MyService> serviceCache = new ConcurrentHashMap<>();
    private static final Object lock = new Object(); // 캐시 갱신 시 동기화를 위한 락 객체

    /**
     * 지정된 서비스 타입에 해당하는 Provider를 찾아,
     * Provider의 create()를 호출해 생성된 인스턴스를 캐시에 저장.
     * 이 메서드는 캐시에 없는 서비스에 대해 동적으로 Provider를 다시 로드(refresh)한다.
     *
     * @param serviceType 등록하고자 하는 서비스의 타입
     */
    private static void refreshServices(Class<? extends MyService> serviceType) {
        loader.reload(); // 새로운 서비스 제공자를 반영
        for (MyServiceProvider provider : loader) {
            if (provider.getServiceType() == serviceType) {
                MyService instance = provider.create();
                serviceCache.putIfAbsent(serviceType, instance); // 기존 서비스 덮어쓰지 않음
            }
        }
    }

    /**
     * [ 싱글턴을 제공하는 SPI 패턴 (캐싱과 재사용) ] - 정적 팩토리 메서드 사용
     * - 한 번 생성한 서비스 인스턴스를 캐싱하여 재사용
     * - 상태를 유지하거나 리소스 사용이 많은 서비스에 적합
     * - 단점: 싱글턴 인스턴스이므로 상태 변경 시 주의가 필요하며,
     * 복합 연산(get → refresh → get)은 멀티스레드 환경에서 적절한 동기화가 필요하다.
     *
     * @param serviceType 요청하는 서비스의 타입 (Class 객체)
     * @return 해당 타입의 캐싱된 싱글턴 서비스 인스턴스
     * @throws IllegalArgumentException 등록된 Provider 중 해당 타입이 없으면 발생
     */
    public static MyService getMyServiceBySingleton(Class<? extends MyService> serviceType) {
        // 1. 우선 캐시에서 해당 서비스 인스턴스를 조회 (비용이 낮은 O(1) 연산)
        MyService myService = serviceCache.get(serviceType);
        if (myService == null) {

            // 2. 캐시에 없으면, 복합 연산(get → refresh → get)이므로 동기화 블록을 사용해 원자적 처리
            synchronized (lock) {
                // 동기화 블록 내에서 다시 캐시를 조회 (다른 스레드가 먼저 refresh 했을 수 있으므로 동기화 블록 내에서 재확인)
                myService = serviceCache.get(serviceType);
                if (myService == null) {
                    // 캐시에 여전히 없으면, refreshServices()를 호출해 Provider를 통한 인스턴스 생성 및 캐시 갱신
                    refreshServices(serviceType);
                    // 갱신 후 다시 캐시에서 조회
                    myService = serviceCache.get(serviceType);
                }
            }
        }
        if (myService == null) {
            // 3. 캐시에서도 존재하지 않으면, 해당 서비스 타입의 Provider가 없는 것이므로 예외를 발생
            throw new IllegalArgumentException("Unknown Service Type: " + serviceType);
        }
        return myService;
    }

}
