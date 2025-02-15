package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.order.Order
import jakarta.persistence.Id
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemRepository: JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE (i.code = :code)")
    fun findByCode(@Param("code") code: Int?, sort: Sort): Item?

    fun findByOrder(order: Order): List<Item>
}