package ru.vood.kotkin.rules.`01_nulluble`

@JvmInline
value class ClientId(
    val value: String
)

data class Client(
    val id: ClientId,
)


@JvmInline
value class ClientIdNullable(
    val value: String?
)

data class ClientNullable(
    val id: ClientIdNullable?,
)


fun main() {
//    val client1 = Client(ClientId(null))

    val client = Client(ClientId("sad"))
    val clientNullable = ClientNullable(ClientIdNullable(null))

    val idStr: String = client.id.value
    val idStrNullable: String? = clientNullable.id?.value

    val idStrNotNull: String = clientNullable.id?.value?:error("какое - то сообщение об ошибке ")

}