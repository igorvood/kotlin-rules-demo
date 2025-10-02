package ru.vood.kotkin.rules.ru.vood.kotkin.rules._500_mockk

import io.mockk.*
import org.junit.jupiter.api.Test

class CaptureTest {

    interface MessageSender {
        fun send(message: String, priority: Int)
    }

    @Test
    fun `capture arguments example`() {
        val sender = mockk<MessageSender>()
        val messages = mutableListOf<String>()
        val priorities = mutableListOf<Int>()

        // Захватываем аргументы
        every { sender.send(capture(messages), capture(priorities)) } just Runs

        // Вызываем методы
        sender.send("Hello", 1)
        sender.send("World", 2)
        sender.send("Test", 3)

        // Проверяем захваченные значения
        assert(messages == listOf("Hello", "World", "Test"))
        assert(priorities == listOf(1, 2, 3))
    }
}