package dev.toolkit.effective_java.serviceLoader;

public class BMW implements Car{
    @Override
    public void horn() {
        System.out.println("BMW horn");
    }
}
