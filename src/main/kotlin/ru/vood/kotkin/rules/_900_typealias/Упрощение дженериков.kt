package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// Сложные дженерики
class Repository<T : List<Map<String, Any>>> { }

// Упрощенная версия
typealias ComplexData<T> = List<Map<String, T>>
class SimplifiedRepository<T : Any> {
    fun processData(data: ComplexData<T>) { }
}