package dev.toolkit.effective_java.attach.serialize.normal_serialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UserSession implements Serializable {

    @Serial // 직렬화 관련 필드나 메서드를 명확하게 지정하는 역할
    private static final long serialVersionUID = -8612465499720552608L;
    private final String sessionId;
    private final String username;
    private final long lastAccessTime;


//    public UserSession(String sessionId, String username, long lastAccessTime) {
//        this.sessionId = sessionId;
//        this.username = username;
//        this.lastAccessTime = lastAccessTime;
//    }

    // 필드가 모두 final 일때 기본 생성자가 없음 -> @JsonCreator 필요
    @JsonCreator
    public UserSession(@JsonProperty("sessionId") String sessionId,
                       @JsonProperty("username") String username,
                       @JsonProperty("lastAccessTime") long lastAccessTime) {
        this.sessionId = sessionId;
        this.username = username;
        this.lastAccessTime = lastAccessTime;
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
        return "UserSession{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserSession that = (UserSession) object;
        return lastAccessTime == that.lastAccessTime && Objects.equals(sessionId, that.sessionId) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, username, lastAccessTime);
    }
}
