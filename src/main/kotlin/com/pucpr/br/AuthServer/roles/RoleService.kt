package com.pucpr.br.AuthServer.roles

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RoleService(
    val roleRepository: RoleRepository
) {
    fun save(role: Role) = roleRepository.save(role)
    fun findAll() = roleRepository.findAll(Sort.by("name"))
}
