package com.urise.webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Введите add числовые значения массива через пробел | exit: ");
            String[] params = reader.readLine().trim().split(" ");
            final int length = params.length;
            if (length < 1) {
                System.out.println("Неверная команда.");
                continue;
            }
            switch (params[0]) {
                case "exit":
                    return;
                case "add":
                    try {
                        int[] values = new int[length - 1];
                        List<Integer> integers = new ArrayList<>();
                        IntStream.range(1, length).forEach((int i) -> {
                            values[i - 1] = Integer.parseInt(params[i]);
                            integers.add(Integer.parseInt(params[i]));
                        });
                        System.out.println(minValue(values));
                        System.out.println(oddOrEven(integers));
                    } catch (NumberFormatException e) {
                        System.out.println("Неверное число в массиве, вводите числовые значения через пробел : " + e);
                    }
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static int minValue(int[] values) {
        //System.out.println(Arrays.stream(values).distinct().sorted().mapToObj(String::valueOf).collect(Collectors.joining()));
        final int[] array = Arrays.stream(values).distinct().sorted().toArray();
        final int length = array.length;
        return IntStream.range(0, length).map(i -> (int) (array[i] * Math.pow(10, length - i - 1))).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final Integer reduce = integers.stream().reduce(0, (a, b) -> a + b);
        if (reduce % 2 == 0) {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        }
    }
}
