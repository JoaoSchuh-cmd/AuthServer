package br.pucpr.authserver2.auxiliar

import br.pucpr.authserver2.roles.Role
import br.pucpr.authserver2.roles.RoleRepository
import br.pucpr.authserver2.users.User
import br.pucpr.authserver2.users.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Bootstrapper(
    val userRepository: UserRepository,
    val rolesRepository: RoleRepository,
): ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRole =
            rolesRepository.findByName("ADMIN")
                ?: rolesRepository
                    .save(Role(name = "ADMIN", description = "System Administrator"))
                    .also { rolesRepository.save(Role(name = "USER", description = "Premium User")) }

        if (userRepository.findByRole(adminRole.name).isEmpty()) {
            val admin = User(
                name = "ADMIN",
                email = "admin@gmail.com",
                password = "admin",
            )

            admin.roles.add(adminRole)

            userRepository.save(admin)
        }
    }
}