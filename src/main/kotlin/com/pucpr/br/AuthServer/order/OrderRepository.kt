package com.pucpr.br.AuthServer.order


import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByNumber(number: Int): Order?

    @EntityGraph(attributePaths = ["buyer", "items"])
    override fun findAll(): MutableList<Order>

    @Query("SELECT o FROM Order o WHERE o.buyer.id = :userId")
    @EntityGraph(attributePaths = ["buyer", "items"])
    fun findAllUsersOrders(@Param("userId") id: Long): MutableList<Order>
}