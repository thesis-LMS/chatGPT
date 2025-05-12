package com.library.system.services

import com.library.system.model.ResourceNotFoundException
import com.library.system.model.User
import com.library.system.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {

    fun registerUser(user: User): User =
        userRepository.save(user)

    fun getUserById(id: UUID): User =
        userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with ID $id not found") }

    fun updateUser(id: UUID, updated: User): User {
        val existing = userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with ID $id not found") }

        return userRepository.save(
            existing.copy(
                name = updated.name,
                email = updated.email,
                role = updated.role
            )
        )
    }
}
