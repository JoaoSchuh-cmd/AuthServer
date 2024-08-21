package br.pucpr.authserver2.roles

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    repository: RoleRepository
) {
    fun insert(role: Role) = roleRepository.save(role)

    fun findAll() = roleRepository.findAll(Sort.by("name").ascending())

    fun findById(id: Long) = roleRepository.findById(id)
}