package ru.vood.kotkin.rules.ru.vood.kotkin.rules._scope_fun

data class Person(
    var name: String = "",
    var age: Int = 0,
    var city: String = ""
)

fun main() {
    // Без apply - много повторений
    val person1 = Person()
    person1.name = "Анна"
    person1.age = 25
    person1.city = "Москва"

    // С apply - вся инициализация в одном блоке
    val person2 = Person().apply {
        name = "Иван"    // this.name = "Иван"
        age = 30         // this.age = 30
        city = "СПб"     // this.city = "СПб"
        // this ссылается на создаваемый объект
    }

    println(person1)
    println(person2)
}
