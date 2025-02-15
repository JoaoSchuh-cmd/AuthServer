package com.pucpr.br.AuthServer.users

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull


@Entity
@Table(name = "tblUsers")
class User(
    @Id @GeneratedValue
    var id: Long? = null,

    @NotNull
    var name: String,

    @NotNull
    var password: String,

    @Column(unique = true, nullable = false)
    var email: String
)
