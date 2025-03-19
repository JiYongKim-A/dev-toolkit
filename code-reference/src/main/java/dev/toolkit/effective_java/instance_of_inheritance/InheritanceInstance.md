# [ 먼저 알아야 할 개념 : 상속 개념의 인스턴스화 과정 ]

## **🔥 `new Child();` 실행 과정**

```java
package dev.toolkit.effective_java.instance_of_inheritance;

public class InheritanceInstanceMain {
    public static void main(String[] args) {
        Child child = new Child();
    }
}

class Parent {
    int parentInstanceField = parentInitialize();

    public Parent() {
        System.out.println("Parent 클래스 생성자 실행");
    }

    public int parentInitialize() {
        System.out.println("Parent 인스턴스 필드 초기화 실행");
        return 10;
    }
}

class Child extends Parent {
    int childInstanceField = childInitialize();
    public Child() {
        System.out.println("Child 클래스 생성자 실행");
    }

    public int childInitialize() {
        System.out.println("Child 인스턴스 필드 초기화 실행");
        return 10;
    }
}
```
---

### **📌 실행 결과**
```java
Task :dev.toolkit.effective_java.instance_of_inheritance.InheritanceInstanceMain.main()
Parent 인스턴스 필드 초기화 실행
Parent 클래스 생성자 실행
Child 인스턴스 필드 초기화 실행
Child 클래스 생성자 실행

BUILD SUCCESSFUL in 357ms
```
---

### **📌 1️⃣ `new Child();` 실행됨 (메모리 할당)**

- `Child` 객체를 만들기 위해 JVM이 먼저 메모리를 할당함.
- 이때, **`Child` 객체 안에는 부모(`Parent`) 부분도 포함되어 있음!**
- 하지만 **아직 Child 필드 초기화나 생성자는 실행되지 않음!**
   
    <br>

- JVM이 내부적으로 [**자바 규칙 : 필드 초기화 → 생성자 실행을 순차적으로 진행해야 한다**]는 규칙을 따름.

  → 자식(`Child`) 객체에는 부모(`Parent`) 부분도 포함되어 있기 때문에, 부모를 먼저 초기화해야 함!


---

### **📌 2️⃣ `Child` 생성자 실행 (하지만 본문 실행은 아직 안 됨)**

- 이제 `Child`의 생성자(`Child()`)를 실행하려고 하는데, **첫 줄이 `super();`이므로 부모 생성자로 가야 함!**
- **[ 자바 규칙: 자식 생성자를 실행하기 전에, 부모 생성자가 먼저 실행되어야 한다. ]**
- **그래서 자식 생성자의 `super();`를 만나면 즉시 부모 생성자로 "점프"한다.**
> 만약 Parent 클래스 생성자가 기본 생성자가 아닐시에 Child 클래스 생성자에 `super(..)`를 추가해 주어야 컴파일 에러가 않는다.
---

### **📌 3️⃣ 부모 생성자로 점프 (`super();` 실행)**

- `super();`을 실행하는 순간, `Parent` 생성자로 이동함.
- 그런데! **부모 생성자를 실행하기 전에,** 먼저 부모 필드를 초기화해야 함!
    - **[자바 규칙: 필드 초기화 → 생성자 실행을 순차적으로 진행해야 한다]**
- **즉, 부모 생성자의 첫 줄을 실행하기 전에, 부모 필드가 먼저 초기화된다.**

---

### **📌 4️⃣ 부모 필드 초기화 진행**

- `Parent` 클래스에서 선언된 인스턴스 필드들이 기본값을 갖고 있다가, 명시된 값으로 초기화됨.
- 예제 코드 기준:

    ```java
    int parentInstanceField = parentInitialize();
    ```

    - `"Parent 인스턴스 필드 초기화 실행"` 출력됨.
    - **부모 필드가 초기화됨!**

---

### **📌 5️⃣ 부모 생성자 실행 (`Parent()` 내부 코드 실행됨)**

- 이제서야 부모 생성자 본문 실행됨:

    ```java
    public Parent() {
        System.out.println("Parent 클래스 생성자 실행");
    }
    ```

    - `"Parent 클래스 생성자 실행"` 출력됨.
    - **부모 생성자 실행 완료!**
    
    <br>
  
- **부모 생성자가 끝나면, 다시 실행 흐름이 자식 생성자로 돌아감.**

---

### **📌 6️⃣ 부모 생성자 종료 → `super();` 다음 줄로 이동하려 하지만...**

- `super();` 다음 줄로 실행이 이어질 것처럼 보이지만,

  **→ JVM은 아직 `Child`의 필드 초기화를 하지 않았음!**

- 따라서 **자식 필드를 먼저 초기화해야 함.**
- **[자바 규칙: 필드 초기화 → 생성자 실행을 순차적으로 진행해야 한다]**

---

### **📌 7️⃣ 자식 필드 초기화 진행**

- `Child`의 인스턴스 필드가 초기화됨:

    ```java
    int childInstanceField = childInitialize();
    ```

    - `"Child 인스턴스 필드 초기화 실행"` 출력됨.
    - **자식 필드 초기화 완료!**

---

### **📌 8️⃣ 자식 생성자 실행 (`super();` 다음 코드 실행됨)**

- 이제서야 `super();` 다음 줄 실행됨:

    ```java
     public Child() {
        System.out.println("Child 클래스 생성자 실행");
    }
    ```

    - `"Child 클래스 생성자 실행"` 출력됨.
    - **여기까지 완료되면 자식 객체 생성이 끝남.**

---

# **🔥 부모 생성자가 기본 생성자가 아닐 경우 → 반드시 `super(인자...)`를 명시해야 하는 이유**

💡 **"부모 생성자가 기본 생성자가 아닐 때 `super(인자...)`를 명시해야 하는 이유?"**

➡ **부모 객체를 초기화하기 위해서는 반드시 부모 생성자가 실행되어야 하기 때문!**

<br>

### **📌 기본 생성자가 없는 경우 필수적으로 `super(인자...)`를 써야 하는 이유**

```java
class Parent {
    int a;

    // 기본 생성자가 없음!
    Parent(int num) {
        this.a = num;
    }
}

class Child extends Parent {
    Child() {
        super(10); // 기본 생성자가 없으므로 명시적으로 부모 생성자 호출 필요!
    }
}
```

<br>

📌 **여기서 `super(10);`이 없으면 컴파일 에러가 발생하는 이유는?**

- `Child()` 생성자가 실행될 때, **부모 필드 `a`를 초기화하려면 부모 생성자가 먼저 실행되어야 한다.**
- 하지만, **부모의 기본 생성자가 없으므로 `super(인자...)`를 명시적으로 호출해야 부모 객체를 올바르게 초기화할 수 있다!**
- 만약 `super(10);`을 안 쓰면, **부모 객체의 초기화 방법을 찾을 수 없어서 컴파일 에러가 나는 것.**

> 결과적으로 **하나의 `B` 객체**가 생성되는데,
→ 내부적으로는 **`A`의 부분**과 **`B`의 부분**을 모두 합쳐 갖게 된다.
>
- **!!즉 내가 만든 자식 객체는!!**
    - 물리적으로는 단일 객체이지만,
    - **상속 구조**에 따라 “위(부모) → 아래(자식)” 순으로 생성자 및 필드 초기화가 호출되는 셈

  > **단일 객체가 생성되는 것**이고, 그 **단일 객체**가 부모 클래스 부분 **+** 자식 클래스 부분을
  → 모두 합쳐 갖는 형태가 되는 것 !!!!

