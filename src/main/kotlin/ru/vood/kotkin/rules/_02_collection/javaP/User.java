package ru.vood.kotkin.rules._02_collection.javaP;

import java.util.Objects;

class User {
    private final int id;
    private final String name;
    private final int age;
    private final String email;
    private final String department;
    private final double salary;
    private final boolean active;

    public User(int id, String name, int age, String email, String department, double salary, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.active = active;
    }

    // Геттеры (в Kotlin это автоматически генерируется data class)
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public boolean isActive() { return active; }

    // Должны вручную реализовать equals/hashCode/toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age &&
                Double.compare(user.salary, salary) == 0 &&
                active == user.active &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(department, user.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, department, salary, active);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}
