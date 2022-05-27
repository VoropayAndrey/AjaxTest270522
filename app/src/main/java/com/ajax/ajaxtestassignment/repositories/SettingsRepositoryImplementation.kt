package com.ajax.ajaxtestassignment.repositories

import android.content.SharedPreferences

class SettingsRepositoryImplementation(private val sharedPreferences: SharedPreferences):
    SettingsRepositoryInterface {

    val IS_FIRST_RUN = "IS_FIRST_RUN"

    override fun isFirstRun(): Boolean {
        //return true
        return sharedPreferences.getBoolean(IS_FIRST_RUN, true)
    }

    override fun setFirstRun(isFirstRun: Boolean) {
        sharedPreferences.edit().putBoolean(IS_FIRST_RUN, isFirstRun).commit()
    }
}