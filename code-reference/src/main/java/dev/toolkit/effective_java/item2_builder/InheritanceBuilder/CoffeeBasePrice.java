package dev.toolkit.effective_java.item2_builder.InheritanceBuilder;

public enum CoffeeBasePrice {
    COFFEE(3.0); // 기본 커피 가격

    private double price;

    CoffeeBasePrice(double price) { this.price = price; }

    public double getPrice() { return price; }

}
