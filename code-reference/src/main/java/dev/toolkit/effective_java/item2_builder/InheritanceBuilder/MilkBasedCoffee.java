package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public abstract class MilkBasedCoffee extends Coffee {
    public enum MilkType {
        WHOLE(0.0), SKIM(0.0), SOY(0.5), OAT(0.5), ALMOND(0.5);
        private final double price;

        MilkType(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    private final MilkType milkType;

    abstract static class Builder<T extends Builder<T>> extends Coffee.Builder<T> {
        private MilkType milkType = MilkType.WHOLE; // 기본 타입

        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        public T selectMilkType(MilkType milkType) { // ✅ 우유 종류 설정 메서드
            this.milkType = milkType;
            return self();
        }

        protected double calculateMilkPrice() {
            double price = super.calculatePrice();
            price += milkType.getPrice();
            return price;
        }


        @Override
        protected abstract T self(); // ✅ 하위 클래스에서 구현하도록 강제

        protected abstract MilkBasedCoffee build(); // ✅ 하위 클래스에서 객체 생성

    }

    MilkBasedCoffee(Builder<?> builder, double price) { // ✅ 생성자는 protected로 설정
        super(builder, price);
        this.milkType = builder.milkType;
    }

    @Override
    protected void buildString(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 우유 선택 --------------------------\n")
                .append("우유 타입 = ").append(milkType);

    }
}
