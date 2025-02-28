package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.ItemRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val itemRepository: ItemRepository
) {
    fun insert(order: Order) = orderRepository.save(order)

    fun findAll() = orderRepository.findAll()
    fun findAllUsersOrders(id: Long) = orderRepository.findAllUsersOrders(id)
    fun findByIdOrNull(id: Long) =
        orderRepository.findByIdOrNull(id)
    fun findByNumberOrNull(number: Int) = orderRepository.findByNumber(number)
    fun findAllItemsByOrder(order: Order) = itemRepository.findByOrder(order)

    fun delete(id: Long) = orderRepository.deleteById(id)
}