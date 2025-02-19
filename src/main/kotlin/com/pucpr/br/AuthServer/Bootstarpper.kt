package com.pucpr.br.AuthServer

import com.pucpr.br.AuthServer.roles.Role
import com.pucpr.br.AuthServer.roles.RoleRepository
import com.pucpr.br.AuthServer.users.User
import com.pucpr.br.AuthServer.users.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class Bootstrapper(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
): ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRole =
            roleRepository.findByIdOrNull("ADMIN")
                ?: roleRepository.save(Role("ADMIN", "System Administrator"))
                    .also { roleRepository.save(Role("USER", "Premium User")) }

        if (userRepository.findByRole("ADMIN").isEmpty()) {
            val admin = User(
                email="admin@authserver.com",
                password = "admin",
                name="Auth Server Administrator"
            )
            admin.roles.add(adminRole)
            userRepository.save(admin)
        }

        if (userRepository.findByEmail("user1@authserver.com") == null) {
            val user1 = User(
                email = "user1@authserver.com",
                password = "user1",
                name = "USER 1"
            )
            user1.roles.add(roleRepository.findByIdOrNull("USER")!!)
            userRepository.save(user1)
        }
        if (userRepository.findByEmail("user2@authserver.com") == null) {
            val user2 = User(
                email = "user2@authserver.com",
                password = "user2",
                name = "USER 2"
            )
            user2.roles.add(roleRepository.findByIdOrNull("ADMIN")!!)
            userRepository.save(user2)
        }
    }

}