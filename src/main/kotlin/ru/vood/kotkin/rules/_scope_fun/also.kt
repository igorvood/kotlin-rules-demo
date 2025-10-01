package ru.vood.kotkin.rules.ru.vood.kotkin.rules._scope_fun


/**
also - используется для выполнения побочных эффектов, логгирование, вализадиция, которые не меняют исходных данных
 */

fun main() {
    data class User(val name: String, val age: Int, val email: String)

    // Валидация при создании объекта
    val user = User("Анна", 25, "anna@example.com")
        .also { user ->
            require(user.name.isNotBlank()) { "Имя не может быть пустым" }
            require(user.age >= 0) { "Возраст не может быть отрицательным" }
            require(user.email.contains("@")) { "Некорректный email" }
            println("Пользователь прошел валидацию: $user")
        }

    // Проверки в цепочках обработки
    val userInput = "42"

    val result = userInput
        .toIntOrNull()
        ?.also { number ->
            check(number > 0) { "Число должно быть положительным" }
            println("Получено число: $number")
        }
        ?.let { it * 2 }
        ?.also { doubled ->
            println("Удвоенное значение: $doubled")
        }

    println("Результат: $result") // 84
}