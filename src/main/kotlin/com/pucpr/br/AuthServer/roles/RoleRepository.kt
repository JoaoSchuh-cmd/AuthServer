package com.pucpr.br.AuthServer.roles

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, String>
