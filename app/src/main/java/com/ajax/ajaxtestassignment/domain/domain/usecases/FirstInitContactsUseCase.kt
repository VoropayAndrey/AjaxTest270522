package com.ajax.ajaxtestassignment.domain.usecases

import com.ajax.ajaxtestassignment.repositories.SettingsRepositoryInterface

class FirstInitContactsUseCase(private val settingsRepositoryInterface: SettingsRepositoryInterface,
                               private val resetDatabaseUseCase: ResetDatabaseUseCase): UseCase {
    override suspend fun invoke(): Boolean {
        if(settingsRepositoryInterface.isFirstRun()) {
            settingsRepositoryInterface.setFirstRun(false)
            resetDatabaseUseCase.invoke()
        }
        return true
    }
}