package ru.vood.kotkin.rules._020_collection.javaP;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class JavaCollectionsDemo {
    public static void main(String[] args) {
        // Создаем тестовые данные
        List<User> users = Arrays.asList(
                new User(1, "Алексей", 25, "alex@mail.com", "IT", 50000.0, true),
                new User(2, "Мария", 32, "maria@mail.com", "HR", 45000.0, true),
                new User(3, "Иван", 28, "ivan@mail.com", "IT", 55000.0, false),
                new User(4, "Ольга", 35, "olga@mail.com", "Finance", 60000.0, true),
                new User(5, "Петр", 22, "petr@mail.com", "IT", 48000.0, true),
                new User(6, "Светлана", 40, "svetlana@mail.com", "HR", 52000.0, true),
                new User(7, "Дмитрий", 29, "dmitry@mail.com", "Finance", 58000.0, false)
        );

        System.out.println("=== БАЗОВЫЕ ОПЕРАЦИИ ===");

        // ----------------------------------------------------------------------
        // MAP - преобразование элементов (МНОГОСЛОВНЫЙ КОД)
        // ----------------------------------------------------------------------

        // Преобразуем пользователей в список имен
        List<String> names = users.stream()
                .map(User::getName)
                .toList();
        System.out.println("Все имена: " + names);

        // Map с индексами (СЛОЖНЕЕ ЧЕМ В KOTLIN), добавляется мубельная переменная
        List<String> usersWithIndex = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            usersWithIndex.add((i + 1) + ". " + user.getName());
        }
        System.out.println("Пользователи с номерами: " + usersWithIndex);

        // ----------------------------------------------------------------------
        // FILTER - фильтрация элементов
        // ----------------------------------------------------------------------

        // Активные пользователи
        List<User> activeUsers = users.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
        System.out.println("Активные пользователи: " + activeUsers.size());

        // Пользователи старше 30 лет
        List<User> usersOver30 = users.stream()
                .filter(user -> user.getAge() > 30)
                .collect(Collectors.toList());
        List<String> usersOver30Names = usersOver30.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        System.out.println("Пользователи старше 30: " + usersOver30Names);

        // ----------------------------------------------------------------------
        // FIND / FIRST / LAST - поиск элементов (МЕНЕЕ БЕЗОПАСНО)
        // ----------------------------------------------------------------------

        // В Java Optional помогает, но код многословнее
        Optional<User> firstITUser = users.stream()
                .filter(user -> "IT".equals(user.getDepartment()))
                .findFirst();
        System.out.println("Первый IT пользователь: " +
                firstITUser.map(User::getName).orElse("Не найден"));

        // last требует больше кода - нужно развернуть список или использовать reduce
        User lastActiveUser = users.stream()
                .filter(User::isActive)
                .reduce((first, second) -> second) // Берем последний элемент
                .orElseThrow(() -> new NoSuchElementException("No active users found"));
        System.out.println("Последний активный пользователь: " + lastActiveUser.getName());

        // Безопасный поиск
        Optional<User> marketingUser = users.stream()
                .filter(user -> "Marketing".equals(user.getDepartment()))
                .findFirst();
        System.out.println("Пользователь из маркетинга: " +
                marketingUser.map(User::getName).orElse("Не найден"));

        System.out.println("\n=== АГРЕГИРУЮЩИЕ ОПЕРАЦИИ ===");

        // ----------------------------------------------------------------------
        // REDUCE / FOLD - агрегация элементов
        // ----------------------------------------------------------------------

        // Сумма зарплат (reduce требует обработки Optional)
        double totalSalary = users.stream()
                .mapToDouble(User::getSalary)
                .reduce(0.0, Double::sum);
        System.out.println("Общая зарплата: " + totalSalary);

        // Альтернатива с summingDouble
        double totalSalaryAlt = users.stream()
                .collect(Collectors.summingDouble(User::getSalary));
        System.out.println("Общая зарплата (summingDouble): " + totalSalaryAlt);

        // ----------------------------------------------------------------------
        // MIN / MAX / AVERAGE / SUM - статистика
        // ----------------------------------------------------------------------

        int maxAge = users.stream()
                .mapToInt(User::getAge)
                .max()
                .orElse(0);
        double minSalary = users.stream()
                .mapToDouble(User::getSalary)
                .min()
                .orElse(0.0);
        double averageAge = users.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(0.0);

        System.out.println("Максимальный возраст: " + maxAge);
        System.out.println("Минимальная зарплата: " + minSalary);
        System.out.println("Средний возраст: " + String.format("%.2f", averageAge));

        System.out.println("\n=== ГРУППИРОВКА И СОРТИРОВКА ===");

        // ----------------------------------------------------------------------
        // GROUP BY - группировка элементов
        // ----------------------------------------------------------------------

        Map<String, List<User>> usersByDepartmentBad = users.stream()
                .collect(Collectors.groupingBy(User::getDepartment));

        // Для value class нужно использовать правильный ключ
        Map<Department, List<User>> usersByDepartmentGood = users.stream()
                .collect(Collectors.groupingBy(user -> new Department(user.getDepartment())));

        System.out.println("Пользователи по отделам:");
        usersByDepartmentBad.forEach((department, deptUsers) -> {
            List<String> deptUserNames = deptUsers.stream()
                    .map(User::getName)
                    .collect(Collectors.toList());
            System.out.println("  " + department + ": " + deptUserNames);
        });

        // Группировка с агрегацией
        Map<String, Double> avgSalaryByDept = users.stream()
                .collect(Collectors.groupingBy(
                        User::getDepartment,
                        Collectors.averagingDouble(User::getSalary)
                ));
        System.out.println("Средняя зарплата по отделам: " + avgSalaryByDept);

        // ----------------------------------------------------------------------
        // SORTED - сортировка
        // ----------------------------------------------------------------------

        List<User> usersByAge = users.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .collect(Collectors.toList());
        List<String> usersByAgeNames = usersByAge.stream()
                .map(user -> user.getName() + "(" + user.getAge() + ")")
                .collect(Collectors.toList());
        System.out.println("Пользователи по возрасту: " + usersByAgeNames);

        List<User> usersBySalaryDesc = users.stream()
                .sorted(Comparator.comparingDouble(User::getSalary).reversed())
                .collect(Collectors.toList());
        List<String> usersBySalaryDescNames = usersBySalaryDesc.stream()
                .map(user -> user.getName() + "(" + user.getSalary() + ")")
                .collect(Collectors.toList());
        System.out.println("Пользователи по зарплате (убывание): " + usersBySalaryDescNames);

        // Сложная сортировка
        List<User> complexSorted = users.stream()
                .sorted(Comparator
                        .comparing(User::getDepartment)
                        .thenComparing(Comparator.comparingDouble(User::getSalary).reversed()))
                .collect(Collectors.toList());
        System.out.println("Сортировка по отделу и зарплате:");
        complexSorted.forEach(user ->
                System.out.println("  " + user.getDepartment() + " - " + user.getName() + " - " + user.getSalary()));

        System.out.println("\n=== ПРОВЕРКИ И УСЛОВИЯ ===");

        // ----------------------------------------------------------------------
        // ANY / ALL / NONE - проверки условий
        // ----------------------------------------------------------------------

        boolean hasHighSalary = users.stream().anyMatch(user -> user.getSalary() > 100000);
        boolean allAdults = users.stream().allMatch(user -> user.getAge() >= 18);
        boolean noInactive = users.stream().noneMatch(user -> !user.isActive());

        System.out.println("Есть ли высокооплачиваемые: " + hasHighSalary);
        System.out.println("Все совершеннолетние: " + allAdults);
        System.out.println("Нет неактивных: " + noInactive);

        // ----------------------------------------------------------------------
        // COUNT - подсчет по условию
        // ----------------------------------------------------------------------

        long itCount = users.stream().filter(user -> "IT".equals(user.getDepartment())).count();
        long youngActiveCount = users.stream().filter(user -> user.getAge() < 30 && user.isActive()).count();

        System.out.println("Количество IT специалистов: " + itCount);
        System.out.println("Молодых активных пользователей: " + youngActiveCount);

        System.out.println("\n=== РАБОТА С МНОЖЕСТВАМИ ===");

        // ----------------------------------------------------------------------
        // DISTINCT / UNION / INTERSECT - операции с множествами
        // ----------------------------------------------------------------------

        List<String> departments = users.stream()
                .map(User::getDepartment)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Уникальные отделы: " + departments);

        List<Integer> ages = users.stream()
                .map(User::getAge)
                .collect(Collectors.toList());
        List<Integer> uniqueAges = ages.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Уникальные возраста: " + uniqueAges);

        System.out.println("\n=== ЦЕПОЧКИ ВЫЗОВОВ (CHAINING) ===");

        // ----------------------------------------------------------------------
        // КОМБИНАЦИЯ НЕСКОЛЬКИХ ОПЕРАЦИЙ
        // ----------------------------------------------------------------------

        List<String> result = users.stream()
                .filter(User::isActive)                    // Только активные
                .filter(user -> "IT".equals(user.getDepartment()))       // Только IT
                .sorted(Comparator.comparingDouble(User::getSalary).reversed())       // Сортировка по зарплате
                .limit(2)                                // Взять топ-2
                .map(user -> user.getName() + " - " + user.getSalary())    // Преобразовать в строки
                .collect(Collectors.toList());

        System.out.println("Топ-2 активных IT специалистов: " + result);

        // ----------------------------------------------------------------------
        // PARTITION - разделение на две группы
        // ----------------------------------------------------------------------

        Map<Boolean, List<User>> partitioned = users.stream()
                .collect(Collectors.partitioningBy(user -> user.getSalary() > 52000));
        List<User> highSalary = partitioned.get(true);
        List<User> lowSalary = partitioned.get(false);

        List<String> highSalaryNames = highSalary.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        List<String> lowSalaryNames = lowSalary.stream()
                .map(User::getName)
                .collect(Collectors.toList());

        System.out.println("Высокооплачиваемые: " + highSalaryNames);
        System.out.println("Остальные: " + lowSalaryNames);

        // ----------------------------------------------------------------------
        // FLATMAP - преобразование и "выравнивание"
        // ----------------------------------------------------------------------

        List<String> allDepartments = users.stream()
                .flatMap(user -> Arrays.asList(user.getDepartment(), user.getDepartment() + "-Backup").stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Все отделы с бэкапами: " + allDepartments);

        System.out.println("\n=== РАБОТА С MAP ===");

        // ----------------------------------------------------------------------
        // ОПЕРАЦИИ С MAP (СЛОВАРЯМИ)
        // ----------------------------------------------------------------------

        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        System.out.println("Пользователи по ID: " + userMap.keySet());

        Map<Integer, String> nameMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::getName));
        System.out.println("Имена по ID: " + nameMap);

        // Фильтрация Map
        Map<Integer, User> highSalaryMap = userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getSalary() > 52000)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<String> highSalaryMapNames = highSalaryMap.values().stream()
                .map(User::getName)
                .collect(Collectors.toList());
        System.out.println("Высокооплачиваемые в Map: " + highSalaryMapNames);

        System.out.println("\n=== LAZY OPERATIONS (STREAMS) ===");

        // ----------------------------------------------------------------------
        // ПОТОКИ (STREAMS) - ленивые вычисления
        // ----------------------------------------------------------------------

        List<List<Character>> lazyResult = users.stream()
                .filter(user -> {
                    System.out.println("Фильтруем: " + user.getName());
                    return user.isActive();
                })
                .map(user -> {
                    System.out.println("Преобразуем: " + user.getName());
                    return user.getName().chars()
                            .mapToObj(c -> (char) c)
                            .collect(Collectors.toList());
                })
                .limit(2)  // Обработаются только первые 2 активных пользователя!
                .collect(Collectors.toList());

        System.out.println("Ленивый результат: " + lazyResult);
    }

    // ----------------------------------------------------------------------
    // ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ - АНАЛОГИ ФУНКЦИЙ-РАСШИРЕНИЙ KOTLIN
    // ----------------------------------------------------------------------

    /**
     * Создание своих утилитных методов для коллекций (МНОГОСЛОВНО)
     */
    public static List<String> getActiveNames(@NotNull List<User> users) {
        return users.stream()
                .filter(User::isActive)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public static List<Double> getTopSalaries(@NotNull List<User> users, int count) {
        return users.stream()
                .map(User::getSalary)
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Использование методов для более сложной логики
     */
    public static void processUsers(@NotNull List<User> users) {
        Map<String, Double> stats = users.stream()
                .filter(User::isActive)
                .collect(Collectors.groupingBy(
                        User::getDepartment,
                        Collectors.averagingDouble(User::getSalary)
                ));
        System.out.println("Статистика по отделам: " + stats);
    }
}

/*
Ключевые отличия и проблемы Java кода:
1. Многословность
Kotlin: users.map { it.name }

Java: users.stream().map(User::getName).collect(Collectors.toList())

2. Null безопасность
Kotlin: Встроенная null safety, firstOrNull возвращает nullable тип

Java: Нужно использовать Optional, который добавляет дополнительную обертку

3. Data классы vs POJO
Kotlin: data class автоматически генерирует equals/hashCode/toString/copy

Java: Нужно вручную реализовывать все методы (или использовать Lombok)

4. Value классы
Kotlin: value class для типобезопасности без аллокаций

Java: Нужно создавать полноценный класс с equals/hashCode

5. Обработка исключений
Kotlin: last бросает понятное исключение

Java: Нужно явно обрабатывать NoSuchElementException

6. Цепочки вызовов
Kotlin: Более лаконичные цепочки, особенно для преобразований

Java: Часто требуются промежуточные коллекции

7. Декларативность
Kotlin: Код более выразительный и читаемый

Java: Больше шаблонного кода, меньше выразительности

Преимущества Java:
Более зрелая экосистема

Лучшая производительность в некоторых сценариях

Более предсказуемое поведение памяти

Вывод: Kotlin предоставляет более лаконичный, безопасный и выразительный API для работы с коллекциями, сокращая объем кода на 30-50% и уменьшая вероятность ошибок.
*/