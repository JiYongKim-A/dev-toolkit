package dev.toolkit.effective_java.attach.serialize.normal_serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.CodedOutputStream;
import dev.toolkit.effective_java.attach.serialize.protobuf.UserSessionProto;

import java.io.*;
import java.util.UUID;

public class NormalSessionManager {
    private static final String SER_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/Session.ser";
    private static final String JSON_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/Session.json";
    private static final String PROTO_SESSION_FILE = System.getProperty("user.dir") + "/src/main/resources/serialize/Session.proto.bin";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private NormalSessionManager() {
    }

    // Java 기본 직렬화 (.ser - 바이너리)
    public static void saveSessionAsSer(UserSession session) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SER_SESSION_FILE));
            oos.writeObject(session);
            oos.close();
            System.out.println("사용자 세션 저장 완료 : " + session);
            System.out.println("사용자 세션 파일 저장 위치: " + new File(SER_SESSION_FILE).getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // .ser 역직렬화 (.ser - 바이너리)
    public static UserSession loadSessionFromSer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SER_SESSION_FILE));
            UserSession userSession = (UserSession) ois.readObject();
            ois.close();
            System.out.println("사용자 세션 불러오기 완료 : " + userSession);
            return userSession;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //JSON 직렬화 (.json)
    public static void saveUserSessionAsJson(UserSession session) {
        try {
            objectMapper.writeValue(new File(JSON_SESSION_FILE), session);
            System.out.println("사용자 세션 저장 완료 : " + session);
            System.out.println("사용자 세션 파일 저장 위치: " + new File(JSON_SESSION_FILE).getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //JSON 역직렬화 (.json)
    public static UserSession loadUserSessionFromJson() {
        UserSession userSession = null;
        try {
            userSession = objectMapper.readValue(new File(JSON_SESSION_FILE), UserSession.class);
            System.out.println("사용자 세션 불러오기 완료 : " + userSession);
            return userSession;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void saveSessionAsProtobuf(UserSession session) {
        try (FileOutputStream fos = new FileOutputStream(PROTO_SESSION_FILE)) {
            CodedOutputStream codeOut = CodedOutputStream.newInstance(fos);
            UserSessionProto.UserSession protobufSession = UserSessionProto.UserSession.newBuilder()
                    .setSessionId(session.getSessionId())
                    .setUsername(session.getUsername())
                    .setLastAccessTime(session.getLastAccessTime())
                    .build();

            protobufSession.writeTo(codeOut);
            codeOut.flush();

            System.out.println("사용자 세션 (Protobuf) 저장 완료 : " + session);
            System.out.println("Protobuf 세션 파일 저장 위치: " + new File(PROTO_SESSION_FILE).getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ✅ Protobuf 역직렬화 (.proto)
    public static UserSession loadSessionFromProtobuf() {
        try (FileInputStream fis = new FileInputStream(PROTO_SESSION_FILE)) {
            UserSessionProto.UserSession protobufSession = UserSessionProto.UserSession.parseFrom(fis);
            return new UserSession(protobufSession.getSessionId(), protobufSession.getUsername(), protobufSession.getLastAccessTime());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        UserSession session = new UserSession(UUID.randomUUID().toString(), "chulsoo", System.currentTimeMillis());

        System.out.println("------------------------------------------------");
        // ✅ 객체 직렬화 (.ser 파일에 저장 )
        NormalSessionManager.saveSessionAsSer(session);
        System.out.println();

        // ✅ 객체 역직렬화 (.ser 파일에서 읽기)
        UserSession loadSessionBySer = NormalSessionManager.loadSessionFromSer();
        System.out.println(session == loadSessionBySer); // false -> 역직렬화를 통해 새로운 객체를 생성
        System.out.println(session.equals(loadSessionBySer)); // true -> (equals & hash 오버라이딩 완료) 서로 동일한 값을 가지고 있음
        System.out.println("------------------------------------------------");

        // ✅ 객체 직렬화 (.json 파일에 저장 )
        NormalSessionManager.saveUserSessionAsJson(session);
        System.out.println();

        // ✅ 객체 역직렬화 (.json 파일에서 읽기)
        UserSession loadSessionByJson = NormalSessionManager.loadUserSessionFromJson();
        System.out.println(session == loadSessionByJson); // false -> 역직렬화를 통해 새로운 객체를 생성
        System.out.println(session.equals(loadSessionByJson)); // true -> (equals & hash 오버라이딩 완료) 서로 동일한 값을 가지고 있음
        System.out.println("------------------------------------------------");

        // ✅ Protobuf 직렬화 (.proto 파일에 저장 )
        NormalSessionManager.saveSessionAsProtobuf(session);
        System.out.println();

        // ✅ Protobuf 역직렬화 (.proto 파일에서 읽기)
        UserSession loadSessionByProto = NormalSessionManager.loadSessionFromProtobuf();
        System.out.println(session == loadSessionByProto); // false -> 역직렬화를 통해 새로운 객체를 생성
        System.out.println(session.equals(loadSessionByProto)); // true -> (equals & hash 오버라이딩 완료) 서로 동일한 값을 가지고 있음
        System.out.println("------------------------------------------------");
    }
}
