package com.ajax.ajaxtestassignment.domain.entities

data class ContactEntity(var id: Int = 0,
                         val firstName: String,
                         val lastName: String,
                         val title: String = "Engineer",
                         val email: String = "dev@gmail.com",
                         val photoURL: String = "https://randomuser.me/api/portraits/med/women/72.jpg",
                         val gender: String = "male",
                         val phone1: String = "123455778",
                         val phone2: String = "234567890")