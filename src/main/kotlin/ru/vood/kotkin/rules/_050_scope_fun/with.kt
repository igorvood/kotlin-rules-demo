package ru.vood.kotkin.rules.ru.vood.kotkin.rules._scope_fun

fun main(){
    val person = Person("", 0, "")

// Многочисленные обращения к person
    person.name = "Анна"
    person.age = 29
    person.city = "Москва"

    println(person)

    // with идеально подходит для инициализации объекта с множеством полей
    with(person) {
        name = "Анна"    // this.name = "Анна"
        age = 29         // this.age = 29
        city = "Москва"  // this.city = "Москва"
    }

    println(person) // Person(name=Анна, age=29, city=Москва)
}