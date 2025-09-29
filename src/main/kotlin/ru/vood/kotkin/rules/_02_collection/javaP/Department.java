package ru.vood.kotkin.rules._02_collection.javaP;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ДЕМО: API РАБОТЫ С КОЛЛЕКЦИЯМИ В JAVA
 *
 * Java предоставляет Stream API для работы с коллекциями в функциональном стиле,
 * но код получается более многословным и менее безопасным
 */

// В Java нет value классов, используем обычный класс
class Department {
    private final String value;

    public Department(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Должны реализовать equals/hashCode для корректной работы в коллекциях
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
