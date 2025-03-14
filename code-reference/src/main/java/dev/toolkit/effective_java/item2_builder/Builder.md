# **1. 생성자와 정적 팩터리의 공통적인 제약**

- **선택적 매개변수**가 많을 때 적절히 대응하기 어렵다.
- 특히, 선택적 매개변수가 많을 경우 이를 어떻게 처리할지가 큰 문제.

---

# 2. 예제: 게임 캐릭터
```java
package dev.toolkit.effective_java.item2_builder;

import java.util.List;

public class GameCharacter {
    private String name;
    private int level;
    private int health;
    private int mana;
    private int attackPower;
    private int defense;
    private String specialty;
    private List<String> skills;
    private List<String> equipment;

    /**
     * Default Constructor
     * @param name : 캐릭터 명
     * @param level : 캐릭터 레벨
     * @param health : 캐릭터 체력
     * @param mana : 캐릭터 마나
     * @param attackPower : 캐릭터 공격력
     * @param defense : 캐릭터 방어력
     * @param specialty : 캐릭터 특성
     * @param skills : 캐릭터 스킬 목록
     * @param equipment : 캐릭터 장비 목록
     */
    public GameCharacter(String name, int level, int health, int mana, int attackPower, int defense, String specialty, List<String> skills, List<String> equipment) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.mana = mana;
        this.attackPower = attackPower;
        this.defense = defense;
        this.specialty = specialty;
        this.skills = skills;
        this.equipment = equipment;
    }
}

```
- 필수 항목:
    - 캐릭터 명 (name)

    <br>

- 선택 항목:
    - 캐릭터 레벨
    - 캐릭터 체력
    - 캐릭터 마나
    - 캐릭터 공격력
    - 캐릭터 방어력
    - 캐릭터 특성
    - 캐릭터 스킬 목록
    - 캐릭터 장비 목록

    <br>

- 조건
  - 필수 매개변수 - 캐럭터 명
  - 최초 캐릭터 생성시 - 선택항목 값은 모두 1 또는 null

---

# 3. 점층적 생성자 패턴 (Telescoping Constructor Pattern)
> 생성자를 여러 개 정의하여 선택적 매개변수를 단계적으로 추가하는 방식
>
- 매개변수가 많은 생성자를 대신에 사용할 수 있는 **점층적 생성자 패턴 (telescoping constructor pattern)**
    - 필수 매개변수만 받는 생성자,
    - 필수 매개변수와 선택 매개변수 1개를 받는 생성자
    - 선택 매개변수를 2개까지 받는 생성자, … 형태로

  > 선택적 매개변수를 전부다 받는 생성자까지 늘려가는 방식이다.

```java
package dev.toolkit.effective_java.item2_builder.telescopingConstructor;

import java.util.List;

public class GameCharacter_telescoping {
  private String name;
  private int level;
  private int health;
  private int mana;
  private int attackPower;
  private int defense;
  private String specialty;
  private List<String> skills;
  private List<String> equipment;

  /**
   * 필수 매개변수만 받는 생성자
   *
   * @param name : 캐릭터 명
   */
  public GameCharacter_telescoping(String name) {
    this(name, 1);
  }

  /**
   * 필수 매개변수 + 매개변수 1개를 받는 생성자
   *
   * @param name  : 캐릭터 명
   * @param level : 캐릭터 레벨
   */
  public GameCharacter_telescoping(String name, int level) {
    this(name, level,1);
  }

  /**
   * 필수 매개변수 + 매개변수 2개를 받는 생성자
   *
   * @param name   : 캐릭터 명
   * @param level  : 캐릭터 레벨
   * @param health : 캐릭터 체력
   */
  public GameCharacter_telescoping(String name, int level, int health) {
    this(name, level,health,1);
  }

  /**
   * 필수 매개변수 + 매개변수 3개를 받는 생성자
   *
   * @param name   : 캐릭터 명
   * @param level  : 캐릭터 레벨
   * @param health : 캐릭터 체력
   * @param mana   : 캐릭터 마나
   */
  public GameCharacter_telescoping(String name, int level, int health, int mana) {
    this(name, level,health,mana,1);
  }

  public GameCharacter_telescoping(String name, int level, int health, int mana, int attackPower) {
    this(name, level,health,mana,attackPower,1);
  }

  public GameCharacter_telescoping(String name, int level, int health, int mana, int attackPower, int defense) {
    this(name, level,health,mana,attackPower,defense,null);
  }

  public GameCharacter_telescoping(String name, int level, int health, int mana, int attackPower, int defense, String specialty) {
    this(name, level, health, mana, attackPower, defense, specialty, null);
  }

  public GameCharacter_telescoping(String name, int level, int health, int mana, int attackPower, int defense, String specialty, List<String> skills) {
    this(name, level, health, mana, attackPower, defense, specialty, skills,null);
  }


  /**
   * 모든 매개변수를 다 받는 생성자
   *
   * @param name        : 캐릭터 명
   * @param level       : 캐릭터 레벨
   * @param health      : 캐릭터 체력
   * @param mana        : 캐릭터 마나
   * @param attackPower : 캐릭터 공격력
   * @param defense     : 캐릭터 방어력
   * @param specialty   : 캐릭터 특성
   * @param skills      : 캐릭터 스킬 목록
   * @param equipment   : 캐릭터 장비 목록
   */
  public GameCharacter_telescoping(String name, int level, int health, int mana, int attackPower, int defense, String specialty, List<String> skills, List<String> equipment) {
    this.name = name;
    this.level = level;
    this.health = health;
    this.mana = mana;
    this.attackPower = attackPower;
    this.defense = defense;
    this.specialty = specialty;
    this.skills = skills;
    this.equipment = equipment;
  }
}
```
<br>

[ 사용 예시 ]

```java
package dev.toolkit.effective_java.item2_builder.telescopingConstructor;

public class TelescopingConstructorMain {
    public static void main(String[] args) {
        GameCharacter_telescoping character1 = new GameCharacter_telescoping("MainCharacter");
        GameCharacter_telescoping character2 = new GameCharacter_telescoping("MainCharacter",10);
        GameCharacter_telescoping character3 = new GameCharacter_telescoping("MainCharacter",10,100);
    }
}

```
> 원하는 매개변수를 모두 포함한 생성자 중 가장 짧은 것을 골라 호출하면 되다.

### 점층적 생성자 패턴의 문제점

1. 코드를 읽을때 **값의 의미가 무엇인지 헷갈릴것**이고
2. **매개변수가 몇개인지도 주의해서 세어 보아야 할 것**이다.
3. **타입이 같은 매개변수가 연달아 늘어서 있으면 찾기 어려운 버그**로 이어질 수 있다.
4. 클라이언트가 실수로 **매개변수의 순서를 바꿔 건네줘도 컴파일러는 알아채지 못하고**

   → 결국 런타임에 엉뚱한 동작을 하게 된다.

<br>

### **장점**

✅ 객체의 **불변성**(immutability)을 유지할 수 있음

✅ 한 번의 생성자 호출로 객체를 만들 수 있음

<br>

### **단점**

❌ 매개변수가 많아지면 **코드가 복잡해짐**

❌ 매개변수의 의미를 **한눈에 파악하기 어려움**

❌ **타입이 같은 매개변수가 많으면 순서가 바뀌어도 컴파일러가 오류를 감지 못함**

❌ 일부 **선택적 매개변수만 설정하고 싶어도 필요 없는 매개변수까지 채워줘야 함**

<br>

---

# 4. 자바 빈즈 패턴 (Java Beans pattern)

- 선택 매개변수가 많을 때 활용할 수 있는 두번째 대안인
  - 자바 빈즈 패턴 (Java Beans pattern)

> **매개변수 없는 생성자로 객체를 생성하고,setter 메서드로 값을 설정**하는 방식

<br>

```java
package dev.toolkit.effective_java.item2_builder.javaBeans;

import java.util.List;

public class GameCharacter_java_beans {
    private String name;
    private int level;
    private int health;
    private int mana;
    private int attackPower;
    private int defense;
    private String specialty;
    private List<String> skills;
    private List<String> equipment;

    public GameCharacter_java_beans() {}

    public void setName(String name) {this.name = name;}

    public void setLevel(int level) {this.level = level;}

    public void setHealth(int health) {this.health = health;}

    public void setMana(int mana) {this.mana = mana;}

    public void setAttackPower(int attackPower) {this.attackPower = attackPower;}

    public void setDefense(int defense) {this.defense = defense;}

    public void setSpecialty(String specialty) {this.specialty = specialty;}

    public void setSkills(List<String> skills) {this.skills = skills;}

    public void setEquipment(List<String> equipment) {this.equipment = equipment;}
}


```

<br>

[ 사용 예시 ]
```java
package dev.toolkit.effective_java.item2_builder.javaBeans;

public class JavaBeansMain {
    public static void main(String[] args) {
        GameCharacter_java_beans character = new GameCharacter_java_beans();
        character.setName("MainCharacter");
        character.setLevel(10);
        character.setHealth(100);
        character.setMana(100);
        character.setAttackPower(20);
        character.setDefense(30);
        character.setSpecialty(null);
        character.setSkills(null);
        character.setEquipment(null);
    }
}

```

<br>

### **장점**
- ✅ **매개변수가 많아도 코드가 깔끔하고 읽기 쉬움**
- ✅ **필요한 값만 설정할 수 있어 유연함**

<br>

### **단점**
❌ **객체 하나를 만들기 위해 많은 메서드를 호출해야 한다.**

  <br>

❌ **객체가 일관성이 깨질 위험이 있음**

- 객체를 만들고 setter를 호출하는 동안 미완성된 상태로 남을 수 있음.
- 예를 들어, `setCalories(100)`만 호출하고 `setServingSize()`를 호출하지 않으면

  → 일관성이 깨질 수 있음.

  <br>

❌ **불변성을 보장할 수 없음**

- 필드가 `final`이 아니기 때문에, 객체의 상태를 바꿀 수 있어 **스레드 안정성이 떨어짐**

  <br>

❌ **객체 생성 과정이 명확하지 않음**

- 어떤 필드를 설정했는지, 안 했는지 한눈에 파악하기 어려움

---

# 5. freeze 방법

### **객체를 완성한 후 동결 (freeze)**

- 객체가 완성된 후 `freeze()` 메서드를 호출하여 변경을 막는 방법이 있지만,

  **→ 프로그래머가 실수로 freeze()를 호출하지 않으면 문제가 발생**할 수 있음.

```java
package dev.toolkit.effective_java.item2_builder.freeze;

import java.util.List;

public class GameCharacter_freeze {
    private String name;
    private int level;
    private int health;
    private int mana;
    private int attackPower;
    private int defense;
    private String specialty;
    private List<String> skills;
    private List<String> equipment;

    private boolean frozen = false;

    public GameCharacter_freeze() {}
    // 각 메서드에서 checkIfFrozen()을 매번 호출
    public void setName(String name) {checkIfFrozen(); this.name = name;}
    public void setLevel(int level) {checkIfFrozen(); this.level = level;}
    public void setHealth(int health) {checkIfFrozen(); this.health = health;}
    public void setMana(int mana) {checkIfFrozen(); this.mana = mana;}
    public void setAttackPower(int attackPower) {checkIfFrozen(); this.attackPower = attackPower;}
    public void setDefense(int defense) {checkIfFrozen(); this.defense = defense;}
    public void setSpecialty(String specialty) {checkIfFrozen(); this.specialty = specialty;}
    public void setSkills(List<String> skills) {checkIfFrozen(); this.skills = skills;}
    public void setEquipment(List<String> equipment) {checkIfFrozen(); this.equipment = equipment;}

    public void freeze(){
        this.frozen = true;
    }

    private void checkIfFrozen() {
        if(frozen){
            throw new IllegalStateException("객체가 얼어있어 수정할 수 없습니다.");
        }
    }

}
```
> ❌ **하지만** 이 방식도 **개발자가 freeze()를 호출하는 것을 강제할 수 없음**
>   
>   ➡ **여전히 실수를 방지할 수 없음**

<br>

---

# 6. 빌더 패턴 (Builder Pattern) 

## **빌더 패턴의 필요성**

### **(1) 점층적 생성자 패턴 (Telescoping Constructor Pattern)의 문제점**

- ✅ **장점**
  - 객체의 **불변성(immutability)** 유지 가능

    <br>
  
- ❌ **단점**
  - 매개변수가 많아지면 **코드를 읽기 어렵고 유지보수도 어려움**
  - 선택하지 않은 매개변수도 함께 넣어야만 객체를 생성할 수 있다.
  - 같은 타입의 매개변수가 많을 경우 **순서를 잘못 넣어도 컴파일러가 오류를 감지하지 못함**

    <br>

### **(2) 자바 빈즈 패턴 (Java Beans Pattern)의 문제점**

- ✅ **장점**
  - 코드의 가독성이 높고 필요 없는 값은 설정하지 않아도 됨

    <br>

- ❌ **단점**
  - **객체가** 완전히 생성되기 전에 불완전한 상태에 놓일 수 있음

    → 일관성이 깨질 가능성

  - **불변성을 보장할 수 없음**

    → 멀티스레드 환경에서 안전하지 않음


---

## 빌더 패턴 (Builder Pattern)의 개념

> 점층적 생성자 패턴의 안정성과
>
>
> **자바 빈즈 패턴의 가독성을 결합한 방법**

<br>

### **빌더 패턴의 동작 방식**

1. **필수 매개변수**만으로 생성자(또는 정적 팩터리 메서드)를 호출하여 빌더 객체를 생성한다.

<br>

2. 빌더 객체의 **메서드 체이닝 방식 (Method Chaining)** 을 사용해 선택적 매개변수를 설정한다.

<br>

3. 마지막으로 `build()` 메서드를 호출하여 **완성된 불변 객체를 생성**한다.

  <br>

> 빌더는 생성할 클래스안에 **정적 멤버 클래스**로 만들어두는게 보통이다.

<br>


### 빌더 패턴 (Builder Pattern)의 특징

✅ **1) 불변성 유지**

- `NutritionFacts` 객체가 **불변(immutable)** 하게 생성됨.
- 한 번 생성된 객체는 변경할 수 없음.

<br>

✅ **2) 가독성이 뛰어난 코드**

```java
 // Builder 기본 사용법
GameCharacter_builder mainCharacter
        = new GameCharacter_builder.Builder("MainCharacter") // 필수 매개변수
        .level(5)
        .health(10)
        .mana(10)
        .attackPower(10)
        .defense(10)
        .specialty("Immortal")
        .skills(new ArrayList<>())
        .equipment(new ArrayList<>())
        .build();
```

- **선택적 매개변수를 쉽게 추가 가능**
- **필수 매개변수는 명확하게 보장됨**
- **값들의 의미가 명확하여 코드 가독성이 높음**
- **메서드 체이닝을 사용하여 코드가 간결함**

<br>

✅ **3) 빌더의 세터 메서드들은 빌더 자신을 반환**

- 모든 매개 변수의 기본값들을 한 곳에 모아 뒀다.
- 빌더의 세터 메서드들은 빌더 자신을 반환하기 때문에

  > 연쇄적 호출을 할 수 있다.
  >
  >
  > → 이런 방식을 메서드 호출이 흐르듯 연결된다는 뜻으로
  >
  > ( 플루언트 API(fluent API) 혹은 메서드 연쇄(method chaining) 이라 한다. )


<br>

✅ **4) 안정적인 객체 생성**

- 객체가 생성될 때 **일관성이 깨지지 않음**
- setter 메서드가 없으므로 **객체가 불완전한 상태로 존재할 가능성이 없음**
- 빌더의 생성자에서

  **→** 필수 매개변수를 강제하므로 안전함

<br>

**[ 사용 예시 ]**

```java
 // Builder 기본 사용법
GameCharacter_builder mainCharacter
        = new GameCharacter_builder.Builder("MainCharacter") // 필수 매개변수
        .level(5)
        .health(10)
        .mana(10)
        .attackPower(10)
        .defense(10)
        .specialty("Immortal")
        .skills(new ArrayList<>())
        .equipment(new ArrayList<>())
        .build();
```

<br>

---

## 빌더 패턴의 유효성 검사화 불변식 검사
```java
package dev.toolkit.effective_java.item2_builder.builder;

import java.util.List;

public class GameCharacter_builder {
    private String name;
    private int level;
    private int health;
    private int mana;
    private int attackPower;
    private int defense;
    private String specialty;
    private List<String> skills;
    private List<String> equipment;

    public static class Builder {
        // 필수 매개변수
        private final String name;

        // 선택 매개변수  모두 1 혹은 null 값으로 초기화
        private int level = 1;
        private int health = 1;
        private int mana = 1;
        private int attackPower = 1;
        private int defense = 1;
        private String specialty = null;
        private List<String> skills = null;
        private List<String> equipment = null;

        public Builder(String name) {
            if (name == null || name.isEmpty()) { // 유효성 검사 추가
                throw new IllegalArgumentException("이름은 null 이거나 비어있을 수 없습니다.");
            }
            this.name = name;
        }

        // 새로운 생성자: 기존 GameCharacter_builder 객체로부터 Builder 생성 -> ToBuilder 사용
        public Builder(GameCharacter_builder character) {
            this.name = character.name;
            this.level = character.level;
            this.health = character.health;
            this.mana = character.mana;
            this.attackPower = character.attackPower;
            this.defense = character.defense;
            this.specialty = character.specialty;
            this.skills = character.skills;
            this.equipment = character.equipment;
        }

        public Builder level(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("level은 1 이상 이여야 합니다.");
            }
            level = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder health(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("체력은 1 이상 이여야 합니다.");
            }
            health = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder mana(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("마나는 1 이상 이여야 합니다.");
            }
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder attackPower(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("공격력은 1 이상 이여야 합니다.");
            }
            attackPower = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder defense(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("방어력은 1 이상 이여야 합니다.");
            }
            defense = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder specialty(String val) {
            if (val.isEmpty()) {
                throw new IllegalArgumentException("특성은 비어있을 수 없습니다.");
            }
            specialty = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder skills(List<String> val) {
            if (val == null) {
                throw new IllegalArgumentException("Skill 목록은 null 일 수 없습니다.");
            }
            skills = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder equipment(List<String> val) {
            if (val == null) {
                throw new IllegalArgumentException("장비 목록은 null 일 수 없습니다.");
            }
            equipment = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }


        public GameCharacter_builder build() {
            // 불변식 체크
            // 규칙 1. 레벨이 높아질수록 체력과 마나의 최소값도 증가해야 한다는 규칙.

            if (level > 9 && health < 50) {
                throw new IllegalStateException("레벨 10 이상이면 체력은 최소 50 이상이어야 합니다.");
            }
            if (level > 9 && mana < 20) {
                throw new IllegalStateException("레벨 10 이상이면 마나는 최소 20 이상이어야 합니다.");
            }

            return new GameCharacter_builder(this);
        }

    }

    private GameCharacter_builder(Builder builder) {
        name = builder.name;
        level = builder.level;
        health = builder.health;
        mana = builder.mana;
        attackPower = builder.attackPower;
        defense = builder.defense;
        specialty = builder.specialty;
        skills = builder.skills;
        equipment = builder.equipment;
    }

    // ToBuilder 메서드 추가
    public Builder toBuilder() {
        return new Builder(this); // toBuilder 는 수정된 객체를 새로 만들어 반환한다.
    }

    @Override
    public String toString() {
        return "GameCharacter_builder{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", mana=" + mana +
                ", attackPower=" + attackPower +
                ", defense=" + defense +
                ", specialty='" + specialty + '\'' +
                ", skills=" + skills +
                ", equipment=" + equipment +
                '}';
    }
}
```

<br>

**[빌더 사용]**
```java
package dev.toolkit.effective_java.item2_builder.builder;

import java.util.ArrayList;

public class BuilderMain {
    public static void main(String[] args) {

        // Builder 기본 사용법
        GameCharacter_builder mainCharacter
                = new GameCharacter_builder.Builder("MainCharacter") // 필수 매개변수
                .level(5)
                .health(10)
                .mana(10)
                .attackPower(10)
                .defense(10)
                .specialty("Immortal")
                .skills(new ArrayList<>())
                .equipment(new ArrayList<>())
                .build();
        System.out.println(mainCharacter);


        // Builder 유효성 검사 실패
        GameCharacter_builder subCharacter1
                = new GameCharacter_builder.Builder("subCharacter1") // 필수 매개변수
                .level(-1).build(); // IllegalArgumentException


        // Builder 불변식 검사 실패
        // 불변식 1. 레벨 10 이상이면 체력은 최소 50 이상이어야 합니다.
        // 불변식 2. 레벨 10 이상이면 마나는 최소 20 이상이어야 합니다.
        GameCharacter_builder subCharacter2
                = new GameCharacter_builder.Builder("subCharacter2") // 필수 매개변수
                .level(10)
                .health(1)
                .mana(1)
                .build(); // IllegalStateException 불변식 검사 실패


        // ToBuilder 사용
        GameCharacter_builder subCharacter3
                = new GameCharacter_builder.Builder("subCharacter3")
                .level(5)
                .health(10)
                .mana(10)
                .build();
        System.out.println(subCharacter3);

        GameCharacter_builder subCharacter3_modify
                = subCharacter3.toBuilder()
                .level(3)
                .health(3)
                .mana(3)
                .build();
        System.out.println(subCharacter3_modify);

    }
}

```

<br>

### **1️⃣ 잘못된 매개변수를 일찍 발견하는 유효성 검사**
- 입력값(매개변수)이 잘못되었는지 즉시 검사해야 한다. 
- 이를 위해, 각 Setter 메서드에서 개별 매개변수를 검사한다.
- 잘못된 값이 감지되면, `IllegalArgumentException`을 던져 즉시 예외 발생.


<br>

### **2️⃣ build() 메서드에서 불변식(invariant) 검사** 
- 객체가 생성될 때(build() 호출 시), 여러 매개변수 간의 관계를 검사해야 한다. 
- 개별 매개변수는 유효할 수 있지만, 여러 개의 값이 조합될 때 논리적으로 맞지 않을 수도 있다. 
- 이러한 불변식 검사는 build() 메서드에서 수행한다.
- 불변식 검사가 실패하면, `IllegalStateException`을 던져 즉시 예외 발생.

<br>

---

## ToBuilder 사용
```java
package dev.toolkit.effective_java.item2_builder.builder;

import java.util.List;

public class GameCharacter_builder {
    private String name;
    private int level;
    private int health;
    private int mana;
    private int attackPower;
    private int defense;
    private String specialty;
    private List<String> skills;
    private List<String> equipment;

    public static class Builder {
        // 필수 매개변수
        private final String name;

        // 선택 매개변수  모두 1 혹은 null 값으로 초기화
        private int level = 1;
        private int health = 1;
        private int mana = 1;
        private int attackPower = 1;
        private int defense = 1;
        private String specialty = null;
        private List<String> skills = null;
        private List<String> equipment = null;

        public Builder(String name) {
            if (name == null || name.isEmpty()) { // 유효성 검사 추가
                throw new IllegalArgumentException("이름은 null 이거나 비어있을 수 없습니다.");
            }
            this.name = name;
        }

        // 새로운 생성자: 기존 GameCharacter_builder 객체로부터 Builder 생성 -> ToBuilder 사용
        public Builder(GameCharacter_builder character) {
            this.name = character.name;
            this.level = character.level;
            this.health = character.health;
            this.mana = character.mana;
            this.attackPower = character.attackPower;
            this.defense = character.defense;
            this.specialty = character.specialty;
            this.skills = character.skills;
            this.equipment = character.equipment;
        }

        public Builder level(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("level은 1 이상 이여야 합니다.");
            }
            level = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder health(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("체력은 1 이상 이여야 합니다.");
            }
            health = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder mana(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("마나는 1 이상 이여야 합니다.");
            }
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder attackPower(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("공격력은 1 이상 이여야 합니다.");
            }
            attackPower = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder defense(int val) {
            if (val < 0) {
                throw new IllegalArgumentException("방어력은 1 이상 이여야 합니다.");
            }
            defense = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder specialty(String val) {
            if (val.isEmpty()) {
                throw new IllegalArgumentException("특성은 비어있을 수 없습니다.");
            }
            specialty = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder skills(List<String> val) {
            if (val == null) {
                throw new IllegalArgumentException("Skill 목록은 null 일 수 없습니다.");
            }
            skills = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }

        public Builder equipment(List<String> val) {
            if (val == null) {
                throw new IllegalArgumentException("장비 목록은 null 일 수 없습니다.");
            }
            equipment = val;
            return this; // 다시 Builder를 반환하여 체이닝 유지
        }


        public GameCharacter_builder build() {
            // 불변식 체크
            // 규칙 1. 레벨이 높아질수록 체력과 마나의 최소값도 증가해야 한다는 규칙.

            if (level > 9 && health < 50) {
                throw new IllegalStateException("레벨 10 이상이면 체력은 최소 50 이상이어야 합니다.");
            }
            if (level > 9 && mana < 20) {
                throw new IllegalStateException("레벨 10 이상이면 마나는 최소 20 이상이어야 합니다.");
            }

            return new GameCharacter_builder(this);
        }

    }

    private GameCharacter_builder(Builder builder) {
        name = builder.name;
        level = builder.level;
        health = builder.health;
        mana = builder.mana;
        attackPower = builder.attackPower;
        defense = builder.defense;
        specialty = builder.specialty;
        skills = builder.skills;
        equipment = builder.equipment;
    }

    // ToBuilder 메서드 추가
    public Builder toBuilder() {
        return new Builder(this); // toBuilder 는 수정된 객체를 새로 만들어 반환한다.
    }

    @Override
    public String toString() {
        return "GameCharacter_builder{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", mana=" + mana +
                ", attackPower=" + attackPower +
                ", defense=" + defense +
                ", specialty='" + specialty + '\'' +
                ", skills=" + skills +
                ", equipment=" + equipment +
                '}';
    }
}
```

- ToBuilder를 만들기 위해 `Builder` 정적 클래스 내에 추가 코드 필요
  ```java
  // 새로운 생성자: 기존 GameCharacter_builder 객체로부터 Builder 생성 -> ToBuilder 사용
        public Builder(GameCharacter_builder character) {
            this.name = character.name;
            this.level = character.level;
            this.health = character.health;
            this.mana = character.mana;
            this.attackPower = character.attackPower;
            this.defense = character.defense;
            this.specialty = character.specialty;
            this.skills = character.skills;
            this.equipment = character.equipment;
        }
  ```
  - toBuilder()가 실행될 때 기존 객체를 Builder로 변환할 방법이 필요 
  - Builder(GameCharacter_builder character) 생성자를 추가함으로써, 기존 객체를 복사한 Builder를 만들 수 있게 됨 
  - 이 빌더를 사용하면 기존 객체의 값을 유지하면서 특정 값만 변경하여 새로운 객체를 생성할 수 있음


<br>

- ToBuilder를 만들기 위해 클래스 내에 추가 코드 필요
  ```java
  // ToBuilder 메서드 추가
    public Builder toBuilder() {
        return new Builder(this); // toBuilder 는 수정된 객체를 새로 만들어 반환한다.
    }
  ```
  - toBuilder()를 통해 기존 객체를 기반으로 수정할 수 있는 Builder를 제공
    >즉, 기존 객체를 복사한 새로운 빌더를 반환하여 객체를 일부 수정한 후 새로운 객체를 생성할 수 있음
    

<br>

**[ ToBuilder 사용 ]**

```java
// ToBuilder 사용
GameCharacter_builder subCharacter3
        = new GameCharacter_builder.Builder("subCharacter3")
                .level(5)
                .health(10)
                .mana(10)
                .build();
        System.out.println(subCharacter3);

        GameCharacter_builder subCharacter3_modify
                = subCharacter3.toBuilder()
                .level(3)
                .health(3)
                .mana(3)
                .build();
        System.out.println(subCharacter3_modify);
```
- 불변 객체라서 **setter를 만들 수 없지만,** 일부 필드만 변경해야 하는 경우가 있음.
  - 이 경우 ToBuilder를 사용해 특정 값만 변경된 새로운 객체를 만들어 사용할 수 있다. 
 
<br>

>  `toBuilder()` 패턴을 사용하면 기존 객체를 기반으로 일부 값만 변경한 새로운 객체를 쉽게 만들 수 있음.
  - 여기서 중요한 점은 일부 값을 변경해서 `새로운 객체`를 반환한다는 점이다.