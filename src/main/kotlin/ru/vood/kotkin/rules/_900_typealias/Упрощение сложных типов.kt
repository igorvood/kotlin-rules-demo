package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// Без typealias - сложно читать
fun processUserData1(data: Map<String, List<Pair<Int, String>>>) { }

// С typealias - понятнее
typealias UserData = Map<String, List<Pair<Int, String>>>
typealias UserPreferences = List<Pair<Int, String>>

fun processUserData(data: UserData) {
    // Код становится более читаемым
}