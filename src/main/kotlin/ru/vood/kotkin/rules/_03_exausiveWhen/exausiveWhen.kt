package ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen

import ru.vood.kotkin.rules.ru.vood.kotkin.rules._03_exausiveWhen.EEnum.*

sealed interface IResult

data class Ok(val value: String) : IResult

data class Error(val value: String) : IResult

enum class EEnum{
    VAL1,
    VAL2,
    VAL3,
}

fun main(){

    val ok: IResult = Ok("Asdasd")

    when(ok){
        is Error -> TODO()
        is Ok -> TODO()
    }

    val vaL1 = VAL1


    when(vaL1){
        VAL1 -> TODO()
        VAL2 -> TODO()
        VAL3 -> TODO()
    }

}