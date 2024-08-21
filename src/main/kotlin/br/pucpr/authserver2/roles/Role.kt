package br.pucpr.authserver2.roles

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Role(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val name: String,

    val description: String = ""
)  {
}