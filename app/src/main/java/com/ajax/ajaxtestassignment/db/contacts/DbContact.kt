package com.ajax.ajaxtestassignment.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class DbContact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val lastName: String?,
    @ColumnInfo val email: String?,
    //@ColumnInfo val phone: String?,
    @ColumnInfo val photoURL: String?,

)
