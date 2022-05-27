package com.ajax.ajaxtestassignment.repositories

import com.ajax.ajaxtestassignment.domain.entities.ContactEntity

interface RemoteRepositoryInterface {
    suspend fun requestContact(): List<ContactEntity>
}