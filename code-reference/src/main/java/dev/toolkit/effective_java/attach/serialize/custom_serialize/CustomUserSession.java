package dev.toolkit.effective_java.attach.serialize.custom_serialize;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class CustomUserSession implements Serializable {

    @Serial
    private static final long serialVersionUID = -2319022411096407636L;

    private String sessionId;
    private String username;
    private long lastAccessTime;

//    writeObject()와 readObject() 메서드를 커스터마이즈하여 직접 직렬화 및 역직렬화 로직을 구현하고 있기 때문에
//      -> JVM은 기본 생성자를 호출할 필요가 없기 때문에 예외가 발생 하지 않는다.
//    public CustomUserSession() {
//    }

    public CustomUserSession(String sessionId, String username, long lastAccessTime) {
        this.sessionId = sessionId;
        this.username = username;
        this.lastAccessTime = lastAccessTime;
    }

    // 직렬화 동작 재정의
    @Serial
    private void writeObject(ObjectOutputStream outputStream) {
        String key = "privateKEY123456";
        String iv = "abcdef1234567890";
        // 모든 필드 AES 암호화
        try {
            System.out.println("[자바 커스텀 직렬화 - 암호화 버전]");
            outputStream.writeObject(AESUtils.encrypt(sessionId, key, iv));
            outputStream.writeObject(AESUtils.encrypt(username, key, iv));
            outputStream.writeObject(AESUtils.encrypt(String.valueOf(lastAccessTime), key, iv));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found error during deserialization", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during serialization/deserialization", e);
        }
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) {
        String key = "privateKEY123456";
        String iv = "abcdef1234567890";
        // 모든 필드 AES 복호화
        try {
            System.out.println("[자바 커스텀 역직렬화 - 암호화 버전]");
            this.sessionId = AESUtils.decrypt((String) inputStream.readObject(), key, iv);
            this.username = AESUtils.decrypt((String) inputStream.readObject(), key, iv);
            this.lastAccessTime = Long.parseLong(AESUtils.decrypt((String) inputStream.readObject(), key, iv));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found error during deserialization", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during serialization/deserialization", e);
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
        return "CustomUserSession{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CustomUserSession that = (CustomUserSession) object;
        return getLastAccessTime() == that.getLastAccessTime() && Objects.equals(getSessionId(), that.getSessionId()) && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionId(), getUsername(), getLastAccessTime());
    }
}

class AESUtils {
    private static final String ALGORITHM = "AES/CTR/NoPadding";
    private static final String ENCODING = "UTF-8";
    private static final String AES = "AES";

    /**
     * 입력받은 텍스트를 AES 알고리즘으로 암호화하고 Base64로 인코딩한 결과를 반환
     *
     * @param text : 암호화할 텍스트 (암호화하려는 원본 데이터)
     * @param key  : AES 암호화에 사용할 비밀 키 (일반 텍스트)
     * @param iv   : AES 암호화에 사용할 초기화 벡터 (일반 텍스트)
     * @return 암호화된 텍스트를 Base64로 인코딩한 문자열
     * @throws Exception 암호화 과정에서 발생할 수 있는 예외 (Cipher 초기화, Base64 디코딩/인코딩 오류 등)
     */
    public static String encrypt(String text, String key, String iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM); // AES 알고리즘을 사용하는 Cipher 객체

        // 일반 텍스트로 받은 key와 iv를 Base64로 디코딩하여 처리
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(ENCODING), AES); // key를 바이트로 변환하여 SecretKeySpec 객체 생성
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(ENCODING)); // iv를 바이트로 변환하여 IvParameterSpec 객체 생성

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec); // Cipher 객체를 암호화 모드로 초기화 (ENCRYPT_MODE)
        byte[] encrypted = cipher.doFinal(text.getBytes(ENCODING)); // 주어진 텍스트를 바이트 배열로 변환 후 암호화 (doFinal 메서드 사용)

        // 암호화된 바이트 배열을 Base64로 인코딩하여 문자열로 반환
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 암호화된 텍스트를 AES 알고리즘으로 복호화하고 원본 텍스트를 반환
     *
     * @param cipherText : 암호화된 텍스트 (Base64로 인코딩된 암호문)
     * @param key        : AES 복호화에 사용할 비밀 키 (일반 텍스트)
     * @param iv         : AES 복호화에 사용할 초기화 벡터 (일반 텍스트)
     * @return 복호화된 원본 텍스트 (복호화된 데이터)
     * @throws Exception 복호화 과정에서 발생할 수 있는 예외 (Cipher 초기화, Base64 디코딩/인코딩 오류 등)
     */
    public static String decrypt(String cipherText, String key, String iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM); // AES 알고리즘을 사용하는 Cipher 객체를 생성

        // 일반 텍스트로 받은 key와 iv를 Base64로 디코딩하여 처리
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(ENCODING), AES); // key를 바이트로 변환하여 SecretKeySpec 객체 생성
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(ENCODING)); // iv를 바이트로 변환하여 IvParameterSpec 객체 생성

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec); // Cipher 객체를 복호화 모드로 초기화 (DECRYPT_MODE)
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText); // 암호화된 Base64 문자열을 디코딩하여 바이트 배열로 변환
        byte[] decrypted = cipher.doFinal(decodedBytes); // 복호화된 바이트 배열을 원본 문자열로 변환

        // 복호화된 바이트 배열을 UTF-8로 변환하여 문자열로 반환
        return new String(decrypted, ENCODING);
    }
}
