### ✅ **서비스 제공자 프레임워크(Service Provider Framework)**

- 서비스 제공자 프레임워크는
    - 클라이언트 코드가 **특정 구현체(서비스 제공자)에 종속되지 않고**,
    - **추상화된 서비스 인터페이스**를 통해 원하는 기능을 사용할 수 있도록 설계된 아키텍처 패턴 이다.

> 즉, ①**클라이언트는 구체적인 구현 방법을 알 필요 없이** ②**인터페이스에 정의된 기능을 사용**하고,
>
>
> **③**실제 구현체는 나중에 추가하거나 변경할 수 있는 유연한 구조**를 제공한다.**
>

---

## 📌 **서비스 제공자 프레임워크의 핵심 구성 요소**

> 서비스 제공자 프레임워크는 일반적으로 다음 **4가지 핵심 컴포넌트**로 구성된다.
>

### **1. 서비스 인터페이스 (Service Interface) (🔥 필수 요소)**

- **정의**
    - 서비스의 동작을 정의하는 인터페이스.
    - 실제 클라이언트 이용하는 인터페이스.
- **특징**
    - 클라이언트는 **이 인터페이스만 의존**하며, **구체적인 구현체가 무엇인지 몰라도 됨**.
    - 인터페이스에 정의된 메서드를 호출하여 원하는 기능을 사용할 수 있음.

- **예시**
    ```java
  public interface MyService {
        void execute();
    }
  ```

<br>

### **2. 제공자 등록 API (🔥 필수 요소)**

- **정의:**
    - 서비스 구현체(제공자)가 프레임워크에서 인식될 수 있도록 지원하는 API
    - **등록하는 과정과는 별개로, 이미 등록된 제공자를 검색하고 활용할 수 있도록 돕는 API 이다.**

    <br>
- **등록 방법:**
    - 제공자를 등록하는 방법은 따로 존재하며, 일반적으로 `META-INF/services` 디렉터리를 활용한다.
    - 예를 들어, `META-INF/services/com.example.MyServiceProvider` 파일을 생성하고, 해당 파일에 구현체(예: `com.example.MyServiceImplAProvider`)를 등록한다.
      
    <br>
- **특징:**
    - **제공자 등록 API는 직접 제공자를 등록하지 않고, 등록된 제공자를 검색하고 활용하는 역할을 수행한다.**
    - 다양한 구현체가 프레임워크에서 사용할 수 있도록 관리할 수 있다.
    - 클라이언트가 필요할 때 적절한 구현체를 선택할 수 있도록 지원한다.
      
    <br>
- **제공자 등록 API를 수행하는 주체:**
    - **이 역할을 수행하는 대표적인 API가 `ServiceLoader`**
    - `ServiceLoader`는 `META-INF/services`에 등록된 제공자를 검색하고, 동적으로 로드하여 활용할 수 있도록 한다.

<br>

📌 **예제: ServiceLoader를 활용한 제공자 검색 및 로드**

```java
ServiceLoader<MyServiceProvider> loader = ServiceLoader.load(MyServiceProvider.class);
for (MyServiceProvider provider : loader) {
    MyService service = provider.create();  // 등록된 제공자로부터 객체 생성
    service.execute();
}
```

✔️ **여기서 `ServiceLoader.load(MyServiceProvider.class)`가 제공자 등록 API 역할을 수행하며, 등록된 제공자를 검색하고 활용할 수 있도록 지원한다.** 🚀


<br>

### **3. 서비스 접근 API (Service Access API) (🔥 필수 요소)**

- **정의**
    - 클라이언트가 인터페이스의 기능을 사용할 때,
        - 실제 서비스의 동작을 수행하는 구현체를 얻기 위해 호출하는 API"
          
        <br>
- **특징**
    - 일반적으로 **정적 팩터리 메서드(Static Factory Method)** 형태로 구현됨.
    - **입력 매개변수에 따라 다양한 구현체(하위 타입)를 반환할 수 있음**.
    - 클라이언트는 이 API를 호출하여 조건에 맞는 인스턴스를 얻어 사용할 수 있음.
      
    <br>
- 예시

    ```java
    public class ServiceFactory {
        public static MyService getService(String type) {
            if (type.equals("A")) {
                return new MyServiceImplA();
            } else if (type.equals("B")) {
                return new MyServiceImplB();
            }
            throw new IllegalArgumentException("Unknown service type");
        }
    }
    ```

    - 정적 팩터리 메서드를 사용하여
        - 클라이언트는 필요한 형태의 Service 인스턴스를 반환받아 사용한다.

      
<br>


### **4. 서비스 제공자 인터페이스 (Service Provider Interface, SPI) (❌ 선택 요소)**

- **정의**
    - **서비스 제공자가 자신을 생성하는 방법을 명시적으로 정의하는 인터페이스.**
    - **각 구현체가 객체 생성 방식을 직접 제어할 수 있도록 지원.**
    - **`ServiceLoader`와 함께 사용하여 동적으로 구현체를 로드할 수 있음.**
      
    <br>
- **SPI의 특징**
    - `ServiceLoader`를 사용할 때, **기본 생성자가 강제되는 문제를 해결하기 위해 도입됨.**
    - **기본 생성자 없이도 객체 생성 가능!**
    - **객체 생성 방식(싱글턴, 정적 팩터리 메서드, 매개변수 주입 등) 커스터마이징 가능.**
    - **하지만, 기본 생성자로 충분하면 SPI는 필요 없음.**
      
    <br>
- **SPI가 필요 없는 경우 (기본 생성자로 충분할 때)**

  📌 **SPI 없이 `ServiceLoader`만 사용하는 경우, 기본 생성자가 필수!**

    ```java
    // 인터페이스 정의
    public interface MyService {
        void execute();
    }
    
    // 구현체 (기본 생성자가 반드시 필요)
    public class MyServiceImplA implements MyService {
        public MyServiceImplA() {} // 기본 생성자 필수
        @Override
        public void execute() {
            System.out.println("Executing Service A");
        }
    }
    
    // ServiceLoader를 통해 기본 생성자로 객체 생성
    ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class);
    for (MyService service : loader) {
        service.execute();  // "Executing Service A"
    }
    
    ```

  > **기본 생성자가 있으면 `ServiceLoader`만으로도 객체를 생성할 수 있음.
  즉, 객체 생성 방식을 직접 제어할 필요가 없다면 SPI를 사용하지 않아도 됨.**

<br>

- **SPI를 사용하는 경우 (객체 생성 방식 제어가 필요할 때)**

  📌 **SPI를 사용하면 기본 생성자가 없어도 동작하며, 객체 생성 방식을 유연하게 조정 가능.**

    ```java
    // 서비스 제공자 인터페이스 (SPI)
    public interface MyServiceProvider {
        MyService create(); // 객체 생성 방식 정의
    }
    
    // 특정 구현체 A의 제공자 (정적 팩터리 메서드 활용)
    public class MyServiceImplAProvider implements MyServiceProvider {
        @Override
        public MyService create() {
            return new MyServiceImplA();  // 직접 객체 생성
        }
    }
    
    // ServiceLoader를 사용하여 SPI 기반으로 로드
    ServiceLoader<MyServiceProvider> loader = ServiceLoader.load(MyServiceProvider.class);
    for (MyServiceProvider provider : loader) {
        MyService service = provider.create(); // SPI를 통해 생성된 인스턴스 사용
        service.execute();
    }
    ```

> **기본 생성자가 없어도 객체를 생성할 수 있음.
  객체 생성 방식을 유연하게 변경할 수 있음.
  정적 팩터리 메서드, 싱글턴 패턴, 매개변수 기반 생성 방식 적용 가능!**

<br>

[ **정리: SPI는 언제 필요하고 언제 필요하지 않은가?** ]

| **구분** | **SPI 없이 ServiceLoader 사용** | **SPI 사용 (ServiceLoader + Provider Interface)** |
    | --- | --- | --- |
    | **기본 생성자 필수 여부** | ✅ 필요함 (반드시 있어야 함) | ❌ 필요 없음 (객체 생성 방식 직접 정의 가능) |
    | **객체 생성 방식 제어 가능 여부** | ❌ 불가능 (ServiceLoader가 기본 생성자만 사용) | ✅ 가능 (정적 팩터리 메서드, 매개변수 주입 등 커스터마이징 가능) |
    | **싱글턴, 캐싱 적용 가능 여부** | ❌ 불가능 | ✅ 가능 |
    | **사용해야 하는 경우** | 객체 생성 방식이 단순할 때 (기본 생성자로 충분) | 객체 생성 방식 커스터마이징이 필요할 때 |
    
> **즉, SPI는 필수 요소가 아니며, "객체 생성 방식을 직접 제어해야 할 때만" 사용하는 선택 요소다!**
    