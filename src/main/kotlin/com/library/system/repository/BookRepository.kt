package com.library.system.repository

import com.library.system.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, UUID> {
    fun findByTitleContainingIgnoreCase(title: String): List<Book>

    fun findByAuthorContainingIgnoreCase(author: String): List<Book>

    fun findByAvailable(available: Boolean): List<Book>

    // For combined search, matching the test's expectation of IgnoreCase
    fun findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndAvailable(
        title: String?,
        author: String?,
        available: Boolean?,
    ): List<Book>

    // Keep the original if it's used elsewhere or if tests are mixed
    fun findByTitleContainingAndAuthorContainingAndAvailable(
        title: String?,
        author: String?,
        available: Boolean?,
    ): List<Book>
}
