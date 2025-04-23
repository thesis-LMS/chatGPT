package com.library.system.services

import com.library.system.model.BookAlreadyReturnedException
import com.library.system.model.BookNotAvailableException
import com.library.system.model.ResourceNotFoundException
import com.library.system.model.Book
import com.library.system.repository.BookRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository) {

    fun addBook(book: Book): Book {
        return bookRepository.save(book)
    }

    fun getBookById(id: UUID): Book {
        return bookRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Book with ID $id not found")
        }
    }

    fun getAllBooks(): List<Book> {
        return bookRepository.findAll()
    }

    fun updateBook(id: UUID, updatedBook: Book): Book {
        val existingBook = getBookById(id)
        existingBook.apply {
            title = updatedBook.title
            author = updatedBook.author
            available = updatedBook.available
        }
        return bookRepository.save(existingBook)
    }

    fun deleteBookById(id: UUID) {
        val book = getBookById(id)
        bookRepository.delete(book)
    }

    fun searchBooks(title: String?, author: String?, available: Boolean?): List<Book> {
        return bookRepository.findByTitleContainingAndAuthorContainingAndAvailable(title, author, available ?: true)
    }

    fun borrowBook(bookId: UUID, userId: UUID): Book {
        val book = getBookById(bookId)
        if (!book.available) {
            throw BookNotAvailableException("Book with ID $bookId is not available")
        }
        book.apply {
            available = false
            borrowedByUserId = userId
        }
        return bookRepository.save(book)
    }

    fun returnBook(bookId: UUID): Book {
        val book = getBookById(bookId)
        if (book.available) {
            throw BookAlreadyReturnedException("Book with ID $bookId has already been returned")
        }
        book.apply {
            available = true
            borrowedByUserId = null
        }
        return bookRepository.save(book)
    }
}
