package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

// MilkBasedCoffee: 우유가 포함된 커피를 정의하는 추상 클래스
public abstract class MilkBasedCoffee extends Coffee {
    // 우유 종류 및 추가 가격을 정의하는 Enum
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

    private final MilkType milkType; // 선택된 우유 타입 (불변)

    // MilkBasedCoffee의 빌더 패턴을 위한 추상 클래스 정의
    // 제네릭 타입 <T>는 자기 자신(Builder의 하위 클래스)을 가리키며, Coffee의 빌더를 상속받는다.
    abstract static class Builder<T extends Builder<T>> extends Coffee.Builder<T> {
        private MilkType milkType = MilkType.WHOLE; // 기본 타입

        // 생성자: 원두 타입, 사이즈, 샷 정보를 필수 입력
        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        // 우유 종류를 선택하는 메서드 (빌더 패턴 적용)
        public T selectMilkType(MilkType milkType) {
            this.milkType = milkType;
            return self();
        }

        // MilkBasedCoffee의 가격을 계산하는 메서드
        @Override
        protected double calculatePrice() {
            double price = super.calculatePrice();
            price += milkType.getPrice();
            return price;
        }



        // 하위 클래스에서 자신의 타입을 반환하기 위한 추상 메서드 - 재귀적 재너릭 패턴
        @Override
        protected abstract T self(); // ✅ 하위 클래스에서 구현하도록 강제

        // 하위 클래스에서 빌드 메서드 구현 필요
        public abstract MilkBasedCoffee build(); // ✅ 하위 클래스에서 객체 생성

    }

    // MilkBasedCoffee 생성자: 빌더에서 전달받은 값을 초기화
    MilkBasedCoffee(Builder<?> builder) {
        super(builder);
        this.milkType = builder.milkType;
    }

    // 선택된 우유 타입을 반환하는 메서드
    public MilkType getMilkType() {
        return milkType;
    }

    @Override
    protected void buildString(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 우유 선택 --------------------------\n")
                .append("우유 타입 = ").append(milkType);

    }
}
