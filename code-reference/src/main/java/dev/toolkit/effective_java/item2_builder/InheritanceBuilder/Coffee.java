package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

import java.util.EnumSet;
import java.util.Objects;

public abstract class Coffee {
    public enum BeanType {ARABICA, ROBUSTA, LIBERICA}

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

    private final double price; // 가격을 final로 저장

    final BeanType beanType;
    final Size size;
    final Shots shots;
    final EnumSet<Topping> toppings;


    abstract static class Builder<T extends Builder<T>> {
        private final BeanType beanType;
        private final Size size;
        private final Shots shots;
        private final EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        // ✅ 필수 필드를 생성자에서 강제함
        public Builder(BeanType beanType, Size size, Shots shots) {
            this.beanType = Objects.requireNonNull(beanType, "BeanType is required");
            this.size = Objects.requireNonNull(size, "Size is required");
            this.shots = Objects.requireNonNull(shots, "Shots are required");
        }

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        protected double calculatePrice() {
            double price = CoffeeBasePrice.COFFEE.getPrice(); // ✅ 기본 가격을 Enum에서 가져옴

            price += size.getPrice();
            price += shots.getPrice();

            for (Topping topping : toppings) {
                price += topping.getPrice();
            }

            return price;
        }

        abstract Coffee build();

        protected abstract T self();
    }

    Coffee(Builder<?> builder, double price) {
        this.beanType = builder.beanType;
        this.size = builder.size;
        this.shots = builder.shots;
        this.toppings = builder.toppings.clone();
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    protected void buildString(StringBuilder sb) {
        sb.append("\n-------------------------- 기본 선택 --------------------------\n")
                .append("원두 = ").append(beanType)
                .append(", 사이즈 = ").append(size)
                .append(", 샷 = ").append(shots)
                .append(", 토핑 = ").append(toppings.isEmpty() ? "없음" : toppings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(sb);
        return sb.toString();
    }
}
