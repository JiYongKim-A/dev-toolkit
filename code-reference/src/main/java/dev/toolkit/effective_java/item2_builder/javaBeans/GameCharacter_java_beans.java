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
