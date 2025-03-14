package dev.toolkit.effective_java.item2_builder;

import java.util.List;

public class GameCharacter_normal {
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
    public GameCharacter_normal(String name, int level, int health, int mana, int attackPower, int defense, String specialty, List<String> skills, List<String> equipment) {
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
