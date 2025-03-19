package dev.toolkit.effective_java.item.item2_builder.builder;

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
