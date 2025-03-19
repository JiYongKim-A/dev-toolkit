package dev.toolkit.effective_java.item.item2_builder.InheritanceBuilder;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// 추상 클래스 Coffee: 다양한 커피 종류의 공통 속성을 정의
public abstract class Coffee {
    // 커피 원두 종류 정의
    public enum BeanType {ARABICA, ROBUSTA, LIBERICA}

    // 커피 사이즈별 가격 정보 정의
    public enum Size {
        TALL(0.0), GRANDE(0.5), VENTI(1.0), TRENTA(1.5);
        private final double price;

        Size(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    // 샷 추가 옵션 및 가격 정보
    public enum Shots {
        SINGLE(0.0), DOUBLE(0.5), TRIPLE(1.0);
        private final double price;

        Shots(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    // 토핑 옵션 및 가격 정보
    public enum Topping {
        CINNAMON_POWDER(0.2), CHOCOLATE_POWDER(0.3), WHIPPED_CREAM(0.5);
        private final double price;

        Topping(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    private final double price; // 커피의 최종 가격
    private final BeanType beanType; // 사용 원두 종류
    private final Size size; // 커피의 사이즈
    private final Shots shots; // 추가 샷 개수
    private final EnumSet<Topping> toppings; // 선택한 토핑 목록


    // 빌더 패턴을 위한 정적 중첩 추상 클래스 정의 ( 재귀적 타입 한정 )
    abstract static class Builder<T extends Builder<T>> {
        private final BeanType beanType;
        private final Size size;
        private final Shots shots;
        private final EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        // 생성자를 통해 하위 클래스가 호출 해야하는 필수 필드 강제화
        public Builder(BeanType beanType, Size size, Shots shots) {
            this.beanType = Objects.requireNonNull(beanType, "BeanType is required");
            this.size = Objects.requireNonNull(size, "Size is required");
            this.shots = Objects.requireNonNull(shots, "Shots are required");
        }

        // 토핑 추가 메서드 (반환 타입을 T 타입으로 강제하여 메서드 체이닝 가능)
        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        // 기본 가격 계산 메서드
        protected double calculatePrice() {
            double price = CoffeeBasePrice.COFFEE.getPrice(); // 기본 가격을 Enum에서 가져옴

            price += size.getPrice();
            price += shots.getPrice();

            for (Topping topping : toppings) {
                price += topping.getPrice();
            }
            return price;
        }


        // 구체적인 하위 클래스에서 구현할 빌드 메서드
        public abstract Coffee build();

        // 하위 클래스에서 자신의 타입을 반환하기 위한 추상 메서드
        protected abstract T self();
    }

    // 생성자를 통해 하위 클래스가 호출 해야하는 필수 필드 강제화
    Coffee(Builder<?> builder) {
        this.beanType = builder.beanType;
        this.size = builder.size;
        this.shots = builder.shots;
        this.toppings = builder.toppings.clone();
        this.price = builder.calculatePrice();
    }

    // 커피의 최종 가격 반환
    public double getPrice() {
        return price;
    }

    // 원두 종류 반환
    public BeanType getBeanType() {
        return beanType;
    }

    // 커피 사이즈 반환
    public Size getSize() {
        return size;
    }

    //샷 개수 반환
    public Shots getShots() {
        return shots;
    }

    // 선택된 토핑 목록 반환 ( 불변 컬랙션으로 반환하여 외부 변경 방지 - Collections.unmodifiableSet )
    public Set<Topping> getToppings() {
        return Collections.unmodifiableSet(toppings);
    }

    // 커피 정보를 문자열로 변환하는 메서드 - 추가 메뉴가 있을시 @Override 진행
    protected void buildString(StringBuilder sb) {
        sb.append("\n-------------------------- 기본 선택 --------------------------\n")
                .append("원두 = ").append(beanType)
                .append(", 사이즈 = ").append(size)
                .append(", 샷 = ").append(shots)
                .append(", 토핑 = ").append(toppings.isEmpty() ? "없음" : toppings);
    }
}
