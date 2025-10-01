package ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_expression

fun main() {
    println("=== ДЕМО TRY ВЫРАЖЕНИЙ В KOTLIN ===\n")

    // ----------------------------------------------------------------------
    // 1. БАЗОВОЕ ИСПОЛЬЗОВАНИЕ TRY-CATCH КАК ВЫРАЖЕНИЯ
    // ----------------------------------------------------------------------
    println("1. БАЗОВОЕ ИСПОЛЬЗОВАНИЕ TRY-CATCH КАК ВЫРАЖЕНИЯ:")

    val result1: String = try {
        "Успешный результат: ${10 / 2}"
    } catch (e: ArithmeticException) {
        "Ошибка вычисления: ${e.message}"
//        throw RuntimeException("Asdsadasd")
//        null
    }
    println("Результат: $result1")
}