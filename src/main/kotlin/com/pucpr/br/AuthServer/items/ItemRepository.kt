package com.pucpr.br.AuthServer.items

import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long> {
    fun findByCodeOrNull(code: Int): Item?
}