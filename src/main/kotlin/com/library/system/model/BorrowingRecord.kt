package com.library.system.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "borrowing_records")
data class BorrowingRecord(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    @Column(nullable = false)
    val bookId: UUID,
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    var borrowDate: LocalDate,
    @Column(nullable = false)
    var dueDate: LocalDate,
    var returnDate: LocalDate? = null,
    var lateFee: Double = 0.0,
)
