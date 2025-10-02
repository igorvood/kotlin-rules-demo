package ru.vood.kotkin.rules.ru.vood.kotkin.rules._900_typealias

// API для работы с сетью
typealias Endpoint = String
typealias Headers = Map<String, String>
typealias QueryParams = Map<String, Any>
typealias ApiResponse<T> = Result<T>

class ApiClient {
     fun <T> get(
        endpoint: Endpoint,
        headers: Headers = emptyMap(),
        queryParams: QueryParams = emptyMap()
    ): ApiResponse<T> {
        // Реализация HTTP запроса
        return Result.success(Unit as T)
    }
}