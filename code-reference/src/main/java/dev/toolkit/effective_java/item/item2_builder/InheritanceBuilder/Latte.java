package dev.toolkit.effective_java.item.item2_builder.InheritanceBuilder;

// Latte: MilkBasedCoffee를 상속받아 라떼를 정의하는 클래스
public class Latte extends MilkBasedCoffee {

    // 라떼 스타일 및 추가 가격을 정의하는 Enum
    public enum LatteStyle {
        CLASSIC(0.0), VANILLA(0.5), CARAMEL(0.5), HAZELNUT(0.5);

        private final double price; // 라떼 스타일에 따른 추가 가격

        LatteStyle(double price) { this.price = price; }

        public double getPrice() { return price; }
    }

    // 우유와 커피의 비율을 설정하는 Enum
    public enum MilkToCoffeeRatio {LOW, MEDIUM, HIGH}

    private final LatteStyle latteStyle; // 선택된 라떼 스타일
    private final MilkToCoffeeRatio milkToCoffeeRatio; // 우유-커피 비율
    private final boolean latteArt; // 라떼 아트 여부

    // Latte의 빌더 클래스 정의
    public static class Builder extends MilkBasedCoffee.Builder<Builder> {
        private LatteStyle latteStyle = LatteStyle.CLASSIC; // 기본값: 클래식
        private MilkToCoffeeRatio milkToCoffeeRatio = MilkToCoffeeRatio.MEDIUM; // 기본값: 중간 비율
        private boolean latteArt = false; // 기본값: 라떼 아트 없음

        // 필수 필드를 포함한 생성자 (원두, 사이즈, 샷 정보)
        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        // 라떼 스타일 선택 메서드
        public Builder selectLatteStyle(LatteStyle latteStyle) {
            this.latteStyle = latteStyle;
            return this;
        }

        // 우유-커피 비율 선택 메서드
        public Builder selectMilkToCoffeeRatio(MilkToCoffeeRatio ratio) {
            this.milkToCoffeeRatio = ratio;
            return this;
        }

        // 라떼 아트 추가 여부 설정 메서드
        public Builder withLatteArt(boolean latteArt) {
            this.latteArt = latteArt;
            return this;
        }

        // 최종 라떼 가격을 계산하는 메서드
        @Override
        protected double calculatePrice() {
            double price = super.calculatePrice(); // 기본 커피 가격 계산
            price += latteStyle.getPrice(); // 선택한 라떼 스타일의 추가 비용 반영
            if (latteArt) price += 0.7; // 라떼 아트 추가 비용 반영
            return price;
        }

        // 자기 자신을 반환하는 self() 메서드 (빌더 패턴 유지)
        @Override
        protected Builder self() {
            return this;
        }

        // Latte 객체를 생성하는 build() 메서드
        @Override
        public Latte build() {
            return new Latte(this);
        }
    }

    // Latte의 생성자 (빌더 객체에서 값 전달)
    private Latte(Builder builder) {
        super(builder);
        this.latteStyle = builder.latteStyle;
        this.milkToCoffeeRatio = builder.milkToCoffeeRatio;
        this.latteArt = builder.latteArt;
    }

    // 라떼 스타일 반환 메서드
    public LatteStyle getLatteStyle() {
        return latteStyle;
    }

    // 우유-커피 비율 반환 메서드
    public MilkToCoffeeRatio getMilkToCoffeeRatio() {
        return milkToCoffeeRatio;
    }

    // 라떼 아트 여부 반환 메서드
    public boolean isLatteArt() {
        return latteArt;
    }

    // 라떼 정보를 문자열로 변환하여 반환하는 메서드
    private void buildLatte(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 라떼 선택 --------------------------\n")
                .append("라떼 스타일 = ").append(latteStyle)
                .append(", 밀크 비율 = ").append(milkToCoffeeRatio)
                .append(", 라떼 아트 = ").append(latteArt)
                .append("\n-------------------------- 최종 가격 --------------------------\n")
                .append("최종 가격 = $ ").append(getPrice())
                .append("\n-------------------------------------------------------------\n");
    }

    // toString() 메서드: 라떼 정보를 문자열로 반환
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildLatte(sb);
        return sb.toString();
    }
}
