package com.library.system.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var author: String,

    @Column(nullable = false)
    var available: Boolean = true,

    var borrowedByUserId: UUID? = null,

    var dueDate: LocalDate? = null
)
