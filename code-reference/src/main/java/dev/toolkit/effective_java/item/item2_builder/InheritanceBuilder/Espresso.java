package dev.toolkit.effective_java.item.item2_builder.InheritanceBuilder;

// Espresso: NonMilkCoffee를 상속받아 에스프레소를 정의하는 클래스
public class Espresso extends NonMilkCoffee {

    // 카페인 강도를 설정하는 Enum
    public enum Caffeine {LOW, MEDIUM, HIGH}

    private final Caffeine caffeine; // 선택된 카페인 강도

    // Espresso의 빌더 클래스 정의
    public static class Builder extends NonMilkCoffee.Builder<Builder> {
        private Caffeine caffeine = Caffeine.MEDIUM; // 기본값: 중간 강도

        // 필수 필드를 포함한 생성자 (원두, 사이즈, 샷 정보)
        public Builder(BeanType beanType, Size size, Shots shots) {
            super(beanType, size, shots);
        }

        // 카페인 강도를 선택하는 메서드
        public Builder selectCaffeine(Caffeine caffeine) {
            this.caffeine = caffeine;
            return this;
        }

        // 최종 에스프레소 가격을 계산하는 메서드
        @Override
        protected double calculatePrice() {
            return super.calculatePrice(); // NonMilkCoffee 가격 계산 방식 사용
        }

        // 자기 자신을 반환하는 self() 메서드 (빌더 패턴 유지)
        @Override
        protected Espresso.Builder self() {
            return this;
        }

        // Espresso 객체를 생성하는 build() 메서드
        @Override
        public Espresso build() {
            return new Espresso(this);
        }
    }

    // Espresso의 생성자 (빌더 객체에서 값 전달)
    private Espresso(Builder builder) {
        super(builder);
        this.caffeine = builder.caffeine;
    }

    // 커피 정보를 문자열로 변환하여 반환하는 메서드 (카페인 강도 추가)
    protected void buildEspresso(StringBuilder sb) {
        super.buildString(sb);
        sb.append("\n-------------------------- 에스프레소 선택 --------------------------\n")
                .append("카페인 강도 = ").append(caffeine)
                .append("\n-------------------------- 최종 가격 --------------------------\n")
                .append("최종 가격 = $ ").append(getPrice())
                .append("\n-------------------------------------------------------------\n");
    }

    // toString() 메서드: 에스프레소 정보를 문자열로 반환
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildEspresso(sb);
        return sb.toString();
    }
}
