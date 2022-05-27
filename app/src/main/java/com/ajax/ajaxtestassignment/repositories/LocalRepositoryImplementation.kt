package com.ajax.ajaxtestassignment.repositories

import android.content.Context
import com.ajax.ajaxtestassignment.db.AppDatabase
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import java.util.*

class LocalRepositoryImplementation(private val context: Context,
                                    private val settingsRepository: SettingsRepositoryInterface,
                                    private val appDatabase: AppDatabase
): LocalRepositoryInterface {
    override suspend fun insert(vararg contacts: ContactEntity) {
        for (entity in contacts) {
            appDatabase.userDao().insertAll(entityToContact(entity))
        }
    }

    override suspend fun getAll(): List<ContactEntity> {
        val contacts = appDatabase.userDao().getContacts()
        var resultList = LinkedList<ContactEntity>()
        for(contact in contacts) {
            resultList.add(contactToEntity(contact))
        }
        return resultList
    }

    override suspend fun deleteAll() {
        appDatabase.userDao().deleteAll()
    }

    override suspend fun getById(id: Int): ContactEntity {
        return contactToEntity(appDatabase.userDao().loadAllByIds(intArrayOf(id))[0])
    }

    override suspend fun update(contact: ContactEntity) {
        appDatabase.userDao().update(entityToContact(contact))
    }

    private fun contactToEntity(contact: DbContact): ContactEntity {
        return ContactEntity(id = contact.id,
            firstName = contact.firstName!!,
            lastName = contact.lastName!!,
            email = contact.email!!,
            photoURL = contact.photoURL!!
        )
    }

    private fun entityToContact(entity: ContactEntity): DbContact {
        var contact = DbContact(id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            //phone = entity.phone1,
            photoURL = entity.photoURL
        )
        return contact
    }
}