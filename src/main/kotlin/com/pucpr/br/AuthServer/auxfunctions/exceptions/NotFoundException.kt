package com.pucpr.br.AuthServer.auxfunctions.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException (
    message: String? = "Not found",
    cause: Throwable? = null
): IllegalArgumentException(message, cause)