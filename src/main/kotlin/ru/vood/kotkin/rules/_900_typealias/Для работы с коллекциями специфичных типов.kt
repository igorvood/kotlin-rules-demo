package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// Представьте, что у вас есть класс пользователя
data class User(val id: Long, val name: String, val email: String)

// Typealias для часто используемых коллекций
typealias UserList = List<User>
typealias UserMap = Map<Long, User>
typealias UserById = Map<Long, User>

class UserRepository {
    private val users: UserById = mutableMapOf()

    fun findUsersByIds(ids: List<Long>): UserList {
        return ids.mapNotNull { users[it] }
    }
}