package com.pucpr.br.AuthServer.security

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pucpr.br.AuthServer.users.User

data class UserToken(
    val id: Long,
    val name: String,
    val roles: Set<String>
) {
    // exigência do Jackson (biblioteca que gerencia o json, como o gson do google, por exemplo)
    constructor(): this(0, "", setOf())
    constructor(user: User): this(
        id = user.id!!,
        name = user.name,
        roles = user.roles.map { it.name }.toSortedSet()
    )

    @get:JsonIgnore
    val isAdmin: Boolean get() = "ADMIN" in roles
}
