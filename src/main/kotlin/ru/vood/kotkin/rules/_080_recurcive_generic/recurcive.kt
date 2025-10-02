package ru.vood.kotkin.rules.ru.vood.kotkin.rules._080_recurcive_generic

/**
 * Интерфейс для изменяемых (mutable) объектов, которые могут быть преобразованы в неизменяемые.
 *
 * @param SELF Тип самого изменяемого объекта (рекурсивный тип)
 * @param OTHER Тип соответствующего неизменяемого объекта
 */
interface IMutable<SELF : IMutable<SELF, OTHER>, OTHER : IImmutable<OTHER, SELF>> {

    /**
     * Преобразует изменяемый объект в его неизменяемую версию.
     *
     * @return Неизменяемая версия этого объекта
     */
    fun toImmutable(): OTHER
}

/**
 * Интерфейс для неизменяемых (immutable) объектов, которые могут быть преобразованы в изменяемые.
 *
 * @param SELF Тип самого неизменяемого объекта (рекурсивный тип)
 * @param OTHER Тип соответствующего изменяемого объекта
 */
interface IImmutable<SELF : IImmutable<SELF, OTHER>, OTHER : IMutable<OTHER, SELF>> {

    /**
     * Преобразует неизменяемый объект в его изменяемую версию.
     *
     * @return Изменяемая версия этого объекта
     */
    fun toMutable(): OTHER
}

/**
 * Изменяемая версия сущности, используемая на уровне доступа к данным (Hibernate).
 * Содержит переменные свойства (var) для возможности изменения.
 */
data class HibernateEntity(
    var id: String  // var позволяет изменять значение
) : IMutable<HibernateEntity, BusinessEntity> {

    /**
     * Преобразует HibernateEntity (изменяемую) в BusinessEntity (неизменяемую).
     *
     * @return Неизменяемая BusinessEntity с теми же данными
     */
    override fun toImmutable(): BusinessEntity = BusinessEntity(id)
}

/**
 * Неизменяемая версия сущности, используемая на бизнес-уровне.
 * Содержит только val свойства, что гарантирует неизменяемость.
 */
data class BusinessEntity(
    val id: String  // val гарантирует неизменяемость
) : IImmutable<BusinessEntity, HibernateEntity> {

    /**
     * Преобразует BusinessEntity (неизменяемую) в HibernateEntity (изменяемую).
     *
     * @return Изменяемая HibernateEntity с теми же данными
     */
    override fun toMutable(): HibernateEntity = HibernateEntity(id)
}
/*
Основные концепции, используемые в коде:
1. Рекурсивные типы (Self-types)
SELF : IMutable<SELF, OTHER> - обеспечивает типобезопасность, указывая что SELF должен реализовывать тот же интерфейс
Позволяет создавать типобезопасные иерархии

2. Двойная диспетчеризация
Каждый интерфейс знает о своем "парном" интерфейсе
IMutable знает о IImmutable и наоборот

3. Преимущества подхода:
Разделение ответственности: изменяемые объекты для persistence слоя, неизменяемые - для бизнес-логики
Типобезопасность: компилятор проверяет корректность преобразований
Чистая архитектура: четкое разделение между слоями данных и бизнес-логики
 */