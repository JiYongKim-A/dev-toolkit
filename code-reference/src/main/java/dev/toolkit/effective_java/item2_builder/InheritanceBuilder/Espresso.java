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
    protected void buildString(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 에스프레소 선택 --------------------------\n")
                .append("카페인 강도 = ").append(caffeine)
                .append("\n-------------------------- 최종 가격 --------------------------\n")
                .append("최종 가격 = $ ").append(getPrice())
                .append("\n-------------------------------------------------------------\n");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(sb);
        return sb.toString();
    }
}
