package com.hugo.datasource.local

import androidx.room.withTransaction
import com.hugo.datasource.local.RoomDB.AppDB
import javax.inject.Inject
import javax.inject.Singleton

//Room’s withTransaction { ... } ensures that all database operations inside the block:
//•	either succeed together, or
//•	fail together (rollback if any one fails).

@Singleton
class TransactionProvider @Inject constructor(
    private val db: AppDB
) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }
}