package com.ajax.ajaxtestassignment.repositories

import com.ajax.ajaxtestassignment.domain.entities.ContactEntity

interface LocalRepositoryInterface {
    suspend fun insert(vararg contact: ContactEntity)
    suspend fun getAll(): List<ContactEntity>
    suspend fun deleteAll()
    suspend fun getById(id: Int): ContactEntity
    suspend fun update(contact: ContactEntity)
    suspend fun delete(contact: ContactEntity)
}