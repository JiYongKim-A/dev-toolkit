# 빌더 패턴과 계층적 설계

## 1. 빌더 패턴(Builder Pattern)이란?

### (1) 개념
- 빌더 패턴은 <U>**복잡한 객체를 단계적으로 생성**</U>할 수 있도록 하는 디자인 패턴이다.
- 객체 생성 과정에서 **필수 값과 선택적 값을 분리**하여 **유연한 객체 생성이 가능**하도록 한다.
- **특징**
    - **객체의 불변성 유지**
          
       → 객체를 한 번 생성하면 변경할 수 없음 (`final` 필드를 활용)
  
      <br>
  
    - **가독성이 좋은 체이닝 방식 지원** 
  
       → `builder.methodA().methodB().build()` 형태의 호출 가능
      
      <br>

    - **계층적 빌더 패턴과 결합 가능** 
     
       → 상위 클래스를 확장하면서도 빌더 패턴을 유지 가능

---

## 2. 제네릭(Generics)이란?

### (1) 개념
- **제네릭**은 자바에서 **클래스나 메서드가 다룰 데이터 타입을 외부에서 결정(매개변수화)할 수 있게 해 주는 문법**
- 예를 들어,
    - `List<String>`은 "문자열을 담는 리스트", `List<Integer>`는 "정수를 담는 리스트"처럼, **동일한 코드**(List) 안에서 **타입**만 달리하여 재사용이 가능

    <br>

### (2) 왜 쓰는가?
- **타입 안정성**
    - 컴파일 시점에 타입 오류를 잡을 수 있게 해준다.

    <br>
- **재사용성**
    - 한 번 작성한 클래스로 여러 타입을 지원할 수 있습니다.

<br>

### (3) 사용 예시
```java
public class Box<T> {
    private T item;
    public Box(T item) { this.item = item; }
    public T getItem() { return item; }
}

Box<String> strBox = new Box<>("Hello");
String str = strBox.getItem(); // "Hello"
```

---

## 3. 재귀적 타입 한정(Recursive Type Bound)

### (1) 개념
- 제네릭에서 "T가 자기 자신을 상속하는(또는 구현하는) 형태"로 → 타입 매개변수를 선언하는 기법

```java
public class Coffee<T extends Coffee<T>> {
    // ...
}
```
- 문법 예: `<T extends Coffee<T>>`
- 여기서 `Coffee<T>`는 어떤 클래스(또는 인터페이스)인데,
  즉, **T는 Coffee<T>를 상속하는 클래스**라는 뜻.

---

## 4. Coffee 코드 빌더 패턴과 계층적 설계 적용 분석

### **4.1. 주요 클래스 및 역할**

1. **`Coffee` (추상 클래스)**
    - 모든 커피의 **공통 속성**(원두, 사이즈, 샷, 토핑, 가격) 정의.
    - `Builder<T>` 패턴을 통해 객체 생성을 유연하게 설계.
    - `calculatePrice()`를 사용하여 가격을 계산.
   
    <br>
   
2. **`MilkBasedCoffee` (추상 클래스)**
    - `Coffee`를 상속받아 **우유가 포함된 커피**(예: Latte)를 정의.
    - `MilkType`(우유 종류) 속성 추가.
    - `Builder<T>`를 통해 우유 타입 선택 기능 추가.

    <br>

3. **`NonMilkCoffee` (추상 클래스)**
    - `Coffee`를 상속받아 **우유가 포함되지 않은 커피**(예: Espresso)를 정의.
    - `Strength`(커피 강도) 속성 추가.

    <br>

4. **`Latte` (구체 클래스)**
    - `MilkBasedCoffee`를 상속받아 라떼를 정의.
    - `LatteStyle`(클래식, 바닐라, 카라멜, 헤이즐넛)과 `MilkToCoffeeRatio`(우유 비율) 추가.
    - `withLatteArt()`를 통해 라떼 아트 옵션 추가.

    <br>

5. **`Espresso` (구체 클래스)**
    - `NonMilkCoffee`를 상속받아 에스프레소를 정의.
    - `Caffeine`(카페인 강도) 옵션 추가.

---

### **4.2. 계층 구조**

✔ **객체 계층 구조**

```
Coffee (추상 클래스)  <-- 모든 커피의 공통 속성 정의
│
├── MilkBasedCoffee (추상 클래스) <-- 우유가 포함된 커피 정의
│   ├── Latte (구현 클래스) <-- 라떼 (MilkBasedCoffee 확장)
│
└── NonMilkCoffee (추상 클래스) <-- 우유가 포함되지 않은 커피 정의
    ├── Espresso (구현 클래스) <-- 에스프레소 (NonMilkCoffee 확장)
```

✔ **빌더 패턴 계층 구조**

```
Coffee.Builder<T> (추상 빌더)
│
├── MilkBasedCoffee.Builder<T> (추상 빌더)
│   ├── Latte.Builder (구현 빌더)
│
└── NonMilkCoffee.Builder<T> (추상 빌더)
    ├── Espresso.Builder (구현 빌더)
```

---

### **4.3. 하위 구현체에 대한 강제 사항**

**4.3.1. 추상 클래스에서 강제하는 메서드**

- `Coffee`는 추상 클래스이므로 **직접 인스턴스화 불가능**. 

    → `MilkBasedCoffee` 또는 `NonMilkCoffee`를 상속해야만 사용 가능.

    <br>

- `MilkBasedCoffee`와 `NonMilkCoffee`는 하위 클래스에서 반드시 **구체적인 커피(예: `Latte`, `Espresso`)를 구현해야 함**.


<br>


**4.3.2. 빌더 패턴에서 강제하는 요소**

- `Coffee.Builder<T>`
    - `public abstract Coffee build();`

        → **각 하위 클래스에서 반드시 `build()`를 구현해야 함.**

    <br>

    - `protected abstract T self();`
      
      → **재귀적 제네릭 패턴을 활용하여 자기 자신을 반환하는 메서드 구현 강제화.**

    <br>

- `MilkBasedCoffee.Builder<T>`와 `NonMilkCoffee.Builder<T>` 역시`build()`와 `self()`를 **반드시 하위 빌더에서 구현**해야 함.

---

### **4.4. 계층적 빌더 패턴 적용 (Coffee → NonMilkCoffee → Espresso)**

✔ **필수 필드**(원두, 사이즈, 샷)는 **생성자로 설정**하여 하위 클래스가 반드시 정의해야 함.

✔ **옵션 필드**(토핑, 강도, 카페인 레벨 등)는 **메서드 체이닝 방식으로 추가 가능**.

✔ **`self()` 메서드를 활용**하여 각 계층에서 빌더의 타입을 반환, **일관된 빌더 패턴 유지**.

---

### 4.5. **계층별 빌더 패턴 설계**

**1. `Coffee.Builder<T>` (최상위 빌더)**
   - 모든 커피의 공통 속성(원두, 사이즈, 샷, 토핑) 포함.
   - `addTopping()`을 통해 **토핑 추가 가능**.
   - `build()` 메서드는 하위 빌더에서 반드시 구현해야 함.

   <br>

**2. `NonMilkCoffee.Builder<T>` (우유 없는 커피용 빌더)**

- `Coffee.Builder<T>`를 상속받아 **커피 강도(Strength) 추가**.
- `selectStrength()`를 통해 **커피 강도를 설정 가능**.
- `build()` 메서드는 여전히 하위 빌더에서 구현해야 함.

   <br>

**3. `Espresso.Builder` (Espresso 전용 빌더)**

- `NonMilkCoffee.Builder<Builder>`를 상속받아 **카페인 강도(Caffeine) 추가**.
- `selectCaffeine()`을 통해 **카페인 강도를 설정 가능**.
- `build()` 메서드를 통해 `Espresso` 객체를 최종 생성.

---

### 4.6. **빌더 패턴 적용 방식**

```java
public static class Builder extends NonMilkCoffee.Builder<Builder> {
    private Caffeine caffeine = Caffeine.MEDIUM; // 기본값: 중간 강도

    // 필수 필드를 포함한 생성자 (원두, 사이즈, 샷 정보)
    public Builder(BeanType beanType, Size size, Shots shots) {
        super(beanType, size, shots);
    }

    // 📌 카페인 강도를 설정하는 메서드 (체이닝 가능)
    public Builder selectCaffeine(Caffeine caffeine) {
        this.caffeine = caffeine;
        return this;
    }

    // 📌 자기 자신을 반환하는 메서드 (빌더 패턴 유지)
    @Override
    protected Builder self() {
        return this;
    }

    // 📌 Espresso 객체를 생성하는 메서드
    @Override
    public Espresso build() {
        return new Espresso(this);
    }
}
```

---

### **4.7. Espresso 객체 생성 예시 (`CoffeeMain`)**

```java
Coffee espresso = new Espresso.Builder(Coffee.BeanType.ROBUSTA, Coffee.Size.GRANDE, Coffee.Shots.SINGLE)
        .addTopping(Coffee.Topping.CHOCOLATE_POWDER)  // ✅ 토핑 추가 가능
        .selectStrength(NonMilkCoffee.Strength.STRONG) // ✅ 커피 강도 설정 가능
        .selectCaffeine(Espresso.Caffeine.HIGH) // ✅ 카페인 강도 설정 가능
        .build();
System.out.println("[ Espresso 명세서 출력 ] " + espresso);
```

✔ `addTopping()`, `selectStrength()`, `selectCaffeine()`이 **체이닝 방식으로 적용 가능**.

✔ `build()` 호출 후 **완전한 `Espresso` 객체 생성 및 출력 가능**.

---

### 4.8. **계층적 빌더 패턴**

✅ **1. 계층별 속성 분리**

- `Coffee` → `NonMilkCoffee` → `Espresso` 순으로 **공통 속성과 개별 속성을 구분**.
- 불필요한 코드 중복을 제거하고 **유지보수성을 향상**.

   <br>

✅ **2. 체이닝 방식으로 빌더 메서드 활용**

- `addTopping()`, `selectStrength()`, `selectCaffeine()`을 **체이닝 방식으로 활용 가능**.
- **가독성이 높아지고, 직관적인 객체 생성 가능**.

   <br>

✅ **3. 불변성 유지**

- `final` 키워드와 `Collections.unmodifiableSet()`을 활용하여 **객체 생성 후 속성 변경 방지**.
- **스레드 안정성 확보**.

   <br>

✅ **4. 코드 재사용성 향상**

- `Coffee.Builder<T>` → `NonMilkCoffee.Builder<T>` → `Espresso.Builder`로 확장하여 **공통 코드 중복 방지**.
- **새로운 커피 타입 추가 시 최소한의 코드 수정만 필요**.

   <br>

✅ **5. 유지보수 용이성**

- **새로운 커피 종류 추가 시 기존 코드 변경 없이 확장 가능**.
- 각 계층에서 필요한 속성만 관리하여 **코드 변경 최소화**.

---

### 4.9 계층적 빌더 패턴 적용으로 얻을 수 있는 이점

**✅ 1. 코드의 모듈성 (Modularity) 향상**

- `Coffee` → `NonMilkCoffee` → `Espresso` 순으로 **공통 속성과 개별 속성을 분리하여 모듈화**.
- **각 클래스의 역할이 명확해지고, 독립적인 유지보수가 가능**.
- 새로운 기능을 추가할 때 특정 계층만 수정하면 되므로 **코드 수정이 최소화됨**.

---

**✅ 2. 재사용성 (Reusability) 증가**

- `Coffee.Builder<T>` → `NonMilkCoffee.Builder<T>` → `Espresso.Builder` 형태로 **공통 기능을 공유**하면서도 **각 계층에서 개별 속성을 추가**.
- 새로운 커피 유형을 추가할 때 **기존 빌더를 활용하여 최소한의 코드 수정으로 확장 가능**.
- **중복 코드 최소화**: 공통 기능을 부모 클래스에서 제공하여 하위 클래스에서 재사용 가능.

---

**✅ 3. 유지보수성 (Maintainability) 향상**

- 각 계층별 책임이 명확하여 **클래스 구조를 쉽게 이해하고 관리 가능**.
- 기존 클래스의 수정 없이 새로운 기능을 추가할 수 있어 **변경 영향도가 적음**.
- 특정 기능(예: 가격 계산 로직)의 변경이 필요할 때 **하나의 계층에서만 수정하면 전체 구조에 영향을 주지 않음**.

---

**✅ 4. 확장성 (Extensibility) 개선**

- 새로운 커피 유형을 추가할 때 기존 계층 구조를 유지하면서도 **새로운 속성을 쉽게 추가 가능**.
- `NonMilkCoffee`를 상속받아 새로운 커피(`Americano`, `Ristretto` 등)를 만들 때 **기존 코드를 재사용하여 최소한의 수정만 필요**.
- 커피에 추가적인 속성(예: `NonMilkCoffee`에 `RoastLevel` 추가)이 필요할 경우 **기존 빌더 계층을 확장하여 구현 가능**.

---

**✅ 5. 객체 생성의 유연성 (Flexibility)**

- **필수 속성과 선택적 속성을 분리**하여 **객체 생성이 더 유연해짐**.
- `Espresso.Builder`에서 `selectStrength()`와 `selectCaffeine()`을 선택적으로 호출 가능 → **다양한 조합의 객체 생성 가능**.
- **체이닝 방식**을 통해 **가독성이 뛰어나고 직관적인 객체 생성 가능**.

---

**✅ 6. 불변성 (Immutability) 보장**

- `final` 키워드 사용 및 `Collections.unmodifiableSet()` 활용하여 **객체 생성 후 변경 불가능하도록 설계**.
- 멀티스레드 환경에서도 안전하게 사용 가능.

---

**✅ 7. 가독성 (Readability) 및 직관적인 객체 생성**

- **메서드 체이닝**을 통해 가독성이 향상됨:

    ```java
    Coffee espresso = new Espresso.Builder(Coffee.BeanType.ROBUSTA, Coffee.Size.GRANDE, Coffee.Shots.SINGLE)
            .addTopping(Coffee.Topping.CHOCOLATE_POWDER)
            .selectStrength(NonMilkCoffee.Strength.STRONG)
            .selectCaffeine(Espresso.Caffeine.HIGH)
            .build();
    ```

- 빌더 패턴을 사용함으로써 **각 단계에서 객체가 어떻게 구성되는지 쉽게 이해 가능**.

---

**✅ 8. 성능 최적화 (Performance Optimization)**

- **불필요한 객체 생성을 방지**: 객체가 한 번 생성되면 변경되지 않으므로 **메모리 사용량 감소**.
- **필요한 속성만 저장**하여 **메모리 낭비 방지**.
- **객체를 한 번에 생성하여 수정할 필요가 없기 때문에 성능 최적화에 유리**.

---

## **📌 결론**

✔ **객체의 계층적 구조를 활용하여 코드의 모듈성과 재사용성을 극대화**.

✔ **유지보수와 확장성이 뛰어나며, 코드 수정이 최소화됨**.

✔ **메서드 체이닝을 활용하여 직관적인 객체 생성을 지원하며, 가독성이 향상됨**.

✔ **객체의 불변성을 유지하여 안정성과 성능 최적화 가능**.