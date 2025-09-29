package ru.vood.kotkin.rules.`01_nulluble`

/**
 * JvmInline value класс - это специальный тип класса в Kotlin, который оборачивает значение
 * без создания дополнительного объекта в runtime (после компиляции).
 *
 * Основные особенности:
 * - Имеет только одно свойство (value)
 * - Устраняет overhead обертки при компиляции
 * - Обеспечивает типобезопасность на этапе компиляции
 * - Не может быть null
 */
@JvmInline
value class ClientId(
    val value: String  // Только одно свойство, не может быть null
)

/**
 * Data class для представления клиента.
 * Использует ClientId как типобезопасный идентификатор вместо простого String.
 */
data class Client(
    val id: ClientId,  // Используем value class как типобезопасный ID
)

/**
 * Value класс с nullable значением.
 * Обратите внимание: сам ClientIdNullable не может быть null, но его значение может быть null.
 */
@JvmInline
value class ClientIdNullable(
    val value: String?  // Значение может быть null, но сама обертка - нет
)

/**
 * Data class с nullable value class.
 * Здесь уже сама обертка ClientIdNullable может быть null.
 */
data class ClientNullable(
    val id: ClientIdNullable?,  // И сама обертка, и её значение могут быть null
)

fun main() {
    // ЭТОТ КОД НЕ СКОМПИЛИРУЕТСЯ - хорошая демонстрация типобезопасности!
    // val client1 = Client(ClientId(null))
    // Ошибка: ClientId требует non-null String, поэтому мы защищены от null на этапе компиляции

    // Создаем клиента с гарантированно не-null ID
    val client = Client(ClientId("sad"))
    // Создаем клиента с nullable ID (и обертка, и значение могут быть null)
    val clientNullable = ClientNullable(ClientIdNullable(null))

    // Работа с non-null версией - простая и безопасная
    val idStr: String = client.id.value  // Не нужно проверять на null

    // Работа с nullable версией - требует проверок
    val idStrNullable: String? = clientNullable.id?.value  // Проверяем и обертку, и значение

//    val idStrNotNull1: String = clientNullable.id.value
    val idStrNotNull1_1: String = clientNullable.id!!.value!!


    // Принудительное извлечение значения с кастомной ошибкой
    val idStrNotNull: String = clientNullable.id?.value ?: error("какое-то сообщение об ошибке")

    // Принудительное извлечение значения с кастомной ошибкой
    val idStrNotNull2: String = clientNullable.id.value

    println(idStrNotNull2)

    // ДЕМОНСТРАЦИЯ ПРЕИМУЩЕСТВ VALUE CLASS:

    // 1. Типобезопасность
    fun processClientId(id: ClientId) { /* логика */ }
    fun processStringId(id: String) { /* логика */ }

    // processClientId("string") // Ошибка компиляции - нужен именно ClientId
    processClientId(ClientId("valid-id")) // Корректно

    // 2. Нет overhead в runtime
    val stringId = "123"
    val clientId = ClientId("123")

    // После компиляции clientId будет представлен как String, без дополнительного объекта

    println("Non-null client ID: ${client.id.value}")
    println("Nullable client ID: ${clientNullable.id?.value ?: "null"}")
}

/**
 * КОГДА ИСПОЛЬЗОВАТЬ VALUE CLASS:
 *
 * 1. Для типобезопасности - когда нужно различать разные типы идентификаторов
 *    (UserId, OrderId, ProductId - все String, но не должны смешиваться)
 *
 * 2. Для обертки примитивных типов без потери производительности
 *
 * 3. Когда нужна семантика определенного типа, но без создания отдельного класса
 */

// Дополнительные примеры использования:
@JvmInline
value class Email(val value: String) {
    // Можно добавлять методы и свойства (только computed)
    val isValid: Boolean
        get() = value.contains("@")

    fun getDomain(): String = value.substringAfter("@")
}

@JvmInline
value class UserId(val value: Long) {
    // Компаньон объекты также поддерживаются
    companion object {
        fun fromString(str: String): UserId = UserId(str.toLong())
    }
}

/*
Ключевые выводы для демо:
Value классы устраняют overhead обертки - в runtime используется прямое значение
Обеспечивают типобезопасность - нельзя случайно передать String вместо ClientId
Защита от null - non-null версия гарантирует наличие значения
Могут содержать методы - добавляют функциональность без потери производительности
Это отличный инструмент для Domain-Driven Design, где важна семантика типов!
 */