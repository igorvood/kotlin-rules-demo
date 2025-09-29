package ru.vood.kotkin.rules._01_nulluble.javaP;


import java.util.Objects;

/**
 * Nullable версия - в Java все ссылочные типы по умолчанию nullable
 * МИНУС: Нет синтаксического различия между nullable и non-null типами
 * МИНУС: в котлин по умолчанию все финальное!!!
 */
public final class ClientIdNullable {
    private final String value;

    public ClientIdNullable(String value) {
        // МИНУС: Можем передать null и это разрешено
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientIdNullable that = (ClientIdNullable) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ClientIdNullable{value='" + value + "'}";
    }
}