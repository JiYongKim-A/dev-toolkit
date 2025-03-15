package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public class Espresso extends NonMilkCoffee {
    public enum Caffeine {LOW, MEDIUM, HIGH}

    private final Caffeine caffeine;

    public static class Builder extends NonMilkCoffee.Builder<Builder> {
        private Caffeine caffeine = Caffeine.MEDIUM;

        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        public Builder selectCaffeine(Caffeine caffeine) {
            this.caffeine = caffeine;
            return this;
        }

        private double calculateEspressoPrice() {
            return super.calculateNonMilkPrice();
        }


        @Override
        protected Espresso.Builder self() {
            return this;
        }

        @Override
        protected Espresso build() {
            return new Espresso(this, calculateEspressoPrice());
        }

    }

    private Espresso(Builder builder, double price) {
        super(builder, price);
        this.caffeine = builder.caffeine;
    }

    @Override
    public String toString() {
        return super.toString() + "\n-------------------------- 카페인 선택 --------------------------\n" +
                " 카페인 강도 = " + caffeine +
                "\n-------------------------- 최종 가격 --------------------------\n" +
                "최종 가격 = $ " + getPrice()+
                "\n-------------------------------------------------------------\n";
    }
}
