package com.ajax.ajaxtestassignment.repositories

import android.content.Context
import com.ajax.ajaxtestassignment.api.contacts.ApiContact
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RemoteRepositoryImplementation: RemoteRepositoryInterface {
    private var context: Context
    private var settingsRepository: SettingsRepositoryInterface
    private var service: ContactsService

    constructor(context: Context,
                settingsRepository: SettingsRepositoryInterface,
                service: ContactsService
    ) {
        this.context = context
        this.settingsRepository = settingsRepository
        this.service = service

    }

    override suspend fun requestContact(): List<ContactEntity> {
        var responseList = service.getContacts().results?.map {
            ContactEntity(firstName = it.name?.firstName!!, lastName = it.name.lastName!!)
        }
        return responseList!!
    }
}