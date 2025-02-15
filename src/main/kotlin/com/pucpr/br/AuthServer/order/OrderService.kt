package com.pucpr.br.AuthServer.order

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository
) {
    fun insert(order: Order) = orderRepository.save(order)

    fun findAll() = orderRepository.findAll()
    fun findByIdOrNull(id: Long) =
        orderRepository.findByIdOrNull(id)
    fun findByNumberOrNull(number: Int) = orderRepository.findByNumberOrNull(number)
    fun findAllItems(orderId: Long) = orderRepository.findAllItems(orderId)

    fun delete(id: Long) = orderRepository.deleteById(id)
}