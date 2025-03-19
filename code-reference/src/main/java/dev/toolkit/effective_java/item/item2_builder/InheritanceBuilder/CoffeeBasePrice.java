package dev.toolkit.effective_java.item.item2_builder.InheritanceBuilder;

// 커피의 기본 가격을 정의하는 불변(enum) 클래스
public enum CoffeeBasePrice {
    COFFEE(3.0); // 기본 커피 가격

    private double price;

    // 생성자: 기본 가격을 설정 (외부에서 변경 불가)
    CoffeeBasePrice(double price) {
        this.price = price;
    }

    // 기본 가격을 반환하는 메서드 (외부에서 접근만 가능하고 수정 불가)
    public double getPrice() {
        return price;
    }

}
