package com.ajax.ajaxtestassignment.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ajax.ajaxtestassignment.api.RetrofitServicesProvider
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.db.AppDatabase
import com.ajax.ajaxtestassignment.ui.contactslist.ContactsListViewModel
import com.ajax.ajaxtestassignment.ui.details.DetailsViewModel
import com.ajax.ajaxtestassignment.domain.usecases.FirstInitContactsUseCase
import com.ajax.ajaxtestassignment.domain.usecases.ResetDatabaseUseCase
import com.ajax.ajaxtestassignment.domain.usecases.UseCase
import com.ajax.ajaxtestassignment.repositories.*

object GlobalFactory: ViewModelProvider.Factory {

    val service: ContactsService by lazy {
        RetrofitServicesProvider().contactsService
    }

    lateinit var db: AppDatabase
    lateinit var sharedPreferences: SharedPreferences
    lateinit var localRepo: LocalRepositoryInterface
    lateinit var remoteRepo: RemoteRepositoryInterface
    lateinit var settingsRepo: SettingsRepositoryInterface
    lateinit var firstInitContactUseCase: UseCase
    lateinit var resetDatabaseUseCase: ResetDatabaseUseCase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()

        sharedPreferences = context.getSharedPreferences("AJAX_TEST_APP_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        settingsRepo = SettingsRepositoryImplementation(sharedPreferences)
        localRepo = LocalRepositoryImplementation(context, settingsRepo, db)
        remoteRepo = RemoteRepositoryImplementation(context, settingsRepo, service)

        resetDatabaseUseCase = ResetDatabaseUseCase(localRepo, remoteRepo)
        firstInitContactUseCase = FirstInitContactsUseCase(settingsRepo, resetDatabaseUseCase)

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ContactsListViewModel::class.java -> ContactsListViewModel(firstInitContactUseCase, localRepo)
            DetailsViewModel::class.java -> DetailsViewModel()
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}
