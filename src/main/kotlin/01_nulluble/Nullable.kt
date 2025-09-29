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

    // ----------------------------------------------------------------------
    // ОПЕРАТОР БЕЗОПАСНОГО ВЫЗОВА ?. (SAFE CALL OPERATOR)
    // ----------------------------------------------------------------------

    // ?. - безопасный вызов: если значение null, вернет null вместо выброса исключения
    val safeValue: String? = clientNullable.id?.value  // вернет null, а не упадет

    // ----------------------------------------------------------------------
    // ОПЕРАТОР !! (NOT-NULL ASSERTION) - АНТИПАТТЕРН!
    // ----------------------------------------------------------------------

    // ЭТОТ КОД ОПАСЕН - может выбросить NullPointerException в runtime!
    // val idStrNotNull1: String = clientNullable.id.value  // Не скомпилируется - нужна проверка

    // !! - принудительное утверждение "это значение не может быть null"
    // ЕСЛИ значение null - выбросится NullPointerException
    val idStrNotNull1_1: String = clientNullable.id!!.value!!  // Двойной риск!

    // Почему clientNullable.id!! выбросит NPE:
    // - clientNullable.id = ClientIdNullable(null) - сама обертка не null
    // - НО clientNullable.id.value = null
    // - Поэтому clientNullable.id!!.value!! упадет на втором !!

    // ----------------------------------------------------------------------
    // ЭЛВИС-ОПЕРАТOR ?: (ELVIS OPERATOR) - ПРЕДПОЧТИТЕЛЬНЫЙ СПОСОБ
    // ----------------------------------------------------------------------

    // ?: - предоставляет значение по умолчанию или бросает кастомное исключение
    val idStrNotNull: String = clientNullable.id?.value ?: error("какое-то сообщение об ошибке")
    // Если clientNullable.id?.value = null, выполнится правая часть ?:

    // ----------------------------------------------------------------------
    // ОПАСНЫЙ ПРИМЕР - ПРЯМОЙ ДОСТУП БЕЗ ПРОВЕРОК
    // ----------------------------------------------------------------------

    // ЭТОТ КОД ВЫБРОСИТ NullPointerException В RUNTIME!
    // тут он компилируется т.к. выше уже прогла проверка на нал в 2-х местах
    // val idStrNotNull: String = clientNullable.id?.value ?: error("какое-то сообщение об ошибке")
    // val idStrNotNull1_1: String = clientNullable.id!!.value!!  // Двойной риск!
     val idStrNotNull2: String = clientNullable.id.value
    // Компилятор не пропустит это, так как clientNullable.id nullable

    // Но если бы скомпилировалось, это привело бы к:
    // kotlin.KotlinNullPointerException

    println(idStrNotNull2)  // Эта строка никогда не выполнится из-за NPE выше

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

    // ----------------------------------------------------------------------
    // ПРАКТИЧЕСКАЯ ДЕМОНСТРАЦИЯ РАЗНИЦЫ МЕЖДУ ОПЕРАТОРАМИ
    // ----------------------------------------------------------------------

    val safeExample = clientNullable.id?.value?.length      // Int? = null
    val elvisExample = clientNullable.id?.value?.length ?: 0 // Int = 0
    val assertionExample = clientNullable.id!!.value!!.length // NPE!

    println("Safe call: $safeExample")     // null
    println("Elvis operator: $elvisExample") // 0
    // println("Assertion: $assertionExample") // NPE!
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

/**
 * ПОЧЕМУ !! - АНТИПАТТЕРН:
 *
 * 1. **Уничтожает преимущества null-safety Kotlin**
 *    - Возвращаемся к проблемам Java с NPE
 *    - Теряем безопасность на этапе компиляции
 *
 * 2. **Исключения в runtime вместо ошибок компиляции**
 *    - Ошибки обнаруживаются позже (в production вместо разработки)
 *    - Сложнее отлаживать
 *
 * 3. **Плохая практика проектирования**
 *    - Часто указывает на проблемы в архитектуре
 *    - Лучше явно обработать null случаи
 *
 * 4. **Сложность поддержки**
 *    - Не очевидно, когда может произойти NPE
 *    - Меняется логика - ломаются утверждения !!
 */

/**
 * КОГДА МОЖНО ИСПОЛЬЗОВАТЬ !! (ОЧЕНЬ РЕДКО):
 *
 * 1. В тестах, где вы контролируете данные
 * 2. Когда значение гарантированно не-null по логике приложения
 * 3. Временно во время рефакторинга
 *
 * Но даже в этих случаях лучше использовать:
 * - checkNotNull()
 * - requireNotNull()
 * - Assertions в тестах
 */

// ЛУЧШИЕ АЛЬТЕРНАТИВЫ !!:
fun demonstrateAlternatives(client: ClientNullable) {
    // 1. Safe call с Elvis
    val id1 = client.id?.value ?: "default"

    // 2. checkNotNull() - бросает IllegalStateException с понятным сообщением
    val id2 = checkNotNull(client.id?.value) { "ID должен быть задан для этого сценария" }

    // 3. requireNotNull() - бросает IllegalArgumentException
    // тут можно убрать ?. т.к. выше уже прошла проверка
    val id3 = requireNotNull(client.id?.value) { "Некорректный ID: null" }

    // 4. Явная проверка
    val id4 = if (client.id?.value != null) {
        client.id.value
    } else {
        // Обработка null случая
        "unknown"
    }

    // 5. Extension функции
    val id5 = client.id?.value.orEmpty() // Для String
    val id6 = client.id?.value ?: return // Ранний возврат если null
}