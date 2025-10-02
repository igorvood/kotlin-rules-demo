package ru.vood.kotkin.rules.ru.vood.kotkin.rules._500_mockk

import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test

class SpyExampleTest {

    class RealService {
        fun heavyOperation(): String {
            // Представим что это тяжелая операция
            Thread.sleep(100)
            return "real result"
        }

        fun lightOperation(data: String): String {
            return "processed: $data"
        }
    }

    @Test
    fun `spy example`() {
        // Создаем spy - реальный объект с возможностью мокирования отдельных методов
        val service = spyk<RealService>()

        // Мокаем только тяжелую операцию
        every { service.heavyOperation() } returns "mocked result"

        // Легкая операция работает по-настоящему
        val lightResult = service.lightOperation("test")

        // Тяжелая операция возвращает мок-результат
        val heavyResult = service.heavyOperation()

        assert(lightResult == "processed: test")
        assert(heavyResult == "mocked result")

        verify {
            service.lightOperation("test")
            service.heavyOperation()
        }
    }
}