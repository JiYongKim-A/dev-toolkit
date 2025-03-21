package dev.toolkit.effective_java.attach.serialize.custom_serialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.UUID;

public class CustomSessionManager {
    private static final String SER_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/CustomSession.ser";
    private static final String JSON_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/CustomSession.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CustomSessionManager() {
    }

    // Java 기본 직렬화 (.ser - 바이너리)
    public static void saveCustomSessionAsSer(CustomUserSession session) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SER_SESSION_FILE)));
            out.writeObject(session);
            out.close();

            System.out.println("사용자 세션 저장 완료 : " + session);
            System.out.println("사용자 세션 파일 저장 위치: " + new File(SER_SESSION_FILE).getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // .ser 역직렬화 (.ser - 바이너리)
    public static CustomUserSession loadCustomSessionFromSer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(SER_SESSION_FILE)));
            CustomUserSession customUserSession = (CustomUserSession) ois.readObject();
            ois.close();

            System.out.println("사용자 세션 불러오기 완료 : " + customUserSession);
            return customUserSession;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CustomUserSession customUserSession = new CustomUserSession(UUID.randomUUID().toString(), "hooni", System.currentTimeMillis());

        System.out.println("------------------------------------------------");
        // ✅ 객체 직렬화 (.ser 파일에 저장 )
        CustomSessionManager.saveCustomSessionAsSer(customUserSession);
        System.out.println();

        // ✅ 객체 역직렬화 (.ser 파일에서 읽기)
        CustomUserSession loadCustomSessionFromSer = CustomSessionManager.loadCustomSessionFromSer();
        System.out.println(customUserSession == loadCustomSessionFromSer); // false -> 역직렬화를 통해 새로운 객체를 생성
        System.out.println(customUserSession.equals(loadCustomSessionFromSer)); // true -> (equals & hash 오버라이딩 완료) 서로 동일한 값을 가지고 있음
        System.out.println("------------------------------------------------");

    }
}
