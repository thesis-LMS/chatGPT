package com.library.system.web

import com.library.system.model.User
import com.library.system.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun registerUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        val created = userService.registerUser(user)
        return ResponseEntity
            .created(URI.create("/api/users/${created.id}"))
            .body(created)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @Valid @RequestBody user: User
    ): ResponseEntity<User> {
        return ResponseEntity.ok(userService.updateUser(id, user))
    }
}
