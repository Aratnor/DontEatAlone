package com.lambadam.data.mapper

interface EntityMapper<D, E> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(domain: D): E
}