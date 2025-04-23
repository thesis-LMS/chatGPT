package com.library.system.repository

import com.library.system.model.BorrowingRecord
import com.library.system.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BorrowingRecordRepository : JpaRepository<BorrowingRecord, UUID> {

    // 1. Find borrowing records by bookId and returned status
    fun findByBookIdAndReturned(bookId: UUID, returned: Boolean): List<BorrowingRecord>

    // 2. Find borrowing records by user
    fun findByUser(user: User): List<BorrowingRecord>
}
