# **📌  `Supplier<T>`(공급자)**

---

## **✅ 1. `Supplier<T>`란?**

- **Java의 함수형 인터페이스 중 하나**
- **입력 없이(`()`), 특정 타입(`T`)의 값을 반환하는 역할**
- 즉, **어떤 값을 "공급(supply)"하는 인터페이스**

   <br>

✅ **`Supplier<T>`의 핵심:**

- 입력 값 없이 `T` 타입의 객체를 반환
- 값이 필요할 때마다 `get()`을 호출하여 값을 제공

   <br>

✅ `Supplier<T>`는 객체 생성 및 공급을 담당하는 함수형 인터페이스 이다.

📌 이를 활용하면 **코드의 유연성, 유지보수성, 확장성** 이 크게 향상된다.

---

## **✅ 2. `Supplier<T>` 기본 구조**

```java
@FunctionalInterface
public interface Supplier<T> {
    T get(); // 값을 반환하는 메서드
}
```

✅ `Supplier<T>`는 **`get()` 메서드를 제공하며, 호출 시 `T` 타입의 값을 반환**

✅ **입력 없이(`()`), 특정 값을 반환하는 역할을 수행**

- **@FunctionalInterface** 란?

  ### **🚀 `@FunctionalInterface`는 꼭 써야 할까?**

  ### **✅ `@FunctionalInterface`란?**

    - `@FunctionalInterface`는 **자바의 함수형 인터페이스를 명확하게 표시하는 애너테이션**
    - **하나의 추상 메서드만 가지는 인터페이스를 함수형 인터페이스로 정의할 때 사용**
    - **컴파일러가 함수형 인터페이스의 조건을 강제하도록 도와줌**

    ```java
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }
    ```
    
  ---

  ### **1️⃣ `@FunctionalInterface`는 필수가 아니다!**

  **📌 `@FunctionalInterface`를 생략해도 함수형 인터페이스는 정상적으로 동작한다.**

  즉, **이 애너테이션은 "선택 사항"이다.**

    ```java
    public interface MySupplier<T> {
        T get(); // 함수형 인터페이스의 조건(추상 메서드 1개) 충족
    }
    ```

  ✅ **위 코드도 문제없이 함수형 인터페이스로 동작한다.**
    
  ---

  ### **2️⃣ `@FunctionalInterface`를 사용하는 이유**

  ✅ **코드의 명확성**

    - **"이 인터페이스는 함수형 인터페이스입니다"라는 의도를 명확히 전달할 수 있음.**
    - 다른 개발자가 코드를 읽을 때 **람다 표현식(`()->`)을 사용할 수 있는 인터페이스라는 점을 쉽게 알 수 있음.**

   <br>

  ✅ **컴파일러의 검증 기능 제공**

    - **추상 메서드가 2개 이상 추가되면 컴파일 오류 발생!**
    - 즉, **실수로 추가적인 메서드를 정의하는 실수를 방지할 수 있음.**

    ```java
    @FunctionalInterface
    public interface InvalidSupplier<T> {
        T get();
        void otherMethod(); // 🚨 컴파일 오류 발생! (함수형 인터페이스 조건 위반)
    }
    ```

  > 📌 `@FunctionalInterface`를 사용하면 **컴파일러가 자동으로 "함수형 인터페이스의 조건"을 검증해준다.**
    
  ---

  ### **3️⃣ 결론: 언제 `@FunctionalInterface`를 써야 할까?**

  ✅ **반드시 써야 하는 것은 아니지만, 사용하는 것이 좋다!**

   <br>

  ✅ **특히, 함수형 인터페이스로 사용될 인터페이스라면,**

  **→ `@FunctionalInterface`를 추가하면 코드의 의도를 명확히 할 수 있음.**

   <br>

  ✅ **추상 메서드가 2개 이상 추가되는 실수를 방지할 수 있음.**

   <br>

  >📌 **즉, "필수는 아니지만, 사용하면 코드가 더 안전하고 가독성이 좋아진다!"**

<br>

---

## **✅ 3. `Supplier<T>`의 사용법**

### **📌 기본적인 `Supplier` 활용 예제**

```java
import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        // ✅ Supplier 사용: 문자열 공급
        Supplier<String> stringSupplier = () -> "Hello, Supplier!";

        // ✅ Supplier 호출
        System.out.println(stringSupplier.get()); // 출력: Hello, Supplier!
    }
}
```

📌 **Supplier를 사용하면 입력 없이 특정 값을 반환하는 함수를 쉽게 정의할 수 있음.**

📌 **데이터베이스 호출, 싱글턴 반환, Lazy Initialization 등 다양한 활용 가능.**

---

## **✅ 4. `Supplier<T>`를 활용한 싱글턴 패턴**

### **📌 `Supplier`를 활용한 싱글턴 패턴**

```java
import java.util.function.Supplier;

public class Elvis {
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {} // private 생성자 → 외부에서 직접 생성 불가능

    public static Elvis getInstance() {
        return INSTANCE;
    }
}

public class Main {
    public static void main(String[] args) {
        // ✅ `Supplier`를 사용하여 싱글턴 객체 가져오기
        Supplier<Elvis> elvisSupplier = Elvis::getInstance;

        // ✅ Supplier를 통해 싱글턴 객체 가져오기
        Elvis elvis1 = elvisSupplier.get();
        Elvis elvis2 = elvisSupplier.get();

        System.out.println(elvis1 == elvis2); // ✅ true (같은 객체 반환)
    }
}
```

📌 **정적 메서드 참조(`Elvis::getInstance`)를 `Supplier`로 활용하여 객체를 반환 가능**

   <br>

📌 **이제 `getInstance()`를 직접 호출하지 않고도 `Supplier`를 통해 유연하게 객체 공급 가능**

---

## **✅ 5. `Supplier`를 사용하는 방법: `()->`(람다) vs `::`(메서드 참조)**

### **📌 (1) `()->` (람다 표현식)**

- **익명 함수(Anonymous Function)로 `Supplier`를 구현하는 방법**
- **형식:**

    ```java
    Supplier<T> supplier = () -> { return someValue; };
    ```


- **예제: `Supplier`를 람다식으로 구현하기**

    ```java
    import java.util.function.Supplier;
    
    public class Main {
        public static void main(String[] args) {
            // ✅ 람다식으로 Supplier 구현
            Supplier<String> messageSupplier = () -> "Hello from Supplier!";
    
            // ✅ Supplier 호출
            System.out.println(messageSupplier.get()); // 출력: Hello from Supplier!
        }
    }
    ```

  📌 **`()->` 함수형 프로그래밍 문법**

  📌 **람다식으로 간결하게 `Supplier`를 정의할 수 있음.**


---

### **📌 (2) `::` (메서드 참조)**

- **이미 존재하는 메서드를 `Supplier`로 참조하는 방식**
- **형식:**

    ```java
    Supplier<T> supplier = ClassName::staticMethod;
    ```

- **예제: 정적 메서드를 `Supplier`로 참조하기**

    ```java
    import java.util.function.Supplier;
    
    public class Elvis {
        private static final Elvis INSTANCE = new Elvis();
        private Elvis() {}
    
        public static Elvis getInstance() {
            return INSTANCE;
        }
    }
    
    public class Main {
        public static void main(String[] args) {
            // ✅ 정적 메서드 참조를 Supplier로 사용
            Supplier<Elvis> elvisSupplier = Elvis::getInstance;
    
            // ✅ Supplier 호출
            Elvis elvis = elvisSupplier.get();
            System.out.println(elvis);
        }
    }
    ```


📌 **`::`는 함수형 프로그래밍 문법**

📌 **람다식(`()->`)과 다르게, 이미 존재하는 메서드를 직접 `Supplier`로 사용할 수 있음.**

---

## **✅ 6. `Supplier<T>`를 사용하면 뭐가 좋아지는가?**

📌 `Supplier<T>`는 **객체 생성 및 공급을 담당하는 함수형 인터페이스**이다.

📌 이를 활용하면 **코드의 유연성, 유지보수성, 확장성**이 크게 향상된다.

📌 **어떤 점이 좋은지 하나씩 자세히 알아보자.**

---

### **✅ 1️⃣ 코드의 유연성이 증가한다 (객체 공급 방식을 쉽게 변경 가능)**

**[ 📌 기존 방식: `new` `getInstance()`를 직접 호출하는 경우 ]**

```java
public class Main {
    public static void main(String[] args) {
		    Elvis elvis = new Elvis(); // 원래 객체 생성
        Elvis elvis = Elvis.getInstance(); // 원래 객체 생성
    }
}
```
   <br>

📌 **문제점:**

- 객체를 **고정된 방식으로만** 가져올 수 있음
- Mock 객체를 사용하거나, 다른 방식으로 객체를 생성하려면 **코드 전체를 수정해야 함**

   <br>

**[ 📌 `Supplier<T>`를 사용한 방식 ]**

```java
// ✅ Elvis 객체를 공급하는 Supplier
Supplier<Elvis> elvisSupplier = Elvis::getInstance;

 // ✅ 원본 Elvis 객체 사용
Elvis originalElvis = elvisSupplier.get();
System.out.println("Original Elvis: " + originalElvis);

// ✅ Elvis의 Mock 객체를 사용하도록 변경 가능!
elvisSupplier = () -> new MockElvis(); // 공급 방식 변경

// ✅ Mock Elvis 객체 사용
Elvis mockElvis = elvisSupplier.get();
System.out.println("Mock Elvis: " + mockElvis);

```

✅ **이제 `elvisSupplier`를 변경하기만 하면 `MockElvis`와 `Elvis`를 쉽게 교체할 수 있음!**

✅ **즉, 코드 전체에서 `getInstance()`를 직접 변경할 필요 없이, `Supplier`만 바꾸면 됨.**

> 📌 **왜 유연해지는가?**
>
- 기존 코드를 수정할 필요 없이 **객체 공급 방식을 한 곳에서만 변경할 수 있다.**
- 특정 환경(테스트, 개발, 프로덕션)에 따라 **공급 객체를 쉽게 변경할 수 있어서 유지보수가 쉬워진다.**

---

### **✅ 2️⃣ Lazy Initialization(지연 초기화) 구현이 더 유연해진다**

📌 **Lazy Initialization(지연 초기화)란?**

- 객체를 **필요할 때만 생성**하여 **불필요한 메모리 사용을 줄이는 패턴**
- `Supplier`를 활용하면 **객체를 필요할 때만 생성하고 캐싱할 수도 있음**

**[ 📌 기존 방식: 정적 팩터리 메서드를 이용한 Lazy Singleton ]**

```java
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

📌 **이 방식도 Lazy Singleton을 구현할 수 있지만, 단점이 있다:**

1. **`getInstance()`를 직접 호출해야만 객체를 가져올 수 있다.**
2. **객체 생성 방식을 바꾸려면 `getInstance()` 내부를 수정해야 한다.**
3. **테스트 환경에서 `LazySingleton`을 Mock 객체로 변경하기 어렵다.**

   <br>

**[ 📌  `Supplier`를 사용하면 Lazy Initialization이 더 유연해진다 ]**

```java
import java.util.function.Supplier;

public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    // ✅ 필요할 때만 생성하는 Lazy 방식
    public static Supplier<LazySingleton> getInstanceSupplier() {
        return () -> {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        };
    }
}

public class Main {
    public static void main(String[] args) {
        // ✅ Supplier를 사용하여 LazySingleton 제공
        Supplier<LazySingleton> lazySupplier = LazySingleton.getInstanceSupplier();

        // ✅ 필요할 때만 생성됨
        LazySingleton obj1 = lazySupplier.get();
        LazySingleton obj2 = lazySupplier.get();

        System.out.println(obj1 == obj2); // ✅ true (같은 객체 반환)
    }
}

```

✅ **이제 `Supplier`를 통해 LazySingleton 객체를 필요할 때만 생성 가능!**

✅ **테스트 환경에서는 `Supplier`를 변경하여 다른 객체(Mock)로 쉽게 교체 가능.**

📌 **즉, `Supplier`를 활용하면 Lazy Singleton이 더욱 유연해진다!**

---

### **✅** 3️⃣ **유지보수와 확장성이 좋아진다**

📌 **객체 생성 방식을 한 곳에서 관리할 수 있어 유지보수가 쉬움.**

📌 **객체 생성 로직을 `Supplier`에 위임하면, 새로운 요구사항이 생겨도 간단히 대응 가능.**

---

### **최종 정리: `Supplier<T>`를 사용하면 어떤 점이 좋아지는가?**

| **장점** | **설명** |
| --- | --- |
| **1. 코드의 유연성 증가** | `Supplier`만 변경하면 객체 공급 방식을 쉽게 바꿀 수 있음 |
| **2. Mock 객체를 쉽게 주입 가능** | 테스트 환경에서 `Supplier`를 활용하면 Mock 객체를 쉽게 주입할 수 있음 |
| **3. Lazy Initialization을 
더 유연하게 구현 가능** | 필요할 때만 객체를 생성하고, `Supplier`를 이용해 생성 방식 변경 가능 |
| **4. 유지보수성과 확장성이 증가** | 객체 생성 로직을 `Supplier`로 분리하면 유지보수가 쉬워지고 확장성이 높아짐 |

> ✅ **즉, `Supplier`를 사용하면 객체 생성과 공급 방식을 유연하게 제어할 수 있어서, 유지보수성이 높아지고 테스트가 쉬워진다!**
>

---

## **✅** 7. Supplier의 단점

### 1️⃣ **코드 가독성 & 복잡성 증가**

**문제**

- Supplier를 너무 많이 사용하면
    - 코드가 복잡해지고,
    - **불필요한 람다식과 인터페이스 사용으로 가독성이 떨어질 수 있음**.

**💡 예제 비교**

[ ✅ 일반 코드 (직관적이고 간결함) ]

```java
String username = "RealUser";
System.out.println(username);
```

   <br>

[ ❌ Supplier 사용 코드 (불필요하게 복잡함) ]

```java
Supplier<String> usernameSupplier = () -> "RealUser";
System.out.println(usernameSupplier.get());
```

> 🚨 이렇게 사용할 필요가 없음!
>
>
> 단순한 변수 할당이면 굳이 `Supplier`를 쓸 이유가 없음.
>

→ **Supplier는 필요한 경우에만 쓰는 것이 좋고, 남발하면 코드만 복잡해진다.**

---

### 2️⃣ **오버헤드(Overhead) 문제**

**문제**

- Supplier를 사용할 때마다 **추가적인 객체(Supplier 인스턴스)를 생성해야 함**.
- 불필요한 람다 표현식이나 익명 클래스 사용으로 성능 저하 가능성 있음.
- 단순한 값 하나를 저장할 때는 **직접 값을 쓰는 것이 더 빠름**.

**💡 예제**

```java
Supplier<Integer> numberSupplier = () -> 100; // 불필요한 Supplier 사용
int number = 100; // 그냥 이렇게 하면 됨
```

> 🚨 Supplier는 **객체 생성이 무거울 때만 사용해야 효과적!**
>

---

### 3️⃣ **지연 평가(Lazy Evaluation)가 항상 좋은 것은 아님**

**문제**

- **즉시 값이 필요한 경우** 굳이 지연 평가를 할 필요가 없음.
- 지연 평가를 남발하면, **언제 값이 평가될지 모호해지는 문제**가 발생할 수 있음.
- 디버깅이 어려워질 수 있음.

   <br>

**💡 예제**

```java
Supplier<String> configSupplier = () -> loadConfig(); // 언제 실행될지 명확하지 않음
String config = loadConfig(); // 이렇게 명확하게 호출하는 것이 좋음
```

> 🚨 지연 평가가 반드시 필요하지 않다면, 즉시 실행하는 것이 더 직관적이다.
>

---

### 4️⃣ **값이 여러 번 필요할 경우, 불필요한 연산 발생**

**문제**

- `Supplier.get()`을 호출할 때마다 연산이 반복될 수 있음.
- 결과를 캐싱하지 않으면 **매번 새로운 객체를 생성하거나 연산을 수행**함.

   <br>

**💡 예제 (잘못된 사용)**

```java
Supplier<Double> randomSupplier = Math::random; // 호출할 때마다 다른 값
System.out.println(randomSupplier.get()); // 0.123456
System.out.println(randomSupplier.get()); // 0.987654
```

> 🚨 같은 값이 필요하면 그냥 변수를 쓰는 게 낫다!


   <br>

**💡 해결 방법 (캐싱 사용)**

```java
Supplier<Double> cachedRandomSupplier = new Supplier<>() {
    private Double value;

    @Override
    public Double get() { // 직접 재정의 수행
        if (value == null) {
            value = Math.random(); // 한 번만 실행
        }
        return value;
    }
};
```

> ✅ 한 번 계산한 값을 재사용할 경우, 캐싱을 해야 효과적이다!
>

---

### 5️⃣ **제어가 어려운 경우가 있음**

**문제**

- Supplier를 쓰면 값이 필요할 때 평가되므로, **언제 실행될지 명확하지 않음**.
- 특히, 여러 스레드에서 사용할 경우 동기화 문제가 발생할 수 있음.

**💡 예제 (멀티스레드 문제 발생 가능)**

```java
Supplier<Connection> connectionSupplier = () -> {
    System.out.println("DB 연결 생성");
    return new Connection(); // 가정: 실제 DB 연결 객체
};

new Thread(() -> System.out.println(connectionSupplier.get())).start();
new Thread(() -> System.out.println(connectionSupplier.get())).start();
```

> 🚨 멀티스레드 환경에서는 Supplier가 같은 객체를 제공하는지 보장할 수 없음.
>
>
> ✅ **싱글톤 패턴이나 의존성 주입(DI)을 활용하는 것이 더 적절할 수 있음**.
>

---

# ✅ **결론: Supplier는 "필요할 때"만 써야 한다!**

Supplier가 강력한 기능이지만, **무조건 쓰면 오히려 코드가 복잡해지고 성능이 저하될 수 있음**.

**언제 사용하는 것이 좋을까?**

| 상황 | Supplier 사용 추천 여부 | 이유 |
| --- | --- | --- |
| ✅ 무거운 객체를 생성할 때 | ✔️ | 필요할 때만 생성하여 최적화 |
| ✅ 값이 필요할 때만 계산하고 싶을 때 | ✔️ | 지연 실행을 활용 |
| ✅ 객체 생성 로직을 동적으로 변경할 때 | ✔️ | DI 및 팩토리 패턴 대체 가능 |
| ✅ 테스트 시 Mocking이 필요할 때 | ✔️ | 유연한 객체 생성 가능 |
| ❌ 단순한 값(예: 숫자, 문자열) 할당 | ❌ | 그냥 변수로 관리하는 것이 좋음 |
| ❌ 즉시 실행이 필요한 경우 | ❌ | 지연 평가가 필요하지 않음 |
| ❌ 멀티스레드 환경에서 동기화 필요 | ❌ | 별도의 동기화 기법 필요 |

   <br>

**🔹 핵심 포인트 🔹**

- **객체 생성 비용이 클 때** 사용하면 좋음.
- **불필요한 사용은 코드 복잡성만 증가**시킴.
- **멀티스레드 환경에서는 주의해야 함**.
- **지연 평가가 항상 좋은 건 아님**.

> 즉, "Supplier는 마법 같은 도구가 아니고, 적절한 상황에서만 써야 효과적"이라는 것!
>