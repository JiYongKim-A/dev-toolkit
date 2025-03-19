package dev.toolkit.effective_java.item.item2_builder.builder;

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
