# 1. 직렬화 (Serialize) 란?

📌 직렬화(Serialization)는

- **객체를** 바이트 스트림으로 변환**하여 파일, 네트워크, 데이터베이스 등에**

  **→ 저장하거나 전송할 수 있도록 하는 기술**이다.

  <br>

- **정확히 말하면, 직렬화(Serialization)는 객체를 "바이트 스트림"으로 변환하는 과정이며,**

  → 📌 **이 바이트 스트림을 저장하는 방법 중 하나가 파일(`.ser`, `.txt` 등)로 저장하는 것이다.**


> 즉
>
> - **JVM의 힙(heap) 또는 스택(stack) 메모리에 상주하는 객체를** 바이트 형태**로 변환하여**
>    
>    → 데이터베이스나 파일과 같은 외부 저장소에 저장 하고
> 
>  <br>
>
> - 다른 컴퓨터에서 이 파일을 가져와 **역직렬화**를 통해 
> 
>    → Java 객체로 변환하여 JVM 메모리에 적재하는 방법
>

---

# **2. 직렬화의 장점**

- **✅ 1. 자바 시스템에서 최적화된 방식**
    - ✔ 자바에서 기본 제공하는 기능이므로 별도의 라이브러리 없이 쉽게 사용 가능
    - ✔ `Serializable` 인터페이스만 구현하면 자동으로 직렬화 처리

   <br>

- **✅ 2. 복잡한 객체 구조도 그대로 저장 가능**
    - ✔ 기본 타입(`int`, `double`, `String`)뿐만 아니라
    - ✔ **컬렉션(`List`, `Map`, `Set`)이나 사용자 정의 객체까지 추가 변환 없이 저장 가능**

  > 💡 **JSON으로 저장하려면 데이터를 직접 매핑해야 하지만,**
  >
  >
  > **→ 직렬화는 별도 변환 없이 객체를 그대로 저장 가능!**

   <br>

- **✅ 3. 데이터베이스나 파일 저장 시 편리함**
    - ✔ 직렬화된 데이터를 DB나 파일에 저장하면,
    - ✔ **다시 불러올 때 타입 변환 없이 바로 객체로 사용 가능**

  > 💡 **객체의 필드 타입을 직접 매핑하지 않아도, 저장된 데이터만으로 원래 객체를 그대로 복원 가능!**

---

# **3. 역직렬화(Deserialization)란?**

> 역직렬화(Deserialization)는 **바이트 스트림을 다시 객체로 변환하는 과정**
>

## **📌 역직렬화가 필요한 이유**

1. **이전 상태를 복원하여 객체를 다시 사용할 수 있다.**
    - 예: 애플리케이션을 다시 실행해도 기존 설정을 불러오기 가능.

   <br>

2. **네트워크로 전송된 데이터를 다시 객체로 변환할 수 있다.**
    - 예: 서버에서 직렬화된 데이터를 받아 다시 객체로 변환하여 활용.

   <br>

3. **데이터베이스나 파일에서 저장된 객체를 다시 로드할 수 있다.**
    - 예: JSON, XML 등의 데이터 형식을 객체로 변환할 때.

<br>

## **📌 직렬화와 역직렬화를 안 하면 어떻게 되는가?**

> **객체를 저장하거나 전송할 때, 직렬화를 하지 않으면 다음과 같은 문제가 발생할 수 있다.**
>

### **🚨 직렬화하지 않으면 발생하는 문제**

1. **객체를 파일에 저장할 수 없음**
    - Java의 기본 객체는 직렬화되지 않으면 파일에 저장할 수 없음.

   <br>

2. **네트워크로 객체를 전송할 수 없음**
    - 예: Java RMI(Remote Method Invocation) 같은 원격 호출 기능을 사용할 수 없음.

   <br>

3. **데이터베이스에서 객체를 직접 저장하고 불러올 수 없음**
    - 객체를 JSON이나 XML로 변환해야 하는데, 직렬화를 사용하면 자동으로 변환 가능.

   <br>
    
---

# 4. 커스텀 직렬화와 상속에서의 직렬화

### **커스텀 직렬화 (Custom Serialization)**

### **`readObject` / `writeObject` 재정의**

- 기본적으로 `readObject()`와 `writeObject()`는 클래스의 모든 필드를 자동으로 직렬화 및 역직렬화

  → 하지만 해당 메서드를 재정의하면 특정 필드만 직렬화하거나, 직렬화 동작을 커스텀할 수 있음.

   <br>


### **예제: `Customer` 클래스에서 비밀번호 필드 제외**

```java
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

    public CustomUserSession() {
    }

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
```
<br>

---

# **5. 객체 상속 관계에서의 직렬화**

## **5-1. 부모가 `Serializable`을 구현한 경우**

- 자식 클래스는 별도로 `Serializable`을 구현하지 않아도 자동 직렬화됨.

<br>

## **5-2. 부모가 `Serializable`을 구현하지 않은 경우**

- 자식 클래스만 `Serializable`을 구현하면 **부모 클래스의 필드는 직렬화되지 않음**.
- 해결 방법:
    - 부모 클래스도 `Serializable`을 구현.
    - `writeObject()`와 `readObject()` 메서드를 재정의하여 부모 클래스의 필드를 직접 직렬화.

  > 단, 부모 클래스의 기본 생성자 필수
  >
  > - 객체를 역직렬화할때
  >     - 부모 클래스 부터 시작해서 상속 구조를 따라 내려간다.
  >     - 역직렬화하는 과정에서 직렬화되지 않은 부모의 속성 정보들을
  >
  >         → 기본 생성자를 통해서 가져오게 된다.
  >     
  >     <br>
  >
  >     - 그런데 만일 부모 클래스의 기본 생성자가 없다면
  >
  >         → 불러올 유효한 생성자(vaild constructor)가 없어서 예외가 터진다.

<br>

### **예제: 부모 필드까지 직렬화하는 방법**

```java
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

```

### **설명**

- `Session`은 `Serializable`을 구현하지 않지만,
- `InheritanceUserSession`에서
    - `writeObject()`와
    - `readObject()`를

      > 재정의하여 부모 필드를 직접 직렬화.

- 이를 통해 **부모 클래스의 필드도 직렬화 및 역직렬화 가능**.

---

# **6. 자바 직렬화 버전 관리 (SerialVersionUID)**

## **6-1. SerialVersionUID란?**

- `Serializable` 인터페이스를 구현하는 모든 클래스는

  → `serialVersionUID`(이하 **SUID**)라는 **고유 식별번호**를 가짐.

   <br>
  
    - 이 값은 직렬화 및 역직렬화 시 **클래스의 동일성을 확인**하는 용도로 사용됨.
    - 만약 클래스가 변경되었는데
        - `SUID` 값이 기존과 다르면,
        - 역직렬화 시 `InvalidClassException`이 발생하여

      > 버전 불일치를 방지

<br>

## **6-2. SerialVersionUID 자동 생성 방식**

- `SerialVersionUID`는 필수가 아니며, 명시적으로 선언하지 않으면
    - **JVM이 클래스의 이름, 필드 등을 이용해 자동 생성함.**
    - 하지만 클래스 내부 구성이 조금이라도 변경되면

      → **`SerialVersionUID`** 값이 바뀌어 **역직렬화 불가능** 문제가 발생함.


---

## **6-3. SerialVersionUID 없이 직렬화 및 역직렬화 예제**

```java
package dev.toolkit.effective_java.attach.serialize.normal_serialize;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UserSession implements Serializable {

    private String sessionId;
    private String username;
    private long lastAccessTime;

    public UserSession() {
    }

    public UserSession(String sessionId, String username, long lastAccessTime) {
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

```

### **[ 문제 발생 가능성 ]**

- 이후 `Session` 클래스에
    - 새로운 필드 (ex. date)를 추가하면
    - 기존에 저장된 `Session.ser` 파일을 읽을 때

      →  `InvalidClassException` 발생.


    > 기존 형태와 다르기 때문 + ( 내부적으로 생성하는 serialVersionUID값도 다름)
    

<br>

---

## **6-4. SerialVersionUID 명시하여 버전 관리**

### **📌 해결책: SUID를 직접 명시**

```java
package dev.toolkit.effective_java.attach.serialize.normal_serialize;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UserSession implements Serializable {

    @Serial // 직렬화 관련 필드나 메서드를 명확하게 지정하는 역할
    private static final long serialVersionUID = -8612465499720552608L;
    private String sessionId;
    private String username;
    private long lastAccessTime;

    public UserSession() {
    }

    public UserSession(String sessionId, String username, long lastAccessTime) {
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

```

### **✔️ 효과**

- `serialVersionUID`를 직접 지정하면 **클래스가 변경되어도 버전이 유지됨**.
- (ex. date) 필드가 추가되었더라도,
    - 기존 `Session.ser` 파일을 읽을 때

      **→** (ex. date) **새 필드는 `null` 값으로 초기화**되며

      → 정상적으로 역직렬화됨.


    > `serialVersionUID` 가 동일하기때문에 이외 새로 추가된 사항은 null 처리
    

---

## 6-5. **SerialVersionUID 관리 시 주의사항**

| 변경 사항 | 역직렬화 영향 여부 | 설명 |
| --- | --- | --- |
| 멤버 변수 추가 | ❌ 영향 없음 | 추가된 필드는 `null` 또는 기본값으로 초기화됨 |
| 멤버 변수 삭제 | ❌ 영향 없음 | 직렬화되지 않으므로 영향 없음 |
| 멤버 변수 이름 변경 | ❌ 영향 없음 | 기존 데이터와 매칭되지 않아 기본값 사용 |
| 멤버 변수 접근 제어자 변경 | ❌ 영향 없음 | `private → public` 변경 등은 영향 없음 |
| 멤버 변수 타입 변경 | ✅ **영향 있음** | 예: `int → long` 변경 시 `InvalidClassException` 발생 |
| `static` 또는 `transient` 추가 | ❌ 영향 없음 | `static`과 `transient` 변수는 원래 직렬화 대상이 아님 |

✅ **`SerialVersionUID`를 직접 명시하면 클래스를 변경해도 직렬화 버전이 유지됨**

✅ **새로운 필드는 `null` 또는 기본값으로 초기화되며 역직렬화 가능**

✅ **멤버 변수 타입 변경은 역직렬화 오류를 유발할 수 있으므로 주의**

✅ **자주 변경될 클래스는 직렬화를 피하는 것이 좋음**

---

# 7. 직렬화의 예외

## **7-1. `InvalidClassException` 예외**

> `InvalidClassException`은 직렬화 및 역직렬화 과정에서 **클래스의 불일치 문제**가 발생할 때 발생하는 예외


### **발생 원인**

1. **클래스의 `serialVersionUID`가 다를 때**
    - 클래스 버전이 변경되었지만 `serialVersionUID`를 명시하지 않으면,

      → 시스템이 자동 생성한 값이 달라져 오류 발생.

        - 해결책: **`serialVersionUID`를 명시적으로 선언**하여 버전 충돌을 방지.

   <br>

2. **클래스 내부 필드의 데이터 타입이 변경될 때**
    - 예를 들어, `int` → `long` 변경 시 역직렬화 오류 발생.
    - 해결책: 데이터 타입 변경 시 **신중하게 설계**하거나 새로운 필드는 추가하는 방식으로 관리.

   <br>

3. **기본 생성자가 없을 때 (`no valid constructor`)**
    - 상속 관계에서 부모 클래스가 `Serializable`을 구현하지 않은 경우, 기본 생성자가 필요함.
    - 부모 클래스의 속성을 기본 생성자로 초기화해야 하지만, 기본 생성자가 없으면 역직렬화 시 오류 발생.


<br>

---

### **7-2. `InvalidClassException : no valid constructor` 예외**

- **발생 조건**: 부모 클래스가 `Serializable`을 구현하지 않았고, 기본 생성자가 없을 경우.
- **발생 이유**:
    - 역직렬화 과정에서 부모 클래스부터 복원해야 하는데,

      → 부모가 `Serializable`하지 않으면 기본 생성자를 통해 필드를 초기화해야 함.

      → 이때 **기본 생성자가 없으면 객체를 생성할 방법이 없어 오류 발생**.

<br>

---

### **7-3. `NotSerializableException` 예외**

- **발생 조건**: 직렬화 가능한 클래스 내부에 **직렬화되지 않은 객체**를 필드로 포함할 경우.
- **발생 이유**:
    - `Serializable`을 구현한 클래스가 **직렬화되지 않은 객체**를 포함하면, 해당 필드에서 직렬화가 중단되고 `NotSerializableException`이 발생.

### **🚨 예제: `NotSerializableException` 발생**

```java
import java.io.*;
import java.util.Objects;

// Session 클래스는 Serializable을 구현하지 않음
class Session {
    String sessionName;
    String purpose;

    // 기본 생성자 필요 (유효한 생성자가 없으면 InvalidClassException 발생)
    public Session() {
    }

    public Session(String sessionName, String purpose) {
        this.sessionName = sessionName;
        this.purpose = purpose;
    }
}

// Logindata 클래스 (Session 객체 포함)
class Logindata implements Serializable {
    String name;
    int age;

    // Session 객체 포함 (Session은 Serializable을 구현하지 않음)
    Session session;
}

public class Main {
    public static void main(String[] args) {
        try {
            Logindata loginData = new Logindata();
            loginData.name = "suji";
            loginData.age = 5;
            // Session 객체 포함
            loginData.session = new Session("User Session", "Authentication");  

            String fileName = "Logindata.ser";
            ObjectOutputStream out = new ObjectOutputStream(
	            new FileOutputStream(fileName));

            // 직렬화 시 오류 발생 (NotSerializableException)
            out.writeObject(loginData);  // Session이 직렬화되지 않아서 오류 발생
            out.close();

        } catch (NotSerializableException e) {
            System.out.println("직렬화 오류 발생: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

**🔴 실행 결과**

```
java.io.NotSerializableException: NonSerializableClass
```

### **✅ 해결책 1: `Serializable` 구현 추가**

- `NonSerializableClass`가 `Serializable`을 구현하면 해결됨.

```java
class Session implements Serializable {
		String sessionName;
    String purpose;
}
```

### **✅ 해결책 2: `transient` 키워드 사용**

- `transient` 키워드를 사용하면 해당 필드는 **직렬화 대상에서 제외됨**.

```java
class Logindata implements Serializable {
    String name;
    int age;

    transient Session session;  // 직렬화에서 제외
}
```

- `transient`로 설정된 필드는 **역직렬화 시 `null`로 초기화**됨.

---

## **7-4. 예외 정리**

| **예외 유형** | **발생 원인** | **해결 방법** |
| --- | --- | --- |
| `InvalidClassException` | `serialVersionUID` 불일치 | `serialVersionUID` 명시 |
| `InvalidClassException` 
(`no valid constructor`) | 부모 클래스가 `Serializable`이 아니고 
기본 생성자가 없음 | 기본 생성자 추가 |
| `NotSerializableException` | 직렬화되지 않은 객체를 포함 | `Serializable` 구현 또는 `transient` 사용 |

✅ **기본 생성자는 꼭 추가!**

✅ **직렬화할 클래스 내부 객체도 `Serializable` 구현 필요!**

✅ **필요 없는 필드는 `transient`로 제외 가능!**

---

# 8. 직렬화의 문제점

## **8-1. 직렬화는 용량이 크다**

- **직렬화된 객체**에는 **데이터 값뿐만 아니라 타입 정보, 클래스 메타 정보**까지 포함됨.
- 같은 데이터를 **JSON으로 저장하는 경우보다 직렬화된 데이터 크기가 2배 이상** 차이 날 수 있음.
- **DB나 캐시(Cache) 등 장기간 저장할 데이터에는 직렬화를 지양하는 것이 좋음**.
- 직렬화 대신 **JSON, XML, Protocol Buffers**와 같은 더 가벼운 데이터 포맷을 사용하는 것이 효과적.

✅ **대안**: JSON을 활용한 직렬화 예제 (Jackson 사용)

```java
ObjectMapper mapper = new ObjectMapper();
String json = mapper.writeValueAsString(user);
User userObj = mapper.readValue(json, User.class);
```

---

## **8-2. 역직렬화는 보안에 취약하다**

- `ObjectInputStream.readObject()`를 호출하면 **클래스 패스 내 모든 객체를 역직렬화하여 생성**할 수 있음.
- 역직렬화 공격(Deserialization Attack)을 통해

  → 악의적인 객체가 생성될 가능성이 있음.


- 중간자 공격(Man-in-the-Middle Attack)으로 직렬화된 데이터를 조작하면,

  → 예상치 못한 값이 인스턴스 필드에 대입될 수도 있음.


- **객체는 생성자 없이 인스턴스화될 수 있어 불변성이 깨질 위험**이 있음.

<br>

✅ **해결책**:

- **신뢰할 수 없는 데이터를 절대 역직렬화하지 말 것!**
- `ObjectInputFilter`를 사용해 허용된 클래스만 역직렬화.
- `readObject()` 메서드에서 검증 로직 추가.

```java
private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    if (age < 0) {
        throw new InvalidObjectException("나이는 0보다 커야 합니다.");
    }
}
```

---

## **8-3. 직렬화를 피할 수 없다면 방어 기법 적용**

**직렬화를 완전히 피할 수 없는 경우, 역직렬화 방어 기법을 적용해야 함.**

- **`ObjectInputFilter` 사용**: 특정 클래스만 허용하거나 차단.
- **`readResolve()` 메서드 활용**: 역직렬화 후 안전한 객체 반환.
- **`transient` 키워드 사용**: 민감한 정보 직렬화 제외.

---

## **8-4.  릴리즈 후 수정이 어렵다**

- 직렬화된 객체는 바이트 스트림 형식이 하나의 **공개 API**가 됨.
- 직렬화된 객체가 널리 사용되면 **직렬화 형식도 영원히 유지해야 함**.
- 즉, 클래스를 수정하면 기존 직렬화된 데이터와 호환성이 깨질 위험이 있음.

✅ **해결책**:

- `serialVersionUID`를 명시하여 역직렬화 오류 방지.
- 새로운 필드를 추가할 때 **기존 데이터를 유지하도록 기본값 처리**.

```java
class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String address; // 새 필드 추가 (기본값 처리)
}
```

---

## **8-5. 클래스 캡슐화(`Encapsulation`)가 깨진다**

- `Serializable`을 구현한 클래스는 **private 필드도 외부에 노출됨**.
- 직렬화된 데이터를 조작하면 private 필드 값을 변경할 수 있음.

✅ **해결책**:

- **`transient` 키워드 사용**: 민감한 정보 보호.
- **`readObject()` 검증 추가**: 필드 무결성 확인.

```java
class User implements Serializable {
    private String username;
    private transient String password; // 직렬화 제외
}
```

---

## **8-6. 버그와 보안 취약점 증가**

- 직렬화된 객체는 **생성자를 호출하지 않고 직접 생성**됨.
- 이로 인해 **불변성 보장 코드가 무력화될 가능성**이 있음.

<br>

✅ **해결책**:

- `readObject()` 내부에서 필드 검증 로직 추가.
- **직렬화 프록시 패턴(Serialization Proxy Pattern)** 적용.

```java
class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Member(String name, int age) {
        if (age < 0) throw new IllegalArgumentException();
        this.name = name;
        this.age = age;
    }
}
```

위 코드에서 `Member` 객체를 직렬화 후 역직렬화하면, **음수 나이(`age < 0`)도 허용됨**. 이를 막기 위해 `readObject()` 내부에서 검증 로직을 추가해야 함.

---

## **8-7. 새로운 버전 릴리즈 시 테스트 부담 증가**

- 직렬화 가능한 클래스를 수정하면, **이전 버전과의 호환성을 유지해야 함**.
- 기존 직렬화된 데이터를 역직렬화할 수 있는지 **버전별 테스트가 필요**.

✅ **해결책**:

- **JSON, XML 같은 형식 사용** → 데이터 구조가 변경되어도 유연하게 대응 가능.

---

# **9. 직렬화를 구현할지 신중히 결정해야 하는 이유**

## **9-1. 값 클래스만 직렬화를 고려**

- **값을 표현하는 클래스(`BigInteger`, `Instant`)** → 직렬화 가능 (`Serializable` 구현)
- **동작을 표현하는 클래스(`ThreadPoolExecutor`)** → 직렬화 지양

> 결론: "값"을 나타내는 클래스는 직렬화, "동작"을 나타내는 클래스는 직렬화 X
>

---

## **9-2. 상속(`Inheritance`)용 클래스는 직렬화를 피해야 한다**

- 부모 클래스가 `Serializable`을 구현하면, **하위 클래스도 직렬화의 위험성을 그대로 떠안음**.
- 즉, 직렬화된 데이터가 부모 클래스를 타고 확장될 수 있어 보안 문제가 발생할 가능성이 있음.

✅ **해결책**:

- `readObjectNoData()`를 정의하여 기본값을 제공.
- `finalize()`를 `final`로 선언하여 하위 클래스에서 재정의 못 하도록 설정.

---

## **9-3. 내부 클래스는 직렬화를 피해야 한다**

- **내부 클래스(`Inner Class`)는 직렬화하면 안 됨** → 컴파일러가 내부적으로 숨겨진 필드를 추가하여 직렬화 형식이 불명확함.
- **해결책**: **정적 내부 클래스(`static inner class`)**만 직렬화 가능.

```java
class OuterClass {
    class InnerClass implements Serializable { // ❌ 직렬화 비권장
    }

    static class StaticInnerClass implements Serializable { // ✅ 가능
    }
}
```

## **9-4. 문제점 요약**

| **문제점** | **설명** | **해결책** |
| --- | --- | --- |
| **큰 데이터 용량** | 직렬화된 데이터가 크고 비효율적 | JSON 같은 포맷 사용 |
| **보안 취약점** | 역직렬화 공격 가능 | 역직렬화 필터링 적용 |
| **캡슐화 깨짐** | private 필드도 외부에 노출됨 | `transient` 키워드 사용 |
| **릴리즈 후 수정 어려움** | 직렬화 형식이 API로 고정됨 | 신중한 설계 필요 |
| **내부 클래스 직렬화 문제** | 직렬화 형식이 불명확함 | 정적 내부 클래스(`static`)만 직렬화 |

---

# **10. 📌 직렬화 사용 시점**

| **상황** | **직렬화 사용 여부** | **대안** |
| --- | --- | --- |
| 네트워크 전송 (같은 JVM) | ✅ 사용 가능 | JSON, Protobuf |
| 객체의 단기 저장 (세션, 캐시) | ✅ 사용 가능 | JSON, Redis |
| 자바 기본 라이브러리 요구 (RMI, JMS 등) | ✅ 사용 필수 | 대안 없음 |
| **장기적인 데이터 저장 (DB, 로그, 파일)** | ❌ 사용 지양 | JSON, XML, Protobuf |
| **보안이 중요한 데이터** | ❌ 사용 지양 | JSON + 데이터 검증 |
| **내부 클래스 직렬화** | ❌ 사용 지양 | 정적 내부 클래스 사용 |

---

## **직렬화를 언제 써야 할까?**

✅ **직렬화를 사용해야 하는 경우**

1. **네트워크 통신(RMI, JMS, EJB)에서 객체를 주고받을 때**
2. **세션이나 캐시처럼 단기간 데이터를 유지할 때**
3. **자바의 특정 라이브러리에서 `Serializable`을 요구할 때**
4. **게임 상태 저장 등 단기적으로 객체를 저장할 때**

🚫 **직렬화를 피해야 하는 경우**

1. **장기간 데이터를 저장할 때(DB, 로그 파일) → JSON, XML 사용**
2. **보안이 중요한 데이터를 다룰 때 → JSON + 검증**
3. **내부 클래스를 직렬화할 때 → 정적 클래스 사용**