package com.pucpr.br.AuthServer.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository: JpaRepository<Order, Long> {
    fun findByNumber(number: Int): Order?

    @Query("SELECT o FROM Order o WHERE o.buyer.id = :userId")
    fun findAllUsersOrders(@Param("useId") id: Long): List<Order>
}