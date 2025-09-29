package ru.vood.kotkin.rules.`02_collection`

/**
 * ДЕМО: API РАБОТЫ С КОЛЛЕКЦИЯМИ В KOTLIN
 *
 * Kotlin предоставляет богатый набор функций-расширений для работы с коллекциями
 * в функциональном стиле (map, filter, reduce и т.д.)
 */

@JvmInline
value class Department(val value: String)

data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val email: String,
    val department: String,
    val salary: Double,
    val active: Boolean
)

fun main() {
    // Создаем тестовые данные
    val users = listOf(
        User(1, "Алексей", 25, "alex@mail.com", "IT", 50000.0, true),
        User(2, "Мария", 32, "maria@mail.com", "HR", 45000.0, true),
        User(3, "Иван", 28, "ivan@mail.com", "IT", 55000.0, false),
        User(4, "Ольга", 35, "olga@mail.com", "Finance", 60000.0, true),
        User(5, "Петр", 22, "petr@mail.com", "IT", 48000.0, true),
        User(6, "Светлана", 40, "svetlana@mail.com", "HR", 52000.0, true),
        User(7, "Дмитрий", 29, "dmitry@mail.com", "Finance", 58000.0, false)
    )

    println("=== БАЗОВЫЕ ОПЕРАЦИИ ===")

    // ----------------------------------------------------------------------
    // MAP - преобразование элементов
    // ----------------------------------------------------------------------

    // Преобразуем пользователей в список имен
    val names = users.map { it.name }
    println("Все имена: $names")

    // Map с индексами
    val usersWithIndex = users.mapIndexed { index, user ->
        "${index + 1}. ${user.name}"
    }
    println("Пользователи с номерами: $usersWithIndex")

    // ----------------------------------------------------------------------
    // FILTER - фильтрация элементов
    // ----------------------------------------------------------------------

    // Активные пользователи
    val activeUsers = users.filter { it.active }
    println("Активные пользователи: ${activeUsers.size}")

    // Пользователи старше 30 лет
    val usersOver30 = users.filter { it.age > 30 }
    println("Пользователи старше 30: ${usersOver30.map { it.name }}")

    // ----------------------------------------------------------------------
    // FIND / FIRST / LAST - поиск элементов
    // ----------------------------------------------------------------------

    val firstITUser = users.find { it.department == "IT" }
    println("Первый IT пользователь: ${firstITUser?.name}")

    val lastActiveUser = users.last { it.active }  // Бросит исключение если не найден
    println("Последний активный пользователь: ${lastActiveUser.name}")

    // Безопасный поиск с orNull
    val marketingUser = users.firstOrNull { it.department == "Marketing" }
    println("Пользователь из маркетинга: ${marketingUser ?: "Не найден"}")

    println("\n=== АГРЕГИРУЮЩИЕ ОПЕРАЦИИ ===")

    // ----------------------------------------------------------------------
    // REDUCE / FOLD - агрегация элементов
    // ----------------------------------------------------------------------

    // Сумма зарплат (reduce требует хотя бы один элемент)
    val totalSalary = users.reduce { acc, user ->
        acc.copy(salary = acc.salary + user.salary)
    }.salary
    println("Общая зарплата: $totalSalary")

    // Более безопасный вариант с fold (можно указать начальное значение)
    val totalSalarySafe = users.fold(0.0) { acc, user -> acc + user.salary }
    println("Общая зарплата (fold): $totalSalarySafe")

    // ----------------------------------------------------------------------
    // MIN / MAX / AVERAGE / SUM - статистика
    // ----------------------------------------------------------------------

    val maxAge = users.maxOf { it.age }
    val minSalary = users.minOf { it.salary }
    val averageAge = users.map { it.age }.average()

    println("Максимальный возраст: $maxAge")
    println("Минимальная зарплата: $minSalary")
    println("Средний возраст: ${"%.2f".format(averageAge)}")

    println("\n=== ГРУППИРОВКА И СОРТИРОВКА ===")

    // ----------------------------------------------------------------------
    // GROUP BY - группировка элементов
    // ----------------------------------------------------------------------

    val usersByDepartmentBad = users.groupBy { it.department }

    val usersByDepartmentGood = users.groupBy { Department(it.department) }

    println("Пользователи по отделам:")
    usersByDepartmentBad.forEach { (department, deptUsers) ->
        println("  $department: ${deptUsers.map { it.name }}")
    }

    // Группировка с агрегацией
    val avgSalaryByDept = users.groupBy { it.department }
        .mapValues { (_, deptUsers) ->
            deptUsers.map { it.salary }.average()
        }
    println("Средняя зарплата по отделам: $avgSalaryByDept")

    // ----------------------------------------------------------------------
    // SORTED - сортировка
    // ----------------------------------------------------------------------

    val usersByAge = users.sortedBy { it.age }
    println("Пользователи по возрасту: ${usersByAge.map { "${it.name}(${it.age})" }}")

    val usersBySalaryDesc = users.sortedByDescending { it.salary }
    println("Пользователи по зарплате (убывание): ${usersBySalaryDesc.map { "${it.name}(${it.salary})" }}")

    // Сложная сортировка
    val complexSorted = users.sortedWith(
        compareBy<User> { it.department }.thenByDescending { it.salary }
    )
    println("Сортировка по отделу и зарплате:")
    complexSorted.forEach { println("  ${it.department} - ${it.name} - ${it.salary}") }

    println("\n=== ПРОВЕРКИ И УСЛОВИЯ ===")

    // ----------------------------------------------------------------------
    // ANY / ALL / NONE - проверки условий
    // ----------------------------------------------------------------------

    val hasHighSalary = users.any { it.salary > 100000 }
    val allAdults = users.all { it.age >= 18 }
    val noInactive = users.none { !it.active }

    println("Есть ли высокооплачиваемые: $hasHighSalary")
    println("Все совершеннолетние: $allAdults")
    println("Нет неактивных: $noInactive")

    // ----------------------------------------------------------------------
    // COUNT - подсчет по условию
    // ----------------------------------------------------------------------

    val itCount = users.count { it.department == "IT" }
    val youngActiveCount = users.count { it.age < 30 && it.active }

    println("Количество IT специалистов: $itCount")
    println("Молодых активных пользователей: $youngActiveCount")

    println("\n=== РАБОТА С МНОЖЕСТВАМИ ===")

    // ----------------------------------------------------------------------
    // DISTINCT / UNION / INTERSECT - операции с множествами
    // ----------------------------------------------------------------------

    val departments = users.map { it.department }.distinct()
    println("Уникальные отделы: $departments")

    val ages = users.map { it.age }
    val uniqueAges = ages.distinct()
    println("Уникальные возраста: $uniqueAges")

    println("\n=== ЦЕПОЧКИ ВЫЗОВОВ (CHAINING) ===")

    // ----------------------------------------------------------------------
    // КОМБИНАЦИЯ НЕСКОЛЬКИХ ОПЕРАЦИЙ
    // ----------------------------------------------------------------------

    val result = users
        .filter { it.active }                    // Только активные
        .filter { it.department == "IT" }       // Только IT
        .sortedByDescending { it.salary }       // Сортировка по зарплате
        .take(2)                                // Взять топ-2
        .map { "${it.name} - ${it.salary}" }    // Преобразовать в строки

    println("Топ-2 активных IT специалистов: $result")

    // ----------------------------------------------------------------------
    // PARTITION - разделение на две группы
    // ----------------------------------------------------------------------

    val (highSalary, lowSalary) = users.partition { it.salary > 52000 }
    println("Высокооплачиваемые: ${highSalary.map { it.name }}")
    println("Остальные: ${lowSalary.map { it.name }}")

    // ----------------------------------------------------------------------
    // FLATMAP - преобразование и "выравнивание"
    // ----------------------------------------------------------------------

    val allDepartments = users.flatMap { user ->
        listOf(user.department, "${user.department}-Backup")
    }.distinct()
    println("Все отделы с бэкапами: $allDepartments")

    println("\n=== РАБОТА С MAP ===")

    // ----------------------------------------------------------------------
    // ОПЕРАЦИИ С MAP (СЛОВАРЯМИ)
    // ----------------------------------------------------------------------

    val userMap = users.associateBy { it.id }  // Создание Map<User>
    println("Пользователи по ID: ${userMap.keys}")

    val nameMap = users.associate { it.id to it.name }  // Создание Map<Int, String>
    println("Имена по ID: $nameMap")

    // Фильтрация Map
    val highSalaryMap = userMap.filterValues { it.salary > 52000 }
    println("Высокооплачиваемые в Map: ${highSalaryMap.values.map { it.name }}")

    println("\n=== LAZY OPERATIONS (SEQUENCES) ===")

    // ----------------------------------------------------------------------
    // ПОСЛЕДОВАТЕЛЬНОСТИ (SEQUENCES) - ленивые вычисления
    // ----------------------------------------------------------------------

    val lazyResult = users.asSequence()
        .filter {
            println("Фильтруем: ${it.name}")
            it.active
        }
        .map {
            println("Преобразуем: ${it.name}")
            it.name.toList()
        }
        .take(2)  // Обработаются только первые 2 активных пользователя!
        .toList() // Только здесь выполняются вычисления

    println("Ленивый результат: $lazyResult")
}

/**
 * КЛЮЧЕВЫЕ ПРЕИМУЩЕСТВА KOTLIN COLLECTIONS API:
 *
 * 1. **Функциональный стиль** - цепочки вызовов вместо циклов
 * 2. **Null safety** - встроенная защита от NPE
 * 3. **Ленивые вычисления** - sequences для больших данных
 * 4. **Расширяемость** - легко добавлять свои функции-расширения
 * 5. **Читаемость** - код выражает "что сделать", а не "как сделать"
 */

/**
 * ЧАСТО ИСПОЛЬЗУЕМЫЕ ФУНКЦИИ:
 *
 * - Трансформация: map, flatMap, mapNotNull
 * - Фильтрация: filter, filterNot, filterIsInstance
 * - Поиск: find, first, last, firstOrNull
 * - Агрегация: reduce, fold, sumOf, average
 * - Сортировка: sortedBy, sortedWith, reversed
 * - Группировка: groupBy, partition
 * - Проверки: any, all, none, count
 * - Ограничения: take, drop, chunked, windowed
 */

// ДОПОЛНИТЕЛЬНЫЕ ПРИМЕРЫ:

/**
 * Создание своих функций-расширений для коллекций
 */
fun List<User>.getActiveNames(): List<String> =
    this.filter { it.active }
        .map { it.name }

fun List<User>.getTopSalaries(count: Int): List<Double> =
    this.map { it.salary }
        .sortedDescending()
        .take(count)

/**
 * Использование scope функций для более сложной логики
 */
fun processUsers(users: List<User>) {
    users.filter { it.active }
        .groupBy { it.department }
        .mapValues { (_, deptUsers) ->
            deptUsers.map { it.salary }.average()
        }
        .also { stats ->
            println("Статистика по отделам: $stats")
        }
}

/*
Ключевые выводы для демо:
Функциональный подход - цепочки вызовов вместо императивных циклов
Богатый API - десятки полезных функций для разных сценариев
Null safety - встроенная защита от NPE во всех операциях
Ленивые вычисления - sequences для оптимизации производительности
Расширяемость - легко создавать свои функции-расширения
Этот подход делает код более декларативным, читаемым и безопасным!
 */