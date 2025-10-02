package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// Сложные сигнатуры функций трудно читать
fun executeOperation1(callback: (List<String>, Int) -> Boolean) { }

// С typealias - понятнее
typealias OperationCallback = (List<String>, Int) -> Boolean
typealias StringList = List<String>

fun executeOperation(callback: OperationCallback) {
    // Более читаемый код
}