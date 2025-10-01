package ru.vood.kotkin.rules.ru.vood.kotkin.rules._005_class

/**
 * DATA CLASS - специальный тип класса в Kotlin для хранения данных
 * Автоматически генерирует:
 * - equals()/hashCode()
 * - toString() в удобном формате
 * - componentN() функции для деструктуризации
 * - copy() функцию для создания копии с измененными полями
 */
data class Person(
    var name: String,
    var age: Int,
    var city: String
)

/**
 * SIMPLE CLASS - обычный класс без модификатора data
 * Не генерирует автоматических методов как data class
 * Подходит для классов с логикой, а не только данными
 */
class SimpleClass()

/**
 * VALUE CLASS (инлайн класс) - класс-обертка для оптимизации производительности
 * На этапе компиляции часто заменяется на wrapped тип (String в данном случае)
 * Позволяет избежать аллокаций памяти при сохранении типобезопасности
 */
@JvmInline
value class PersonId(val name: String)

/**
 * SINGLETON (объект) - гарантирует существование только одного экземпляра
 * Объявление object создает singleton
 * Инициализируется лениво при первом обращении
 */
object Singleton {
    fun doSomething() {
        println("Singleton method called")
    }
}

/**
 * DATA OBJECT - новый тип в Kotlin 1.9+
 * Объединяет преимущества object и data class
 * Автоматически генерирует toString()
 */
data object Singleton1 {
    const val VERSION = "1.0"
}

fun main() {
    println("=== ДЕМОНСТРАЦИЯ DATA CLASS ===")

    // Создание экземпляра data class
    val person1 = Person("Анна", 25, "Москва")
    val person2 = Person("Иван", 30, "Санкт-Петербург")

    // Автоматически сгенерированный toString()
    println("person1: $person1")
    println("person2: $person2")

    // Сравнение объектов (автоматически сгенерированный equals())
    println("person1 == person2: ${person1 == person2}")

    // Копирование с изменением полей
    val person1Copy = person1.copy(age = 26, city = "Казань")
    println("Копия person1 с изменениями: $person1Copy")

    // Деструктуризация (автоматически сгенерированные component функции)
    val (name, age, city) = person1
    println("Деструктуризация: Имя=$name, Возраст=$age, Город=$city")

    println("\n=== ДЕМОНСТРАЦИЯ SIMPLE CLASS ===")

    // Simple class не имеет автоматических методов
    val simple1 = SimpleClass()
    val simple2 = SimpleClass()
    println("simple1: $simple1") // Базовый toString() от Any
    println("simple1 == simple2: ${simple1 == simple2}") // Сравнение по ссылкам

    println("\n=== ДЕМОНСТРАЦИЯ VALUE CLASS ===")

    // Value class выглядит как обычный класс, но оптимизирован
    val id1 = PersonId("user123")
    val id2 = PersonId("user456")
    println("id1: $id1")
    println("id2: $id2")
    println("id1 == id2: ${id1 == id2}")

    // Value class может использоваться как типобезопасная обертка
    fun processUserId(id: PersonId) {
        println("Обрабатываем ID: ${id.name}")
    }
    processUserId(id1)

    println("\n=== ДЕМОНСТРАЦИЯ SINGLETON ===")

    // Object создает singleton - всегда один экземпляр
    Singleton.doSomething()

    // Сравнение ссылок на singleton
    val singletonRef1 = Singleton
    val singletonRef2 = Singleton
    println("singletonRef1 === singletonRef2: ${singletonRef1 === singletonRef2}") // true

    println("\n=== ДЕМОНСТРАЦИЯ DATA OBJECT ===")

    // Data object имеет красивый toString()
    println("Singleton1: $Singleton1")
    println("Версия: ${Singleton1.VERSION}")

    // Сравнение data object
    val dataObjectRef = Singleton1
    println("Singleton1 === dataObjectRef: ${Singleton1 === dataObjectRef}") // true

    println("\n=== ДОПОЛНИТЕЛЬНЫЕ ПРИМЕРЫ DATA CLASS ===")

    // Работа с коллекциями Person
    val people = listOf(
        Person("Мария", 28, "Москва"),
        Person("Алексей", 35, "Новосибирск"),
        Person("Мария", 28, "Москва") // Дубликат для демонстрации
    )

    println("Список людей:")
    people.forEach { println("  - $it") }

    // Поиск уникальных объектов (использует hashCode())
    val uniquePeople = people.toSet()
    println("Уникальные люди: $uniquePeople")

    // Группировка по городу
    val peopleByCity = people.groupBy { it.city }
    println("Люди по городам: $peopleByCity")
}
