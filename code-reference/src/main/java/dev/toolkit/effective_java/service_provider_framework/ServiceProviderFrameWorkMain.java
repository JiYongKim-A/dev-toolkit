package dev.toolkit.effective_java.service_provider_framework;

public class ServiceProviderFrameWorkMain {
    public static void main(String[] args) {

        // Ordinary SPI Patter
        MyService myServiceA = MyServiceFactory.getMyServiceOrdinary(MyServiceImplA.class);
        myServiceA.execute();

        MyService myServiceB = MyServiceFactory.getMyServiceOrdinary(MyServiceImplB.class);
        myServiceB.execute();

        // Singleton SPI Patter
        MyService singletonAService = MyServiceFactory.getMyServiceBySingleton(MyServiceImplA.class);
        singletonAService.execute();

        MyService singletonBService = MyServiceFactory.getMyServiceBySingleton(MyServiceImplB.class);
        singletonBService.execute();

    }
}
