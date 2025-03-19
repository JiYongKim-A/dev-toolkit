package dev.toolkit.effective_java.item.item2_builder.freeze;

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
