package com.party.data

internal interface DataMapper<DomainModel> {
    fun toDomain(): DomainModel
}