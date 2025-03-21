package dev.toolkit.effective_java.attach.serialize.inheritance_serialize;

import java.io.*;
import java.util.Objects;
class Session {
    String sessionName;
    String purpose;

    // 기본 생성자 필요 (유효한 생성자가 없으면 InvalidClassException )
    public Session() {
    }

    public Session(String sessionName, String purpose) {
        this.sessionName = sessionName;
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionName='" + sessionName + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Session session = (Session) object;
        return Objects.equals(sessionName, session.sessionName) && Objects.equals(purpose, session.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionName, purpose);
    }
}

public class InheritanceUserSession extends Session implements Serializable {

    @Serial
    private static final long serialVersionUID = -2319022411096407636L;

    private String sessionId;
    private String username;
    private long lastAccessTime;

    public InheritanceUserSession(String sessionName, String purpose, String sessionId, String username, long lastAccessTime) {
        super(sessionName,purpose);
        this.sessionId = sessionId;
        this.username = username;
        this.lastAccessTime = lastAccessTime;
    }

    // 직렬화 동작 재정의
    @Serial
    private void writeObject(ObjectOutputStream outputStream) {
        try {
            System.out.println("[자바 상속 직렬화 - 부모 필드까지 직렬화]");
            // 부모 필드 직렬화
            outputStream.writeUTF(sessionName);
            outputStream.writeUTF(purpose);
            // 자신의 필드 직렬화
            outputStream.defaultWriteObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) {
        try {
            System.out.println("[자바 상속 역직렬화 - 부모 필드까지 역직렬화]");
            // 부모 필드 역직렬화
            super.sessionName = inputStream.readUTF();
            super.purpose = inputStream.readUTF();
            // 자신의 필드 역직렬화
            inputStream.defaultReadObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public String toString() {
        return "InheritanceUserSession{" +
                "sessionName='" + sessionName + '\'' +
                ", purpose='" + purpose + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        InheritanceUserSession that = (InheritanceUserSession) object;
        return getLastAccessTime() == that.getLastAccessTime() && Objects.equals(getSessionId(), that.getSessionId()) && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionId(), getUsername(), getLastAccessTime());
    }
}
