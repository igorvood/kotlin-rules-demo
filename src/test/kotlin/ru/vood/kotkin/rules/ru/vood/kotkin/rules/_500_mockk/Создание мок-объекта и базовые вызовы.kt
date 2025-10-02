package ru.vood.kotkin.rules.ru.vood.kotkin.rules._500_mockk.ru.vood.kotkin.rules.ru.vood.kotkin.rules._500_mockk

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class SimpleMockExampleTest {

    // Интерфейс для демонстрации
    interface UserRepository {
        fun findUserById(id: Int): String?
        fun saveUser(name: String): Boolean
    }

    @Test
    fun `simple mock example`() {
        // Создаем мок-объект
        val userRepository = mockk<UserRepository>()

        // Настраиваем поведение мока
        every { userRepository.findUserById(1) } returns "John Doe"
        every { userRepository.findUserById(2) } returns null
        every { userRepository.saveUser(any()) } returns true

        // Используем мок в тесте
        val result1 = userRepository.findUserById(1)
        val result2 = userRepository.findUserById(2)
        val result3 = userRepository.saveUser("Alice")

        // Проверяем вызовы
        verify {
            userRepository.findUserById(1)
            userRepository.findUserById(2)
            userRepository.saveUser("Alice")
        }

        // Проверяем результаты
        assert(result1 == "John Doe")
        assert(result2 == null)
        assert(result3 == true)
    }
}