package ru.vood.kotkin.rules._01_nulluble.javaP;

import java.util.Objects;

public final class ClientNullable {
    private final ClientIdNullable id;

    public ClientNullable(ClientIdNullable id) {
        // МИНУС: Можем передать null и это разрешено
        this.id = id;
    }

    public ClientIdNullable getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientNullable that = (ClientNullable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClientNullable{id=" + id + '}';
    }
}
