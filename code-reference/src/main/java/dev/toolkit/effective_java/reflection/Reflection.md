# 리플렉션(Reflection)이란?

## 1. 리플렉션(Reflection) **정의**

- 리플렉션(Reflection)은
    - **런타임에 클래스, 메서드, 필드 등의 정보를 조사하거나 객체를 동적으로 생성할 수 있는 기법**이다.

> 이를 통해 **컴파일 타임이 아닌 실행 중에 클래스 정보를 조회하고, 동적으로 인스턴스를 생성하거나 메서드를 호출할 수 있다.**
>

---

## 2. 리플렉션(Reflection) **예시**

```java
// 클래스 이름을 문자열로 받아 런타임에 동적으로 로드하고 객체 생성
Class<?> clazz = Class.forName("com.example.MyClass");
Object instance = clazz.getDeclaredConstructor().newInstance();
```
- 위 코드는 **클래스 이름을 문자열로 받아 해당 클래스를 로드하고,**

  **→ 기본 생성자를 호출하여 인스턴스를 생성하는 예제**이다.

<br>

또한, 리플렉션을 사용하면 **private 필드 및 메서드에도 접근 가능**하다.

```java
Field field = clazz.getDeclaredField("privateField");
field.setAccessible(true); // 접근 가능하게 설정
field.set(instance, "New Value"); // 필드 값 변경
```

---

## 3. 리플렉션(Reflection)의 장점

### **A. 런타임에 클래스의 정보를 동적으로 분석 가능**

- 프로그램 실행 중에 **클래스의 메서드, 필드, 생성자 등의 정보를 조회하고 활용**할 수 있다.
- 정적인 코드만으로는 예측할 수 없는 상황에서도

  → 객체의 정보를 확인할 수 있어 **유연한 코드 작성이 가능**하다.

<br>

[ 📌  **클래스의 모든 메서드 조회** ]

```java
import java.lang.reflect.Method;

public class ReflectionExample {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.util.ArrayList");
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            System.out.println("메서드 이름: " + method.getName());
        }
    }
}
```

✅ 실행 중에 `ArrayList` 클래스의 모든 메서드를 조회할 수 있다.

---

### **B. 컴파일 타임에 알 수 없는 클래스를 동적으로 로드 가능**

- 문자열로 클래스 이름을 입력받아 런타임에 해당 클래스를 동적으로 로드할 수 있다.
- **플러그인 시스템이나 확장 가능한 애플리케이션에서 필수적인 기능**이다.

<br>

[ 📌 **클래스 이름을 문자열로 받아 동적으로 로드** ]

```java
Class<?> clazz = Class.forName("com.example.MyCustomClass");
Object instance = clazz.getDeclaredConstructor().newInstance();
```

✅ 클래스 이름만 알고 있으면 **직접 코드 수정 없이도 새로운 클래스를 실행 시점에서 로드 가능**하다.

---

### **C. 프레임워크 및 라이브러리에서 핵심적인 역할**

- 리플렉션은 **스프링(Spring), 하이버네이트(Hibernate), JPA, Jackson, Mockito** 등

  → 다양한 프레임워크와 라이브러리에서 사용된다.


- **스프링의 DI(의존성 주입) 기능, AOP(관점 지향 프로그래밍), JPA 엔티티 매핑** 등도

  → 리플렉션을 기반으로 동작한다.

<br>

[ 📌  **Jackson을 이용한 JSON → 객체 변환** ]

```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonExample {
    public static void main(String[] args) throws Exception {
        String json = "{ \"name\": \"John\", \"age\": 30 }";

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);

        System.out.println(user.getName());  // John
    }
}
```

✅ **Jackson은 리플렉션을 사용하여 필드 정보를 확인하고, JSON 데이터를 자동으로 객체에 매핑한다.**

---

### **D. 접근 제한을 우회하여 private 멤버 접근 가능**

- 리플렉션을 이용하면 **private 필드나 메서드에도 접근 가능**하다.
- 단, <U>보안상의 이유로 신중하게 사용해야 한다.</U>

<br>

[ 📌 **private 필드 값 변경** ]

```java
import java.lang.reflect.Field;

class Person {
    private String name = "Original";
}

public class ReflectionPrivateAccess {
    public static void main(String[] args) throws Exception {
        Person person = new Person();

        Field field = Person.class.getDeclaredField("name");
        field.setAccessible(true);
        field.set(person, "Changed");

        System.out.println(field.get(person));  // Changed
    }
}
```

✅ **`setAccessible(true)`를 이용해 private 필드의 값을 변경할 수 있다.**

---

### **E. 테스트 자동화 및 목(mock) 객체 생성에 유용**

- JUnit, Mockito 등의 **테스트 프레임워크에서 리플렉션을 활용하여**

  **→ 목(Mock) 객체를 동적으로 생성**할 수 있다.

- 인터페이스 기반의 가짜 객체를 만들어 **테스트 환경을 구성할 때 유용**하다.

<br>

[ 📌 **Mockito를 활용한 목 객체 생성** ]

```java
import static org.mockito.Mockito.*;

public class MockTest {
    public static void main(String[] args) {
        MyService mockService = mock(MyService.class);
        when(mockService.getData()).thenReturn("Mock Data");

        System.out.println(mockService.getData());  // Mock Data
    }
}
```

✅ **Mockito는 리플렉션을 사용하여 인터페이스 기반의 가짜 객체를 생성할 수 있다.**

---

### **F. 동적 프록시(Dynamic Proxy) 생성 가능**

- 런타임에 새로운 프록시 객체를 생성하여 **AOP(Aspect-Oriented Programming) 기능을 구현할 수 있다.**
- **스프링의 트랜잭션 관리, 로깅, 보안 기능 등도 동적 프록시를 활용**한다.

<br>

[ 📌 **InvocationHandler를 이용한 동적 프록시** ]

```java
import java.lang.reflect.*;

interface MyInterface {
    void doSomething();
}

class MyClass implements MyInterface {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

class ProxyHandler implements InvocationHandler {
    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method call");
        Object result = method.invoke(target, args);
        System.out.println("After method call");
        return result;
    }
}

public class DynamicProxyExample {
    public static void main(String[] args) {
        MyInterface original = new MyClass();
        MyInterface proxyInstance = (MyInterface) Proxy.newProxyInstance(
            original.getClass().getClassLoader(),
            original.getClass().getInterfaces(),
            new ProxyHandler(original)
        );

        proxyInstance.doSomething();
    }
}
```

📌 **출력 결과**

```
Before method call
Doing something...
After method call
```

✅ **리플렉션을 이용해 실행 전후에 추가 로직을 삽입하는 동적 프록시를 만들 수 있다.**

---

### **G. Enum, 애너테이션(Annotation) 등의 메타데이터를 분석 가능**

- 리플렉션을 이용하면 **애너테이션 정보를 읽거나, Enum 값을 동적으로 탐색할 수 있다.**
- **스프링, JPA, Lombok 등의 프레임워크도 리플렉션을 활용하여**

  **→ 애너테이션을 기반으로 기능을 확장한다.**

<br>

[ 📌 **애너테이션 정보 읽기** ]

```java
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

class AnnotatedClass {
    @MyAnnotation("Hello Annotation")
    public void annotatedMethod() {}
}

public class AnnotationReflection {
    public static void main(String[] args) throws Exception {
        Method method = AnnotatedClass.class.getMethod("annotatedMethod");
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        System.out.println(annotation.value());  // Hello Annotation
    }
}
```

✅ **리플렉션을 활용해 런타임에 애너테이션을 읽고 처리할 수 있다.**

---

## **4. 리플렉션의 단점**

### **A. 성능 비용**

- 리플렉션은 **일반적인 메서드 호출보다 성능이 낮다.**
    - 그 이유는 **JVM이 리플렉션을 통해 메서드를 호출할 때 여러 단계를 거쳐야 하기 때문**이다.

        <br>
  
        - **일반적인 메서드 호출**
            - 컴파일 타임에 메서드 호출이 확정되므로 최적화(OOP 기반의 동적 바인딩 포함)가 가능.

        <br>
            
        - **리플렉션 기반 메서드 호출**
            1. 클래스 정보를 조회하고
            2. 해당 메서드를 검색한 뒤
            3. 접근 권한을 확인하고
            4. `invoke()`를 통해 실행해야 하므로 성능이 저하됨.


> **특히, 반복적으로 호출되는 코드에서 리플렉션을 사용하면 성능 저하가 심각해질 수 있다.**
따라서, 일반적인 애플리케이션에서는 가능한 **컴파일 타임에 메서드를 정적으로 호출하는 것이 좋다.**
>

---

### **B. 타입 안전성 부족**

- **컴파일 타임에 타입 검사가 이루어지지 않음.**
    - 예를 들어
        - 클래스나 메서드가 존재하지 않거나 잘못된 타입으로 캐스팅할 경우,
        - **런타임 오류(ClassCastException, NoSuchMethodException 등)가 발생할 위험이 있다.**

> 따라서 **잘못된 코드가 런타임에 문제를 일으킬 가능성이 높아진다.**
>

```java
// 존재하지 않는 클래스를 로드하면 ClassNotFoundException 발생
Class<?> clazz = Class.forName("com.example.UnknownClass");
```

```java
// 잘못된 타입으로 캐스팅하면 ClassCastException 발생
MyService service = (MyService) clazz.getDeclaredConstructor().newInstance();
```

---

### **C. 유지보수 어려움**

- 리플렉션을 활용하면 코드가 **명확하지 않고 가독성이 떨어짐.**
- 특정 클래스 이름을 문자열로 다루다 보니, **리팩토링 시 오타나 변경 사항을 IDE가 감지하기 어려움.**
- 또한, **컴파일 타임에 오류를 발견할 수 없어, 문제가 런타임에서야 발생하는 경우가 많음.**
  (예: 필드명 변경 시, `getDeclaredField("oldFieldName")`가 실패)

---

## 5. 리플렉션 정리

- ✅ **리플렉션은 <U>**강력한 기능**</U>이지만, <U>**성능 저하, 타입 안전성 문제, 유지보수 어려움**</U> 등의 단점이 있다.**
- ✅ **따라서, <U>**일반적인 코드에서는 사용을 지양**</U>하고, <U>**정말 필요한 경우에만 제한적으로 활용해야 한다.**</U>**
- ✅ **스프링 프레임워크나 JPA 같은 프레임워크는 내부적으로 리플렉션을 활용하지만, 일반적인 개발자는 이를 직접 다룰 필요가 거의 없다.**
- ✅ **리플렉션을 피하기 위해 정적 팩터리 메서드, 인터페이스 기반의 DI(의존성 주입) 등을 활용하는 것이 좋다.**