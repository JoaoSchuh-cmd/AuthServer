package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.ResponseEntity

interface OrderRepository: JpaRepository<Order, Long> {
    fun findByNumberOrNull(number: Int): Order?
    fun findAllItems(orderId: Long): List<Item>?
}