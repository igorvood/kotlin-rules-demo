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

    with("asd"){}
    println(person1)
    println(person2)
}

/*
@kotlin.internal.InlineOnly
public inline fun <T> T.apply(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}
@kotlin.internal.InlineOnly
public inline fun <T, R> with(receiver: T, block: T.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return receiver.block()
}

 */