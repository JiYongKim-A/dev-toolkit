package dev.toolkit.effective_java.item.item2_builder.telescopingConstructor;

public class TelescopingConstructorMain {
    public static void main(String[] args) {
        GameCharacter_telescoping character1 = new GameCharacter_telescoping("MainCharacter");
        GameCharacter_telescoping character2 = new GameCharacter_telescoping("Sub1Character",10);
        GameCharacter_telescoping character3 = new GameCharacter_telescoping("Sub2Character",10,100);
    }
}
