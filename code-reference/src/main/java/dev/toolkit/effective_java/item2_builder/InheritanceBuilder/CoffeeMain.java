package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public class CoffeeMain {
    public static void main(String[] args) {

        // Grande += 0.5
        Latte latte = new Latte.Builder(Coffee.BeanType.ROBUSTA, Coffee.Size.GRANDE, Coffee.Shots.SINGLE)
                .addTopping(Coffee.Topping.CINNAMON_POWDER) // +0.2
                .addTopping(Coffee.Topping.WHIPPED_CREAM) // +0.5
                .selectMilkType(MilkBasedCoffee.MilkType.ALMOND) // +0.5
                .selectLatteStyle(Latte.LatteStyle.VANILLA) //+0.5
                .selectMilkToCoffeeRatio(Latte.MilkToCoffeeRatio.HIGH) // +0.0
                .withLatteArt(true) //+ 0.7
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
