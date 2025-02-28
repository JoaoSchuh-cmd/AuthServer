package com.pucpr.br.AuthServer.auxfunctions.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnhauthorizedUser(
    message: String? = "User unhauthorized",
    cause: Throwable? = null
): IllegalArgumentException(message, cause)