package ru.vood.kotkin.rules._03_expression.javaP;

public class Try {
    public static void main(String[] args) {
        System.out.println("=== ДЕМО TRY ВЫРАЖЕНИЙ В JAVA ===\n");

        // ----------------------------------------------------------------------
        // 1. БАЗОВОЕ ИСПОЛЬЗОВАНИЕ TRY-CATCH КАК ВЫРАЖЕНИЯ
        // ----------------------------------------------------------------------
        System.out.println("1. БАЗОВОЕ ИСПОЛЬЗОВАНИЕ TRY-CATCH КАК ВЫРАЖЕНИЯ:");

//        Огромный минус: мутабельная переменная
        String result1;
        try {
            result1 = "Успешный результат: " + (10 / 2);
        } catch (ArithmeticException e) {
            result1 = "Ошибка вычисления: " + e.getMessage();
            // throw new RuntimeException("Asdsadasd");
            // result1 = null;
        }
        System.out.println("Результат: " + result1);
    }
}
