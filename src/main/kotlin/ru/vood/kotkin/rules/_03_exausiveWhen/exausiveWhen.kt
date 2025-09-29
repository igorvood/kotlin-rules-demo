package ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen

import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.EEnum.*

// Sealed interface (запечатанный интерфейс) - ограничивает возможные реализации
// Это как enum на стероидах - каждая реализация известна компилятору
sealed interface IResult

// Data class для успешного результата
data class Ok(val value: String) : IResult

// Data class для ошибки
data class Error(val value: String) : IResult

// Обычное перечисление (enum)
enum class EEnum {
    VAL1,
    VAL2,
    VAL3,
}

fun main() {
    // ДЕМО 1: Работа с sealed interface и when выражением

    val ok: IResult = Ok("Успешный результат")

    // WHEN выражение с sealed class/interface - КОМПИЛЯТОР ЗНАЕТ ВСЕ ВОЗМОЖНЫЕ ВАРИАНТЫ!
    val resultMessage = when(ok) {
        is Error -> {
            println("Обрабатываем ошибку: ${ok.value}")
            "Произошла ошибка"
        }
        is Ok -> {
            println("Обрабатываем успех: ${ok.value}")
            "Всё успешно"
        }
        // ❌ НЕ НУЖНО else - компилятор знает все возможные варианты!
        // Компилятор гарантирует, что все случаи покрыты
    }
    println("Результат: $resultMessage")

    // ДЕМО 2: Работа с enum
    val enumValue = EEnum.VAL1

    val enumDescription = when(enumValue) {
        EEnum.VAL1 -> "Первое значение"
        EEnum.VAL2 -> "Второе значение"
        EEnum.VAL3 -> "Третье значение"
        // ❌ Тоже не нужно else - все варианты enum известны
    }
    println("Enum описание: $enumDescription")

    // ДЕМО 3: Практический пример использования
    demonstrateResultHandling()
}

// Практический пример обработки результатов
fun demonstrateResultHandling() {
    println("\n=== ПРАКТИЧЕСКИЕ ПРИМЕРЫ ===")

    // Симуляция разных результатов операций
    val results = listOf(
        Ok("Данные загружены"),
        Error("Сеть недоступна"),
        Ok("Файл сохранён"),
        Error("Недостаточно прав")
    )

    for (result in results) {
        processResult(result)
    }

    // Пример с возвратом значения из when
    val processedResults = results.map { result ->
        when (result) {
            is Ok -> "✅ ${result.value}"
            is Error -> "❌ ${result.value}"
        }
    }
    println("Обработанные результаты: $processedResults")
}

fun processResult(result: IResult) {
    // When как выражение с сохранением результата
    val action = when (result) {
        is Ok -> {
            // В этой ветке компилятор автоматически кастует result к типу Ok
            // Мы имеем доступ к свойству value без дополнительных проверок!
            performAction(result.value)
            "Действие выполнено"
        }
        is Error -> {
            handleError(result.value)
            "Ошибка обработана"
        }
    }
    println("Статус: $action")
}

fun performAction(data: String) {
    println("Выполняем действие с данными: $data")
}

fun handleError(error: String) {
    println("Обрабатываем ошибку: $error")
}

// ДОПОЛНИТЕЛЬНЫЙ ПРИМЕР: Более сложный sealed hierarchy
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

fun handleNetworkResult(result: NetworkResult<String>) {
    when (result) {
        is NetworkResult.Success -> println("Данные: ${result.data}")
        is NetworkResult.Error -> println("Ошибка ${result.code}: ${result.message}")
        NetworkResult.Loading -> println("Загрузка...")
        // Снова не нужно else!
    }
}