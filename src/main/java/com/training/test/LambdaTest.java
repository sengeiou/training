package com.training.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest {

    public static void main(String[] args) {
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        cost.stream().map(x -> x + x * 0.05).forEach(x -> System.out.println(x));
        cost = cost.stream().map(x -> x + x * 0.05).collect(Collectors.toList());
        cost.forEach(x -> System.out.println(x));

        double allCost = cost.stream().map(x -> x + x * 0.05).reduce((sum, x) -> sum + x).get();
        System.out.println(allCost);

    }

}
