package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_use

import java.io.File

fun main() {
    println("=== Базовый пример с файлом ===")

    // Файл автоматически закроется после выполнения блока
    File("test.txt").bufferedWriter().use { writer ->
        writer.write("Привет, мир!")
        writer.newLine()
        writer.write("Это демо функции use")
        println("Данные записаны в файл")
    }
    // Файл уже закрыт здесь автоматически

    // Чтение из файла
    File("test.txt").bufferedReader().use { reader ->
        val content = reader.readText()
        println("Прочитано из файла: $content")
    }
    // Файл снова закрыт автоматически
}