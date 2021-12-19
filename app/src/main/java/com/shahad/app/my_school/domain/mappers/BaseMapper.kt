package com.shahad.app.my_school.domain.mappers

interface BaseMapper<I, O> {
    fun map(input: I): O
}

class ListBaseMapper<I, O>(private val mapper: BaseMapper<I, O>) : BaseMapper<List<I>, List<O>> {

    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }

}