package dev.toolkit.effective_java.item.item2_builder.InheritanceBuilder;

public class CoffeeMain {
    public static void main(String[] args) {

        Coffee latte = new Latte.Builder(Coffee.BeanType.ROBUSTA, Coffee.Size.GRANDE, Coffee.Shots.SINGLE)
                .addTopping(Coffee.Topping.CINNAMON_POWDER)
                .addTopping(Coffee.Topping.WHIPPED_CREAM)
                .selectMilkType(MilkBasedCoffee.MilkType.ALMOND)
                .selectLatteStyle(Latte.LatteStyle.VANILLA)
                .selectMilkToCoffeeRatio(Latte.MilkToCoffeeRatio.HIGH)
                .withLatteArt(true)
                .build();
        System.out.println("[ Latte 명세서 출력 ] " + latte);


        Coffee espresso = new Espresso.Builder(Coffee.BeanType.ROBUSTA, Coffee.Size.GRANDE, Coffee.Shots.SINGLE)
                .addTopping(Coffee.Topping.CHOCOLATE_POWDER)
                .selectStrength(NonMilkCoffee.Strength.STRONG)
                .selectCaffeine(Espresso.Caffeine.HIGH)
                .build();
        System.out.println("[ Espresso 명세서 출력 ]" + espresso);

    }
}
