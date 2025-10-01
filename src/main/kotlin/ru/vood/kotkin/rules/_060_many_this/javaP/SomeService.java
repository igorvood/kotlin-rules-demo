package ru.vood.kotkin.rules._060_many_this.javaP;

import org.jetbrains.annotations.NotNull;
import ru.vood.kotkin.rules.ru.vood.kotkin.rules._005_class.Person;

// Сервисный класс
class SomeService {

    // Функция, принимающая Person и Consumer (аналог sideEffect функции)
    public void doSomething(Person person, @NotNull java.util.function.Consumer<Person> sideEffect) {
        System.out.println("person: " + person);
        sideEffect.accept(person); // Вызов переданной функции
    }
}