package dev.toolkit.effective_java.item.item4_private_cunstructor;

public class PrivateConstructorExample {
    public static void main(String[] args) {
        UtilityV1 utilityV1 = new UtilityV1(); // 객체 생성 가능

        //UtilityV2 utilityV2 = new UtilityV2(); // 컴파일에러 -> 생성자가 private

        //MathUtil mathUtil = new MathUtil();// 컴파일에러 -> 생성자가 private
        MathUtil.sqrt(10);

        //Constants constants = new Constants();// 컴파일에러 -> 생성자가 private
        String appName = Constants.APP_NAME;
        int maxUsers = Constants.MAX_USERS;

        //Singleton singleton = new Singleton();// 컴파일에러 -> 생성자가 private
        Singleton instance = Singleton.getInstance();
    }

}

class UtilityV1 {
    // 생성자 명시 ❌ -> 기본 생성자가 자동으로 생성됨
}

class UtilityV2 {
    // 기본 생성자가 자동으로 생성되는 것을 막음 ( ❌인스턴스화 방지 )
    // 생성자 호출 불가 ( ❌ 상속 방지 )
    private UtilityV2() {
    }
}

class MathUtil {
    private MathUtil() {
    }

    public static double sqrt(int val) {
        return Math.sqrt(val);
    }
}

class Constants {
    public static final String APP_NAME = "App";
    public static final int MAX_USERS = 1000;

    private Constants() {
    } // 🚨 인스턴스 생성 방지
}

class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    } // 🚨 private 생성자로 인스턴스 제한

    public static Singleton getInstance() {
        return INSTANCE;
    }
}