package br.pucpr.authserver2.users

import br.pucpr.authserver2.auxiliar.SortDir
import br.pucpr.authserver2.roles.RoleRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    fun save(user: User) = userRepository.save(user)

    fun findAll(dir: SortDir, roleName: String?) =
        roleName?.let {
            when (dir) {
                SortDir.ASC -> userRepository.findByRole(roleName.uppercase()).sortedBy { it.name }
                SortDir.DESC -> userRepository.findByRole(roleName.uppercase()).sortedBy { it.name }
            }
        } ?: when (dir) {
            SortDir.ASC -> userRepository.findAll(Sort.by("name").ascending())
            SortDir.DESC -> userRepository.findAll(Sort.by("name").descending())
        }


    fun findByIdOrNull(id: Long) = userRepository.findByIdOrNull(id)

    fun findByEmail(email: String) = userRepository.findByEmail(email)

    fun deleteById(id: Long) =
        userRepository.deleteById(id)
            .let { ResponseEntity.ok(it) }

    fun addRole(id: Long, roleName: String): Boolean {
        val user = userRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("User ${id} not found!")

        if (user.roles.any() { it.name == roleName }) return false

        val role = roleRepository.findByName(roleName)
            ?: throw IllegalArgumentException("Invalid role ${roleName}!")

        user.roles.add(role)
        userRepository.save(user)
        return true
    }

}