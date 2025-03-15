package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public class Latte extends MilkBasedCoffee {
    public enum LatteStyle {
        CLASSIC(0.0), VANILLA(0.5), CARAMEL(0.5), HAZELNUT(0.5);
        private final double price;
        LatteStyle(double price) { this.price = price; }
        public double getPrice() { return price; }
    }

    public enum MilkToCoffeeRatio {LOW, MEDIUM, HIGH}

    private final LatteStyle latteStyle;
    private final MilkToCoffeeRatio milkToCoffeeRatio;
    private final boolean latteArt;

    public static class Builder extends MilkBasedCoffee.Builder<Builder> {
        private LatteStyle latteStyle = LatteStyle.CLASSIC; // 기본값
        private MilkToCoffeeRatio milkToCoffeeRatio = MilkToCoffeeRatio.MEDIUM; // 기본값
        private boolean latteArt = false; // 기본값

        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        public Builder selectLatteStyle(LatteStyle latteStyle) { // ✅ 라떼 비율 설정
            this.latteStyle = latteStyle;
            return this;
        }

        public Builder selectMilkToCoffeeRatio(MilkToCoffeeRatio ratio) { // ✅ 라떼 비율 설정
            this.milkToCoffeeRatio = ratio;
            return this;
        }

        public Builder withLatteArt(boolean latteArt) {
            this.latteArt = latteArt;
            return this;
        }

        private double calculateLattePrice() {
            double basePrice = super.calculateMilkPrice(); // 상위 가격 계산 먼저

            basePrice += latteStyle.getPrice(); // ✅ LatteStyle에서 직접 가격 가져오기

            if (latteArt) basePrice += 0.7; // ✅ Latte Art 추가 비용

            return basePrice;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected Latte build() {
            return new Latte(this, calculateLattePrice());
        }
    }

    private Latte(Builder builder, double price) {
        super(builder, price);
        this.latteStyle = builder.latteStyle;
        this.milkToCoffeeRatio = builder.milkToCoffeeRatio;
        this.latteArt = builder.latteArt;
    }

    @Override
    public String toString() {
        return super.toString() + "\n-------------------------- 라떼 선택 --------------------------\n" +
                "라떼 스타일 = " + latteStyle +
                ", 밀크 비율 = " + milkToCoffeeRatio +
                ", 라떼 아트 = " + latteArt +
                "\n-------------------------- 최종 가격 --------------------------\n" +
                "최종 가격 = $ " + getPrice()+
                "\n-------------------------------------------------------------\n";
    }


}
