# **인스턴스화를 막으려거든 `private` 생성자를 사용하라**

- **클래스의 인스턴스화(객체 생성)를 막아야 하는 경우**가 있다.
    - 특히, **유틸리티 클래스**나 **정적 메서드만 제공하는 클래스**는 인스턴스화할 필요가 없다.
    - 하지만 **기본 생성자를 명시하지 않으면,**

      **→ Java 컴파일러가 자동으로 `public` 기본 생성자를 추가**하여

      → 사용자가 실수로 객체를 생성할 수 있는 문제가 발생한다.


> **📌 해결책 → `private` 생성자를 사용하여 인스턴스화를 방지하면 된다.**
>

---

## **1️⃣ "인스턴스화를 막아야 하는 경우"**

📌 **인스턴스를 만들 필요가 없는 클래스란?**

1. **유틸리티 클래스 (Helper/Utils)**
    - 특정 연산을 수행하는 **정적 메서드만 포함**하는 클래스
    - 예: `Math`, `Collections`, `Arrays` 등 Java 표준 라이브러리

   <br>

2. **상수(Class Constants)만 포함하는 클래스**
    - 변하지 않는 **전역 상수 모음**
    - 예: `java.lang.Integer`, `java.lang.Double` 등의 `TYPE` 상수

   <br>

3. **싱글턴 패턴을 적용할 수 없는 경우**
    - 특정 객체를 하나만 생성해야 하는 경우
    - 그러나 싱글턴이 아니라, **그냥 인스턴스 생성 자체를 막아야 할 때**

---

## **2️⃣ "인스턴스화를 막지 않으면 생기는 문제"**

📌 **Java에서는 생성자를 명시하지 않으면 자동으로 기본 생성자가 추가됨**

```java
public class UtilityClass {
    // 생성자를 명시하지 않음
}
```

```java
UtilityClass obj = new UtilityClass(); // ❌ 원하지 않는 인스턴스 생성 가능
```

- Java 컴파일러가 **기본 생성자(매개변수 없는 `public` 생성자)를 자동으로 추가**
- API 사용자 입장에서 **"이 클래스는 객체를 만들어 사용하는 클래스"** 라고 오해할 수 있음

✅ **해결책 → `private` 생성자를 추가하여 인스턴스화를 막아야 함**

---

## **3️⃣ `private` 생성자를 사용하여 인스턴스화 방지하기**

📌 **`private` 생성자를 사용하면 외부에서 객체 생성을 차단할 수 있음.**

```java
public class UtilityClass {
    // 기본 생성자가 자동으로 생성되는 것을 막음 (인스턴스화 방지)
    private UtilityClass() {
        throw new AssertionError("This class cannot be instantiated.");
    }
}
```

```java
UtilityClass obj = new UtilityClass(); // ❌ 컴파일 오류 (생성자 접근 불가)
```

📌 **이 코드가 하는 일**

1. **생성자가 `private`이므로, 외부에서 `new UtilityClass()` 호출 불가**
2. **AssertionError를 던져서, 내부에서 실수로라도 객체를 생성하지 못하도록 방지**

✅ **이제 `UtilityClass`는 절대 인스턴스화될 수 없음!**

---

## **4️⃣ `private` 생성자를 사용하면 좋은 점**

### ✅ **1. 인스턴스화를 원천 차단하여 실수를 방지할 수 있다.**

- **원하지 않는 객체 생성 방지**
- **메모리 낭비 방지 (불필요한 객체 생성 차단)**

   <br>

### ✅ **2. "이 클래스는 인스턴스를 만들 필요가 없는 클래스"라는 의도를 명확히 표현할 수 있다.**

- API 사용자 입장에서 혼동을 방지할 수 있음
- 예를 들어, `java.lang.Math` 같은 클래스는 객체를 만들 이유가 없음

   <br>

### ✅ **3. 불필요한 상속을 방지할 수 있다.**

- **모든 생성자는 자동으로 상위 클래스의 생성자를 호출해야 함**
- 하지만 `private` 생성자는 **하위 클래스에서 호출할 수 없기 때문에 자동으로 상속이 차단됨**

📌 **예제: `private` 생성자를 사용하면 상속도 방지할 수 있음**

```java
public class UtilityClass {
    private UtilityClass() {} // ✅ private 생성자로 상속 차단
}

public class ChildClass extends UtilityClass { // ❌ 컴파일 오류 발생
}
```

---

## **5️⃣ `private` 생성자가 사용되는 실제 예제**

### 📌 **1. 유틸리티 클래스에서 활용**

- `java.lang.Math`, `java.util.Collections`, `java.util.Arrays` 등의 Java 표준 라이브러리에서 사용됨

```java
public class MathUtils {
    private MathUtils() {} // 🚨 인스턴스 생성 방지

    public static double sqrt(double value) {
        return Math.sqrt(value);
    }
}
```

```java
double result = MathUtils.sqrt(25); // ✅ 객체 생성 없이 사용 가능
```

✅ **객체를 만들 필요 없이 기능만 제공하는 클래스에 적합**

---

### 📌 **2. 상수 클래스에서 활용**

- 특정 값이 변하지 않는 **전역 상수 모음**

```java
public class Constants {
    public static final String APP_NAME = "My Application";
    public static final int MAX_USERS = 1000;

    private Constants() {} // 🚨 인스턴스 생성 방지
}
```

```java
System.out.println(Constants.APP_NAME); // ✅ 객체 없이 사용 가능
```

✅ **상수값만 관리하는 클래스는 객체를 생성할 필요가 없으므로, `private` 생성자로 차단**

---

### 📌 **3. 싱글턴 패턴에서 활용**

- 유일한 인스턴스를 보장하는 싱글턴 패턴을 구현할 때 활용됨

```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {} // 🚨 private 생성자로 인스턴스 제한

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

```java
Singleton obj = Singleton.getInstance(); // ✅ 객체를 직접 생성하지 않고, getInstance()로 접근
```

✅ **싱글턴 패턴에서는 `private` 생성자를 사용하여 객체 생성을 제한하고, 유일한 인스턴스만 반환할 수 있도록 함**

---

## **🔥 6. 결론: "인스턴스화를 막으려거든 `private` 생성자를 사용하라"**

✔ **객체를 생성할 필요가 없는 클래스에서는 `private` 생성자를 사용하여 인스턴스화를 방지해야 한다.**

✔ **이 방식은 객체 생성뿐만 아니라, 불필요한 상속까지 방지할 수 있다.**

✔ **유틸리티 클래스, 상수 클래스, 싱글턴 패턴에서 유용하게 활용된다.**

>✅ **즉, `private` 생성자는 "이 클래스는 인스턴스를 만들지 않는다"는 의도를 명확하게 전달하는 가장 좋은 방법이다!**