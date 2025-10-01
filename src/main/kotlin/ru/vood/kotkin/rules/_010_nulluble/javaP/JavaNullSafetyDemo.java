package ru.vood.kotkin.rules._010_nulluble.javaP;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaNullSafetyDemo {

    public static void main(String[] args) {
        // ----------------------------------------------------------------------
        // СОЗДАНИЕ ОБЪЕКТОВ
        // ----------------------------------------------------------------------

        // Non-null версия - безопасно благодаря проверке в конструкторе
        Client client = new Client(new ClientId("123"));

        // Nullable версия - можем передать null везде
        ClientNullable clientNullable = new ClientNullable(new ClientIdNullable(null));

        // ----------------------------------------------------------------------
        // РАБОТА С NON-NULL ВЕРСИЕЙ - ОТНОСИТЕЛЬНО БЕЗОПАСНО
        // ----------------------------------------------------------------------

        String idStr = client.getId().getValue(); // Безопасно - проверки в конструкторе

        // ----------------------------------------------------------------------
        // РАБОТА С NULLABLE ВЕРСИЕЙ - ОПАСНОСТЬ NPE!
        // ----------------------------------------------------------------------

        // ПРЯМОЙ ДОСТУП - ОПАСНО!
        try {
            // МИНУС: Компилятор не предупреждает об опасности NPE
            String dangerous = clientNullable.getId().getValue();
            System.out.println("Это не выполнится из-за NPE выше: " + dangerous);
        } catch (NullPointerException e) {
            System.out.println("Поймали NPE: " + e.getMessage());
        }

        // ----------------------------------------------------------------------
        // РУЧНЫЕ ПРОВЕРКИ (АНАЛОГ ?. В KOTLIN)
        // ----------------------------------------------------------------------

        // Безопасный доступ - приходится делать вручную
        String safeValue = null;
        if (clientNullable.getId() != null) {
            safeValue = clientNullable.getId().getValue();
        }

        // Более компактная версия (аналог safe call)
        String safeValue2 = (clientNullable.getId() != null) ? clientNullable.getId().getValue() : null;

        // ----------------------------------------------------------------------
        // ЭЛВИС-ОПЕРАТОР ?: В JAVA - ТЕРНАРНЫЙ ОПЕРАТОР
        // ----------------------------------------------------------------------

        String idStrNotNull = (clientNullable.getId() != null && clientNullable.getId().getValue() != null)
                ? clientNullable.getId().getValue()
                : "default-value";

        // Или с кастомным исключением:
        String idStrWithError = (clientNullable.getId() != null && clientNullable.getId().getValue() != null) ? (clientNullable.getId().getValue()) : "throw new RuntimeException(id is null)";

        // ----------------------------------------------------------------------
        // ОПЕРАТОР !! В JAVA - ПРЯМОЙ ДОСТУП БЕЗ ПРОВЕРОК
        // ----------------------------------------------------------------------

        // В Java это поведение по умолчанию - просто обращаемся к полям/методам
        try {
            // МИНУС: NPE произойдет в runtime без предупреждения компилятора
            String assertionExample = clientNullable.getId().getValue().toUpperCase();
        } catch (NullPointerException e) {
            System.out.println("Еще один NPE: " + e.getMessage());
        }

        // ----------------------------------------------------------------------
        // ПРАКТИЧЕСКАЯ ДЕМОНСТРАЦИЯ РАЗНИЦ
        // ----------------------------------------------------------------------

        // Safe call аналог
        Integer safeExample = (clientNullable.getId() != null && clientNullable.getId().getValue() != null)
                ? clientNullable.getId().getValue().length()
                : null;

        // Elvis operator аналог
        int elvisExample = (clientNullable.getId() != null && clientNullable.getId().getValue() != null)
                ? clientNullable.getId().getValue().length()
                : 0;

        // Assertion аналог - просто прямой доступ
        try {
            int assertionExample = clientNullable.getId().getValue().length();
            System.out.println("Assertion: " + assertionExample);
        } catch (NullPointerException e) {
            System.out.println("NPE при assertion доступе");
        }

        System.out.println("Safe call: " + safeExample);     // null
        System.out.println("Elvis operator: " + elvisExample); // 0

        // ----------------------------------------------------------------------
        // ДЕМОНСТРАЦИЯ ПРОБЛЕМ ТИПОБЕЗОПАСНОСТИ В JAVA
        // ----------------------------------------------------------------------

        processClientId(new ClientId("123"));
        processStringId("123"); // МИНУС: Компилятор разрешает - нет типобезопасности!

        // МИНУС: Можем случайно передать строку вместо ClientId
        // processClientId("123"); // Ошибка компиляции - это хорошо

        // Но можем сделать так (обход типобезопасности):
        String stringId = "123";
        // processStringId(stringId); // Разрешено - нет защиты от смешивания типов

        System.out.println("Non-null client ID: " + client.getId().getValue());
        System.out.println("Nullable client ID: " +
                ((clientNullable.getId() != null && clientNullable.getId().getValue() != null)
                        ? clientNullable.getId().getValue()
                        : "null"));
    }

    public static void processClientId(@NotNull ClientId id) {
        System.out.println("Processing ClientId: " + id.getValue());
    }

    public static void processStringId(String id) {
        System.out.println("Processing String ID: " + id);
    }

    // ----------------------------------------------------------------------
    // АЛЬТЕРНАТИВЫ ПРЯМОМУ ДОСТУПУ (АНАЛОГИ KOTLIN)
    // ----------------------------------------------------------------------

    public static void demonstrateAlternatives(@NotNull ClientNullable client) {
        // 1. Ручные проверки (аналог safe call + elvis)
        String id1 = (client.getId() != null && client.getId().getValue() != null)
                ? client.getId().getValue()
                : "default";

        // 2. checkNotNull аналог
        String id2;
        if (client.getId() == null || client.getId().getValue() == null) {
            throw new IllegalStateException("ID должен быть задан для этого сценария");
        }
        id2 = client.getId().getValue();

        // 3. requireNotNull аналог
        String id3;
        if (client.getId() == null || client.getId().getValue() == null) {
            throw new IllegalArgumentException("Некорректный ID: null");
        }
        id3 = client.getId().getValue();

        // 4. Явная проверка
        String id4;
        if (client.getId() != null && client.getId().getValue() != null) {
            id4 = client.getId().getValue();
        } else {
            id4 = "unknown";
        }

        // 5. Utility методы (аналог extension functions)
        String id5 = getValueOrEmpty(client);
        String id6 = getValueOrReturn(client);
    }

    // Утилитные методы для работы с nullable
    private static String getValueOrEmpty(@NotNull ClientNullable client) {
        return (client.getId() != null && client.getId().getValue() != null)
                ? client.getId().getValue()
                : "";
    }

    @Nullable
    private static String getValueOrReturn(@NotNull ClientNullable client) {
        if (client.getId() == null || client.getId().getValue() == null) {
            return null; // или бросить исключение
        }
        return client.getId().getValue();
    }
}