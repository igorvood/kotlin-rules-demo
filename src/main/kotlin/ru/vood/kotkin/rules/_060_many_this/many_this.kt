package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_many_this

import ru.vood.kotkin.rules.ru.vood.kotkin.rules._005_class.Person

// Сервисный класс, который выполняет какую-то операцию
class SomeService {

    // Функция, принимающая объект Person и функцию sideEffect в качестве параметра
    // sideEffect - это функция, которая принимает Person и не возвращает значения (Unit)
    fun doSomething(person: Person, sideEffect: (Person) -> Unit) {
        println("person: $person")
        sideEffect(person) // Вызов переданной функции с объектом person
    }
}

// Класс, который запускает демонстрацию
class Runner() {

    fun run() {
        // Создание объекта Person
        val person = Person("Иван", 30, "Москва")
        val service = SomeService()

        // Вызов метода doSomething с передачей лямбда-выражения в качестве sideEffect
        service.doSomething(person) {
            // 'it' - это неявное имя параметра в лямбде, ссылается на переданный объект Person

            // Блок with - позволяет работать с объектом без повторного указания его имени
            with(it) {
                // this@with - ссылается на объект, переданный в with (в данном случае it - Person)
                println("""with -> ${this@with}""")

                // this@Runner - явное указание на внешний контекст (объект Runner)
                println("""Runner -> ${this@Runner}""")
            }
        }
    }
}

// Точка входа в программу
fun main() {
    val runner = Runner()
    runner.run() // Запуск демонстрации
}