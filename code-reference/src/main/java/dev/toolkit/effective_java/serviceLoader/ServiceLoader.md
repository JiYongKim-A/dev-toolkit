<aside>
📌

# **ServiceLoader란?**

## **1. 정의**

- **ServiceLoader**는 **자바 6부터 도입된 표준 API**로,

  - **동적으로 서비스 구현체(클래스)를 검색하고 로드할 수 있도록 지원하는 도구**이다.

  - 기본적으로 **`META-INF/services` 디렉터리에 등록된 서비스 구현체를**
    **→ 탐색하고 인스턴스를 생성하는 기능을 제공**한다.
    
  <br>
  
- 내부적으로 리플렉션(`Class.forName()`, `newInstance()`)을 사용하여

  → 클래스를 로드하고 객체를 생성한다.


> 즉, 특정 인터페이스나 추상 클래스를 구현하는 여러 클래스를 런타임에 탐색하여 사용할 수 있도록 한다.
>

---

## **2. ServiceLoader의 주요 역할**

1. **동적 서비스 검색**
    - `META-INF/services`에 등록된 구현체들을 자동으로 탐색하여 로드할 수 있다.
    
    <br>
   
2. **구체적인 구현체에 의존하지 않고 유연한 구조 제공**
    - 컴파일 타임이 아니라 **런타임에 적절한 구현체를 선택할 수 있다.**

    <br>

3. **확장 가능한 구조 지원**
    - 새로운 구현체가 추가되더라도 기존 코드를 수정하지 않고 사용할 수 있다.

    <br>

4. **자바 모듈 시스템(Java Module System, JPMS)과의 연동 지원**
    - 자바 9부터는 `module-info.java`를 통해 서비스 제공자를 정의하고 사용할 수 있다.

---

## **3. ServiceLoader의 동작 방식**

ServiceLoader는 다음과 같은 순서로 동작한다.

1. **서비스 인터페이스 정의**
    - 로드할 대상이 되는 인터페이스(또는 추상 클래스)를 정의한다.

    <br>

2. **구현체 작성**
    - 해당 인터페이스를 구현하는 클래스들을 작성한다.
   
    <br>
   
3. **설정 파일 작성**
    - `META-INF/services` 디렉터리 안에 서비스 인터페이스의
    - FQCN(Full Qualified Class Name, 패키지 포함 클래스명)을 파일명으로 한 파일을 만들고, 

      → 파일 내용에는 해당 서비스를 구현한 클래스들의 FQCN을 한 줄씩 작성한다.

    <br>

4. **ServiceLoader를 사용하여 구현체 로드**
    - `ServiceLoader.load(인터페이스.class)`를 호출하면,
    - 설정된 파일을 읽고 해당 구현체들을 동적으로 로드하여 사용할 수 있다.

---

## **4. ServiceLoader의 장점**

1. ✅  **동적 로딩 지원**
    - 컴파일 시점이 아니라 **런타임에 구현체를 선택할 수 있어 유연성이 높다.**
    - 즉, 클라이언트 코드 변경 없이도 새로운 구현체를 추가할 수 있다.

<br>

2. ✅ **리플렉션을 직접 사용할 필요 없음**
    - 기존에는 `Class.forName()`을 통해 수동으로 (리플랙션을 통해 직접적으로)클래스를 로드해야 했지만,

    <br>

    - **ServiceLoader는 이러한 작업을 자동화해주므로 개발자가 직접 리플렉션을 다룰 필요가 없다.**

<br>

3. ✅ **유지보수성 향상**
    - 새로운 구현체가 추가되더라도 기존 코드를 수정할 필요 없이

      → 자동으로 감지하여 사용할 수 있다.

<br>


4. ✅ **자바 모듈 시스템(JPMS)과의 연동**
    - 자바 9부터 `module-info.java`를 활용하여 모듈 간의 의존성을 설정하고,

      → 서비스 등록 및 검색이 가능하다.


---

## **5. ServiceLoader의 단점**

1. ✅ **인터페이스의 기본 생성자가 필요**
    - ServiceLoader를 사용할 경우,
    
        **→ 구현체에 기본 생성자(default constructor)가 필요하다.**

    <br>

    ```java
    ServiceLoader<MyClass> loader = ServiceLoader.load(MyClass.class);
    for (MyClass instance : loader) {
        // 여기서 기본 생성자가 없으면 예외 발생
    }
    ```
    
    - 만약 기본 생성자가 없거나 매개변수가 필요한 생성자가 있을 경우, ServiceLoader로 로드할 수 없다. 
    
      → **내부적**으로
        
        <br>
      
        - **`Class.newInstance()`** 또는
        - **(Java 9 이후부터) `getDeclaredConstructor().newInstance()`**  를 사용하여
        
        → 객체를 생성 하기 때문
        
        → 기본 생성자가 없으면 `NoSuchMethodException` 예외가 발생

<br>

2. ✅  **특정 구현체를 선택하는 로직이 없음**
   - `ServiceLoader.load(MyService.class)`를 호출하면 **등록된 모든 구현체를 로드**하지만,
   - 기본적으로 **특정 구현체를 선택할 수 있는 API를 제공하지 않는다.**
   - 따라서 특정 구현체를 선택하려면 별도의 필터링 로직을 작성해야 한다.

<br>

3. ✅ **실행 시 퍼포먼스 이슈**
   - `META-INF/services` 파일을 읽고 리플렉션을 사용하여 구현체를 로드하는 과정에서 **일정한 성능 비용이 발생**할 수 있다.

<br>

---


## 6. ServiceLoader 도입 전과 후의 동적 로딩 방법

### < ServiceLoader 도입 전 >

[ 개발자가 직접 해야 할 일이 많았음 ]

1. **서비스 인터페이스 정의**
    - `MyService` 같은 인터페이스를 만들고
      
    <br>
    
2. **구현체 등록 (META-INF/services 파일 생성)**
    - `META-INF/services/com.example.MyService` 파일을 만들고 구현체를 수동으로 등록

    <br>   

3. **서비스 로딩 방식 구현**
    - `Class.forName()` + 리플렉션으로 수동 로드
    - `Map<String, Class<?>>` 같은 구조로 관리
    - 동적으로 인스턴스를 생성하는 코드 작성

> 이걸 다 하려면 일종의 **미니 프레임워크를 직접 만들어야 했음.**

<br>

### < ServiceLoader 도입 후 >

[  ServiceLoader 등장 이후 (자바 6+) ]

- `ServiceLoader`가 이 모든 과정을 자동으로 처리
- `ServiceLoader.load(MyService.class)` 한 줄이면 끝
>**따로 프레임워크를 만들 필요가 없음**

<br>

---

## 2-7. ServiceLoader 정리

- ✅ `ServiceLoader`는 **서비스 구현체를 동적으로 검색하고 로드하는 표준 API**.
- ✅ **리플렉션을 직접 사용하지 않아도 구현체를 자동으로 로드할 수 있도록 지원**.
- ✅ **구현체 확장 시 유지보수성을 높이고, 코드 수정 없이 유연한 구조 제공**.
- ✅ 단, **기본 생성자가 필요하고, 특정 구현체 선택이 어렵다는 단점** 존재.
</aside>