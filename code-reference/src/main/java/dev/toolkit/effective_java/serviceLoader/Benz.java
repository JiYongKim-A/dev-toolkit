package dev.toolkit.effective_java.serviceLoader;

public class Benz implements Car{
    @Override
    public void horn() {
        System.out.println("Benz horn");
    }
}
