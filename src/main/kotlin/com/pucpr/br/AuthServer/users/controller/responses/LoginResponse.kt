package com.pucpr.br.AuthServer.users.controller.responses

data class LoginResponse(
    val token: String,
    val user: UserResponse
)