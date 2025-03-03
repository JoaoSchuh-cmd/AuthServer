package com.pucpr.br.AuthServer.users

import com.pucpr.br.AuthServer.roles.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinColumns
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull


@Entity
@Table(name = "tblUsers")
data class User(
    @Id @GeneratedValue
    var id: Long? = null,

    @NotNull
    var name: String,

    @NotNull
    var password: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @ManyToMany
    @JoinTable(
        name = "UserRoles",
        joinColumns = [JoinColumn(name = "idUser")],
        inverseJoinColumns = [JoinColumn(name = "idRole")]
    )
    val roles: MutableSet<Role> = mutableSetOf()
)
