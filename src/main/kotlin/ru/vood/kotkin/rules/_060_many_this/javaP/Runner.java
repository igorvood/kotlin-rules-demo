package ru.vood.kotkin.rules._060_many_this.javaP;

import ru.vood.kotkin.rules.ru.vood.kotkin.rules._005_class.Person;

// Класс, который запускает демонстрацию
class Runner {

    // Внутренний класс для демонстрации множественного контекста
    class InnerContext {
        private Runner outerRunner;

        public InnerContext(Runner outer) {
            this.outerRunner = outer;
        }

        // Метод, демонстрирующий множественные контексты
        public void demonstrateMultipleThis(Person person) {
            // this - ссылается на объект InnerContext
            System.out.println("InnerContext this: " + this);

            // outerRunner - ссылается на внешний объект Runner
            System.out.println("Runner context: " + outerRunner);

            // person - переданный объект
            System.out.println("Person: " + person);
        }
    }

    public void run() {
        Person person = new Person("Иван", 30, "Москва");
        SomeService service = new SomeService();

        // Создаем внутренний класс для демонстрации контекстов
        InnerContext inner = new InnerContext(this);

        // Вызов метода с передачей лямбды (аналог sideEffect)
        service.doSomething(person, p -> {
            // В лямбде мы имеем доступ к:

            // 1. p - параметр лямбды (аналог 'it' в Kotlin)
            System.out.println("Lambda parameter (Person): " + p);

            // 2. inner - объект из внешней области видимости
            System.out.println("InnerContext from lambda: " + inner);

            // 3. this - ссылается на объект Runner (в отличие от Kotlin, где это было бы запрещено)
            System.out.println("Runner this from lambda: " + this);

            // 4. Демонстрация множественного контекста через вызов метода
            inner.demonstrateMultipleThis(p);
        });
    }

    // Переопределяем toString для наглядности
    @Override
    public String toString() {
        return "Runner@" + Integer.toHexString(hashCode());
    }
}