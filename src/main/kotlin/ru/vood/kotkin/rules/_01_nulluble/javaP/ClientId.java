package ru.vood.kotkin.rules._01_nulluble.javaP;

import java.util.Objects;

/**
 * В Java нет аналога value class, поэтому приходится создавать обычный класс.
 * МИНУС: Создает overhead в runtime - дополнительный объект в памяти
 * МИНУС: Нет гарантий типобезопасности как в Kotlin
 * МИНУС: в котлин по умолчанию все финальное!!!
 */
public final class ClientId {
    private final String value;

    public ClientId(String value) {
        // В Java нет встроенной защиты от null в конструкторе
        // МИНУС: Можем случайно передать null и получить NPE позже
        this.value = Objects.requireNonNull(value, "ClientId value cannot be null");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return Objects.equals(value, clientId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ClientId{value='" + value + "'}";
    }
}