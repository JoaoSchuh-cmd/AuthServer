package br.pucpr.authserver.users

import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun insert(user: User)= userRepository.save(user)
    fun findAll(dir: SortDir) =
        when(dir) {
            SortDir.ASC -> userRepository.findAll(Sort.by("name"))
            SortDir.DESC -> userRepository.findAll(Sort.by("name").descending())
        }
    fun findByIdOrNull(id: Long) =
        userRepository.findByIdOrNull(id)
    fun delete(id: Long) = userRepository.deleteById(id)
}
