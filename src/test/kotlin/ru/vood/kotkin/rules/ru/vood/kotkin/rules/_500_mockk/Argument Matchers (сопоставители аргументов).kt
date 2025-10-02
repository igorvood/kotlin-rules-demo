package ru.vood.kotkin.rules.ru.vood.kotkin.rules._500_mockk

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ArgumentMatchersTest {

    interface Service {
        fun process(item: String, priority: Int): String
    }

    @Test
    fun `argument matchers example`() {
        val service = mockk<Service>()

        // any() - любой аргумент
        every { service.process(any(), any()) } returns "processed"

        // eq() - конкретное значение
        every { service.process("important", eq(10)) } returns "high priority"

        // or() - одно из значений
        every { service.process(any(), or(1, 2)) } returns "low priority"

        // more() - больше чем
        every { service.process(any(), more(5)) } returns "medium priority"

        // capture - захват аргументов
        val capturedArgs = mutableListOf<String>()
        every { service.process(capture(capturedArgs), any()) } returns "captured"

        // Используем
        service.process("test", 1)
        service.process("important", 10)
        service.process("data", 7)

        // Проверяем захваченные аргументы
        assert(capturedArgs.contains("test"))
        assert(capturedArgs.contains("important"))
    }
}