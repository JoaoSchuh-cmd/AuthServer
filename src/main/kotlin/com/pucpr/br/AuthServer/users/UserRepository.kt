package com.pucpr.br.AuthServer.users

import com.pucpr.br.AuthServer.users.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
