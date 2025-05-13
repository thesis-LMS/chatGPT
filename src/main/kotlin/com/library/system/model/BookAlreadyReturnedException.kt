package com.library.system.model

class BookAlreadyReturnedException(
    message: String,
) : RuntimeException(message)
