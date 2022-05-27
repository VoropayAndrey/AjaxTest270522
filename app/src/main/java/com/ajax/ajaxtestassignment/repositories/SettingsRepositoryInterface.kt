package com.ajax.ajaxtestassignment.repositories

interface SettingsRepositoryInterface {
    fun isFirstRun(): Boolean
    fun setFirstRun(isFirstRun: Boolean)
}