package com.library.system.repository

import com.library.system.model.BorrowingRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BorrowingRecordRepository : JpaRepository<BorrowingRecord, UUID> {
    // To match BookServiceTest expectations
    fun findByBookIdAndReturnDateIsNull(bookId: UUID): Optional<BorrowingRecord>

    fun countByUserIdAndReturnDateIsNull(userId: UUID): Long

    // Original methods from your provided code (can be kept if used elsewhere or removed if redundant)
    // fun findByBookIdAndReturned(bookId: UUID, returned: Boolean): List<BorrowingRecord>
    // fun findByUser(user: User): List<BorrowingRecord>
}
