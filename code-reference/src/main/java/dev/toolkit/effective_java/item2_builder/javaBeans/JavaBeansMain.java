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
