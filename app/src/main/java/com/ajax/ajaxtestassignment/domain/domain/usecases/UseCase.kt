package com.ajax.ajaxtestassignment.domain.usecases

interface UseCase {
    suspend fun invoke(): Boolean
}