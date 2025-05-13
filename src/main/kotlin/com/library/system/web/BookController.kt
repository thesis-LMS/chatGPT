package com.library.system.web

import com.library.system.model.Book
import com.library.system.model.BookAlreadyReturnedException
import com.library.system.model.BookNotAvailableException
import com.library.system.model.ResourceNotFoundException
import com.library.system.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService,
) {
    @PostMapping
    fun addBook(
        @RequestBody book: Book,
    ): ResponseEntity<Book> {
        val saved = bookService.addBook(book)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable id: UUID,
    ): ResponseEntity<Book> {
        val book = bookService.getBookById(id)
        return ResponseEntity.ok(book)
    }

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<Book>> {
        val books = bookService.getAllBooks()
        return ResponseEntity.ok(books)
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: UUID,
        @RequestBody book: Book,
    ): ResponseEntity<Book> {
        val updated = bookService.updateBook(id, book)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteBook(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        bookService.deleteBookById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchBooks(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) available: Boolean?,
    ): ResponseEntity<List<Book>> {
        val books = bookService.searchBooks(title, author, available)
        return ResponseEntity.ok(books)
    }

    @PostMapping("/{bookId}/borrow")
    fun borrowBook(
        @PathVariable bookId: UUID,
        @RequestParam userId: UUID,
    ): ResponseEntity<Book> {
        val borrowed = bookService.borrowBook(bookId, userId)
        return ResponseEntity.ok(borrowed)
    }

    @PostMapping("/{bookId}/return")
    fun returnBook(
        @PathVariable bookId: UUID,
    ): ResponseEntity<Book> {
        val returned = bookService.returnBook(bookId)
        return ResponseEntity.ok(returned)
    }

    // Exception handlers
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<String> = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)

    @ExceptionHandler(BookNotAvailableException::class)
    fun handleNotAvailable(ex: BookNotAvailableException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)

    @ExceptionHandler(BookAlreadyReturnedException::class)
    fun handleAlreadyReturned(ex: BookAlreadyReturnedException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
}
