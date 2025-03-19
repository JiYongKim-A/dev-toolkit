package dev.toolkit.effective_java.item.item2_builder.telescopingConstructor;

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
