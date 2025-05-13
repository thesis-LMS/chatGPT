package com.library.system.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Entity
data class User(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    @field:NotBlank(message = "Name is mandatory")
    val name: String,
    @field:NotBlank(message = "Email is mandatory")
    @field:Email(message = "Email should be valid")
    @Column(unique = true)
    val email: String,
    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.MEMBER,
)
