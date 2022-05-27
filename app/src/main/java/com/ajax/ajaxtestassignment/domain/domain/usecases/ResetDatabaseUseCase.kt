package com.ajax.ajaxtestassignment.domain.usecases

import com.ajax.ajaxtestassignment.repositories.LocalRepositoryInterface
import com.ajax.ajaxtestassignment.repositories.RemoteRepositoryInterface
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*


class ResetDatabaseUseCase(private val localRepositoryInterface: LocalRepositoryInterface,
                           private val remoteRepositoryInterface: RemoteRepositoryInterface
): UseCase {

    override suspend fun invoke(): Boolean {
        localRepositoryInterface.deleteAll()

        coroutineScope {
            async {
                remoteRepositoryInterface.requestContact()
                    .asFlow()
                    .flatMapMerge(concurrency = 10) {
                        flowOf(localRepositoryInterface.insert(it))
                    }
                    .collect()
            }
        }
        return true
    }
}