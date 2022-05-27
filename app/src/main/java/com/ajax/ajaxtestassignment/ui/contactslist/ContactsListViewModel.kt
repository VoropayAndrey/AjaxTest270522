package com.ajax.ajaxtestassignment.ui.contactslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import com.ajax.ajaxtestassignment.domain.usecases.UseCase
import com.ajax.ajaxtestassignment.repositories.LocalRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsListViewModel(val firstInitContactUseCase: UseCase,
                            val localRepo: LocalRepositoryInterface): ViewModel() {
    val contactsLiveData = MutableLiveData<List<ContactEntity>>()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                firstInitContactUseCase.invoke()
                var contacts = localRepo.getAll()
                withContext(Dispatchers.Main) {
                    contactsLiveData.value = contacts
                }
            }
        }

    }

    fun reloadAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                firstInitContactUseCase.invoke()
                var contacts = localRepo.getAll()
                withContext(Dispatchers.Main) {
                    contactsLiveData.value = contacts
                }
            }
        }
    }
}
