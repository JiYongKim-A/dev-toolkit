package dev.toolkit.effective_java.item.item3_singleton.generic_singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericSingletonMain {
    public static void main(String[] args) {
        // 팩토리를 통해 인터페이스 기반으로 싱글톤 객체 가져오기
        RemoteControl tvRemote = RemoteControlFactory.getRemote(RemoteType.TV);
        RemoteControl acRemote = RemoteControlFactory.getRemote(RemoteType.AIR_CONDITIONER);

        // 동일 객체인지 확인 (싱글톤 유지 확인)
        System.out.println(tvRemote == RemoteControlFactory.getRemote(RemoteType.TV)); // true
        System.out.println(acRemote == RemoteControlFactory.getRemote(RemoteType.AIR_CONDITIONER)); // true
        System.out.println(tvRemote == acRemote); // false (다른 타입이므로 다름)

        // 리모컨 사용 테스트
        tvRemote.control();
        acRemote.control();
    }
}

// 리모컨 타입을 관리하는 Enum
enum RemoteType {
    TV, AIR_CONDITIONER
}

// 인터페이스 (공통 기능 정의)
interface RemoteControl {
    void control();
}


// TV 리모컨 (싱글톤 유지)
class TVRemoteControl implements RemoteControl {
    private static final TVRemoteControl INSTANCE = new TVRemoteControl();

    private TVRemoteControl() {
    } // private 생성자

    public static TVRemoteControl getInstance() {
        return INSTANCE;
    }

    @Override
    public void control() {
        System.out.println("TV 리모컨 작동");
    }
}

// 에어컨 리모컨 (싱글톤 유지)
class AirConditionerRemoteControl implements RemoteControl {
    private static final AirConditionerRemoteControl INSTANCE = new AirConditionerRemoteControl();

    private AirConditionerRemoteControl() {
    } // private 생성자

    public static AirConditionerRemoteControl getInstance() {
        return INSTANCE;
    }

    @Override
    public void control() {
        System.out.println("에어컨 리모컨 작동");
    }
}


// 팩토리: 인터페이스 기반으로 싱글톤 객체 관리
class RemoteControlFactory {
    private static final Map<RemoteType, RemoteControl> instances = new ConcurrentHashMap<>();

    private RemoteControlFactory() {
    }

    public static RemoteControl getRemote(RemoteType type) {
        return instances.computeIfAbsent(type, key -> {
            switch (key) {
                case TV:
                    return TVRemoteControl.getInstance();
                case AIR_CONDITIONER:
                    return AirConditionerRemoteControl.getInstance();
                default:
                    throw new IllegalArgumentException("Unknown remote type: " + type);
            }
        });
    }
}