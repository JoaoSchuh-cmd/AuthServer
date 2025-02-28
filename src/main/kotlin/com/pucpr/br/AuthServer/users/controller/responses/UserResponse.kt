package com.pucpr.br.AuthServer.users.controller.responses

import com.pucpr.br.AuthServer.roles.Role
import com.pucpr.br.AuthServer.users.User

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val roles: Set<Role>
) {
    constructor(user: User): this(id=user.id!!, user.name, user.email, user.roles)
}
