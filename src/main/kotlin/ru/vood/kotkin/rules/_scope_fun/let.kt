package ru.vood.kotkin.rules.ru.vood.kotkin.rules._scope_fun

import ru.vood.kotkin.rules._01_nulluble.Car
import ru.vood.kotkin.rules._01_nulluble.ClientWithNullableField

// мат часть https://www.youtube.com/watch?v=U1N60QTjAeo

val clientNullable = ClientWithNullableField(Car("Пипелац", null))
val clientNotNull = ClientWithNullableField(Car("Пипелац", "Рога и копыта"))


fun `перекраска фаркопа`(client: ClientWithNullableField) {
    client.carInfo?.`производитель фаркопа`?.let { println("Перекрасили фаркоп в красный") }
        ?: println("НЕ Перекрасили фаркоп в красный")
}

fun comprehensiveLetDemo() {
    println("=== ДЕМОНСТРАЦИЯ LET ===")

    // 1. Безопасная работа с nullable
    println("\n1. Безопасная работа с nullable:")
    val nullableValue: String? = "Kotlin"
    nullableValue?.let {
        println("Длина строки '${it}': ${it.length}")
    }

    val hello = "  hello world  "

    // 2. Цепочка преобразований
    println("\n2. Цепочка преобразований:")
    hello
        ?.let { it.trim() }
        ?.let { it.uppercase() }
        ?.let { it.replace("WORLD", "KOTLIN") }
        ?.let { println("Результат: $it") }

    // 3. Ограничение области видимости
    println("\n3. Ограничение области видимости:")
    val result = "data".let { input ->
        val processed = input.uppercase()
        val validated = if (processed.isNotEmpty()) processed else null
        validated?.let { "Результат: $it" } ?: "Ошибка валидации"
    }
    println(result)
}

fun main() {

    `перекраска фаркопа`(clientNullable)
    `перекраска фаркопа`(clientNotNull)

    comprehensiveLetDemo()
}

