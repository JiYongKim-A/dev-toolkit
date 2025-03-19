package dev.toolkit.effective_java.item.item1_static_factory_method;

/**
 * [ 복잡한 객체 생성을 단순화 ] - (Builder 패턴과 결합)
 * 정적 펙터리 메서드를 활용하여
 * new Car("Hyundai", "Sonata", 2023) 와 같이
 * 매개변수가 많은 생성자 호출을 방지하고 가독성을 향상 시킬 수 있다.
 */
public class Car {
    private String brand;
    private String model;
    private int year;

    //private를 통해 외부 사용 제한,
    private Car(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String brand;
        private String model;
        private int year;

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}

class CarMain {
    public static void main(String[] args) {
        Car car = Car.builder()
                .brand("Hyundai")
                .model("sonata")
                .year(2025)
                .build();
        System.out.println(car);
    }
}