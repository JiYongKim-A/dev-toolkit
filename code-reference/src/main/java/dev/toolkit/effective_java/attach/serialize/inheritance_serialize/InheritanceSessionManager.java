package dev.toolkit.effective_java.attach.serialize.inheritance_serialize;

import java.io.*;
import java.util.UUID;

public class InheritanceSessionManager {
    private static final String SER_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/InheritanceSession.ser";

    public InheritanceSessionManager() {
    }

    // Java 기본 직렬화 (.ser - 바이너리)
    public static void saveInheritanceSessionAsSer(InheritanceUserSession session) {
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
    public static InheritanceUserSession loadInheritanceSessionFromSer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(SER_SESSION_FILE)));
            InheritanceUserSession inheritanceUserSession = (InheritanceUserSession) ois.readObject();
            ois.close();

            System.out.println("사용자 세션 불러오기 완료 : " + inheritanceUserSession);
            return inheritanceUserSession;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        InheritanceUserSession inheritanceUserSession = new InheritanceUserSession("LoginSession", "Login", UUID.randomUUID().toString(), "hooni", System.currentTimeMillis());

        System.out.println("------------------------------------------------");
        // ✅ 객체 직렬화 (.ser 파일에 저장 )
        InheritanceSessionManager.saveInheritanceSessionAsSer(inheritanceUserSession);
        System.out.println();

        // ✅ 객체 역직렬화 (.ser 파일에서 읽기)
        InheritanceUserSession loadInheritanceSessionFromSer = InheritanceSessionManager.loadInheritanceSessionFromSer();
        System.out.println(inheritanceUserSession == loadInheritanceSessionFromSer); // false -> 역직렬화를 통해 새로운 객체를 생성
        System.out.println(inheritanceUserSession.equals(loadInheritanceSessionFromSer)); // true -> (equals & hash 오버라이딩 완료) 서로 동일한 값을 가지고 있음
        System.out.println("------------------------------------------------");

    }
}
