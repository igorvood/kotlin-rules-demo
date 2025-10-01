package ru.vood.kotkin.rules._010_nulluble.javaP;


import java.util.Objects;

/**
 * Data class аналог в Java - много шаблонного кода
 * МИНУС: Много boilerplate кода (equals, hashCode, toString)
 * МИНУС: в котлин по умолчанию все финальное!!!
 */
public final class Client {
    private final ClientId id;

    public Client(ClientId id) {
        this.id = Objects.requireNonNull(id, "Client id cannot be null");
    }

    public ClientId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{id=" + id + '}';
    }
}
