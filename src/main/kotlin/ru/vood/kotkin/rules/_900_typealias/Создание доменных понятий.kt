package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// Доменные типы для финансового приложения
typealias Currency = String
typealias Amount = Double
typealias AccountNumber = String
typealias TransactionId = String

class BankAccount {
    fun transfer(
        from: AccountNumber,
        to: AccountNumber,
        amount: Amount,
        currency: Currency
    ): TransactionId {
        // Логика перевода
        return "TX-12345"
    }
}