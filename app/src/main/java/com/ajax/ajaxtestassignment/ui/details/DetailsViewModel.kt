package com.ajax.ajaxtestassignment.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import com.ajax.ajaxtestassignment.repositories.LocalRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DetailsViewModel(val localRepositoryInterface: LocalRepositoryInterface): ViewModel() {

    enum class DetailState {
        VIEWING,
        EDITING
    }

    val fetchedContact: MutableLiveData<ContactEntity> = MutableLiveData()
    val detailState: MutableLiveData<DetailState> = MutableLiveData()

    fun fetchUserById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var contact = localRepositoryInterface.getById(id)
                withContext(Dispatchers.Main) {
                    fetchedContact.value = contact
                }
            }
        }
    }

    fun changeState(newState: DetailState) {
        detailState.value = newState
    }

    fun updateContactInfo(newContact: ContactEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                localRepositoryInterface.update(newContact)
                var contact = localRepositoryInterface.getById(newContact.id)
                withContext(Dispatchers.Main) {
                    fetchedContact.value = contact
                    changeState(DetailState.VIEWING)
                }
            }
        }
    }
}
