package ru.vood.kotkin.rules._030_expression.javaP;

import org.jetbrains.annotations.NotNull;
import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.EEnum;
import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.IResult;
import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.Ok;
import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.Error;

import java.util.Arrays;
import java.util.List;

import static ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.ExhaustiveWhenKt.handleError;
import static ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.ExhaustiveWhenKt.performAction;

public class ExhaustiveWhen {


    public static void main(String[] args) {
        // ДЕМО 1: Работа с sealed interface и switch expressions
        // ✅ В JAVA 21 ЕСТЬ SEALED INTERFACES - ТАК ЖЕ БЕЗОПАСНО КАК В KOTLIN!

        IResult ok = new Ok("Успешный результат");

        // Switch expression с pattern matching (Java 21)
        String resultMessage = switch (ok) {
            case Error error -> {
                System.out.println("Обрабатываем ошибку: " + error.getValue());
                yield "Произошла ошибка";
            }
            case Ok okResult -> {
                System.out.println("Обрабатываем успех: " + okResult.getValue());
                yield "Всё успешно";
            }
//            case null -> throw new IllegalArgumentException("Неверный тип результата");
            // ❌ НЕ НУЖНО default - компилятор знает все возможные варианты!
            // Компилятор гарантирует, что все случаи покрыты (exhaustive)
        };
        System.out.println("Результат: " + resultMessage);

        // ДЕМО 2: Работа с enum
        EEnum enumValue = EEnum.VAL1;

        // Switch expression для enum
        String enumDescription = switch (enumValue) {
            case VAL1 -> "Первое значение";
            case VAL2 -> "Второе значение";
            case VAL3 -> "Третье значение";
            // ✅ НЕ НУЖНО default - все варианты enum известны
        };
        System.out.println("Enum описание: " + enumDescription);

        // ДЕМО 3: Практический пример использования
        demonstrateResultHandling();
    }

    // Практический пример обработки результатов
    public static void demonstrateResultHandling() {
        System.out.println("\n=== ПРАКТИЧЕСКИЕ ПРИМЕРЫ ===");

        // Симуляция разных результатов операций
        List<IResult> results = Arrays.asList(
                new Ok("Данные загружены"),
                new Error("Сеть недоступна"),
                new Ok("Файл сохранён"),
                new Error("Недостаточно прав")
        );

        for (IResult result : results) {
            processResult(result);
        }

        // Пример с преобразованием - современный Stream API
        List<String> processedResults = results.stream()
                .map(result -> switch (result) {
                    case Ok ok -> "✅ " + ok.getValue();
                    case Error error -> "❌ " + error.getValue();
                })
                .toList();

        System.out.println("Обработанные результаты: " + processedResults);
    }

    public static void processResult(@NotNull IResult result) {
        // Switch expression с pattern matching
        String action = switch (result) {
            case Ok ok -> {
                performAction(ok.getValue());
                yield "Действие выполнено";
            }
            case Error error -> {
                handleError(error.getValue());
                yield "Ошибка обработана";
            }
        };
        System.out.println("Статус: " + action);
    }
}
