package com.pucpr.br.AuthServer.roles

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.intellij.lang.annotations.Pattern

@Entity
class Role(
    @Id
    @Pattern("^[A-Z][A-Z0-9]+$")
    var name: String,
    var description: String
)
