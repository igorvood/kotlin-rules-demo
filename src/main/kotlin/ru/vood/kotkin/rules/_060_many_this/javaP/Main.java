package ru.vood.kotkin.rules._060_many_this.javaP;

import ru.vood.kotkin.rules.ru.vood.kotkin.rules._005_class.Person;

import java.util.function.Consumer;

class Main {

    public void run() {
        Person person = new Person("Иван", 30, "Москва");
        SomeService service = new SomeService();

        // Используем анонимный класс для демонстрации множественного this
        service.doSomething(person, new java.util.function.Consumer<Person>() {
            @Override
            public void accept(Person p) {
                // В анонимном классе мы имеем:

                // 1. p - параметр метода
                System.out.println("Person parameter: " + p);

                // 2. this - ссылается на анонимный класс Consumer
                Consumer<Person> consumer = this;
                System.out.println("Anonymous class this: " + consumer);

                // 3. Runner.this - ссылается на внешний класс Runner
                Main main = Main.this;
                System.out.println("Outer Runner.this: " + main);

                // Демонстрация работы с объектом (аналог with в Kotlin)
                System.out.println("Working with person: " + p.toString());
            }

            @Override
            public String toString() {
                return "AnonymousConsumer@" + Integer.toHexString(hashCode());
            }
        });
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
