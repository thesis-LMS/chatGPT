package com.library.system.repository

import com.library.system.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, UUID> {

    // Custom search method to find books by title, author, and availability
    fun findByTitleContainingAndAuthorContainingAndAvailable(
        title: String?,
        author: String?,
        available: Boolean?
    ): List<Book>

}
