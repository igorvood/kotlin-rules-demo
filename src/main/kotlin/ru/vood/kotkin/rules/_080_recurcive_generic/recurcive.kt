package ru.vood.kotkin.rules.ru.vood.kotkin.rules._080_recurcive_generic

interface IMutable<SELF : IMutable<SELF, OTHER>, OTHER : IImmutable<OTHER, SELF>> {

    fun toImmutable(): OTHER
}

interface IImmutable<SELF : IImmutable<SELF, OTHER>, OTHER : IMutable<OTHER, SELF>> {
    fun toMutable(): OTHER
}


data class HibernateEntity(
    var id: String
) : IMutable<HibernateEntity, BusinessEntity> {
    override fun toImmutable(): BusinessEntity = BusinessEntity(id)
}


data class BusinessEntity(
    val id: String
) : IImmutable<BusinessEntity, HibernateEntity> {
    override fun toMutable(): HibernateEntity = HibernateEntity(id)
}
