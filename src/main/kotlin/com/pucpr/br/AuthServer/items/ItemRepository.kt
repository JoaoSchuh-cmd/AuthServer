package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.order.Order
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository: JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = ["orders"])
    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.orders WHERE i.code = :code")
    fun findByCode(@Param("code") code: String): Item?

    @Query("SELECT i FROM Item i JOIN i.orders o WHERE o = :order")
    fun findByOrder(order: Order): MutableList<Item>
}