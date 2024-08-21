package br.pucpr.authserver2.roles

class RoleResponse(
    val name: String,
    val description: String
) {
    constructor(role: Role): this(name = role.name, description = role.description)
}