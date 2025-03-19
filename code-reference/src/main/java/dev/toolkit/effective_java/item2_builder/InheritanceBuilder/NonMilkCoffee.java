package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

// NonMilkCoffee: 우유가 들어가지 않는 커피를 정의하는 추상 클래스
public abstract class NonMilkCoffee extends Coffee {
    // 커피 강도를 정의하는 Enum
    public enum Strength {MILD, MEDIUM, STRONG}

    private final Strength strength;  // 커피 강도 (불변)

    // NonMilkCoffee의 빌더 패턴을 위한 추상 클래스 정의
    // 제네릭 타입 <T>는 자기 자신(Builder의 하위 클래스)을 가리키며, Coffee의 빌더를 상속받는다.
    abstract static class Builder<T extends Builder<T>> extends Coffee.Builder<T> {
        private Strength strength = Strength.MILD; // 기본 강도는 MILD

        // 생성자: 원두 타입, 사이즈, 샷 정보를 필수 입력
        public Builder(Coffee.BeanType beanType, Coffee.Size size, Coffee.Shots shots) {
            super(beanType, size, shots);
        }

        // 커피 강도를 선택하는 메서드 (빌더 패턴 적용)
        public T selectStrength(Strength strength) {
            this.strength = strength;
            return self();
        }

        // NonMilkCoffee의 가격을 계산하는 메서드
        @Override
        protected double calculatePrice() {
            // 추가 금액 없음
            return super.calculatePrice();
        }


        // 하위 클래스에서 자신의 타입을 반환하기 위한 추상 메서드 - 재귀적 재너릭 패턴
        @Override
        protected abstract T self();

        // 하위 클래스에서 빌드 메서드 구현 필요
        public abstract NonMilkCoffee build();
    }

    // NonMilkCoffee 생성자: 빌더에서 전달받은 값을 초기화
    NonMilkCoffee(Builder<?> builder) {
        super(builder);
        this.strength = builder.strength;
    }

    @Override
    protected void buildString(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 강도 선택 --------------------------\n")
                .append("커피 강도 타입 = ")
                .append(strength);
    }
}
