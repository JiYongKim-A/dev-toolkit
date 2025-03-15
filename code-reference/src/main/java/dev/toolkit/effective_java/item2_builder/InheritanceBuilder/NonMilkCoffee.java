package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public abstract class NonMilkCoffee extends Coffee {
    public enum Strength {MILD, MEDIUM, STRONG}

    private final Strength strength;

    abstract static class Builder<T extends Builder<T>> extends Coffee.Builder<T> {
        private Strength strength = Strength.MILD; // 기본 타입

        public Builder(Coffee.BeanType beanType, Coffee.Size size, Coffee.Shots shots) {
            super(beanType, size, shots);
        }

        public T selectStrength(Strength strength) {
            this.strength = strength;
            return self();
        }

        protected double calculateNonMilkPrice() {
            return super.calculatePrice();
        }

        @Override
        protected abstract T self();

        protected abstract NonMilkCoffee build();
    }

    NonMilkCoffee(Builder<?> builder,double price) {
        super(builder,price);
        this.strength = builder.strength;
    }

    @Override
    public String toString() {
        return super.toString() + "\n-------------------------- 강도 선택 --------------------------\n커피 강도 타입 = " + strength;
    }
}
