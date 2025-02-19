package com.pucpr.br.AuthServer.users

import com.pucpr.br.AuthServer.auxfunctions.SortDir
import com.pucpr.br.AuthServer.auxfunctions.exceptions.BadRequestException
import com.pucpr.br.AuthServer.roles.RoleService
import com.pucpr.br.AuthServer.security.Jwt
import com.pucpr.br.AuthServer.users.controller.responses.LoginResponse
import com.pucpr.br.AuthServer.users.controller.responses.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import com.pucpr.br.AuthServer.auxfunctions.exceptions.NotFoundException

@Service
class UserService(
    val userRepository: UserRepository,
    private val roleService: RoleService,
    private val jwt: Jwt
) {
    fun insert(user: User) = userRepository.save(user)

    fun findAll(dir: SortDir, role: String?): List<User> {
        if (!role.isNullOrBlank()) return userRepository.findByRole(role)
        return when(dir) {
            SortDir.ASC -> userRepository.findAll(Sort.by("name"))
            SortDir.DESC -> userRepository.findAll(Sort.by("name").descending())
        }
    }

    fun findByIdOrNull(id: Long) = userRepository.findByIdOrNull(id)

    fun delete(id: Long) = userRepository.deleteById(id)

    fun addRole(id: Long, roleName: String): Boolean {
        val roleUpper = roleName.uppercase()
        val user = findByIdOrNull(id) ?: throw NotFoundException("User ${id} not found")
        if (user.roles.any { it.name == roleUpper }) return false

        val role = roleService.findByNameOrNull(roleUpper) ?: throw BadRequestException("Role ${roleUpper} not found")

        user.roles.add(role)
        userRepository.save(user)
        return true
    }

    fun login(email: String, password: String):  LoginResponse? {
        val user = userRepository.findByEmail(email) ?: return null
        if (user.password != password) return null
        log.info("User logged i. id=${user.id}, name=${user.name}")
        return LoginResponse(
            token = jwt.createToken(user),
            user = UserResponse(user)
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }
}
