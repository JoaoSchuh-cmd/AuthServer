package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.items.ItemRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val itemRepository: ItemRepository
) {
    fun insert(order: Order) = orderRepository.save(order)

    fun findAll(): MutableList<Order> = orderRepository.findAll()
    fun findAllUsersOrders(id: Long): MutableList<Order> = orderRepository.findAllUsersOrders(id)
    fun findByIdOrNull(id: Long): Order? = orderRepository.findByIdOrNull(id)
    fun findByNumberOrNull(number: Int): Order? = orderRepository.findByNumber(number)
    fun findAllItemsOfOrder(order: Order): MutableList<Item> = itemRepository.findByOrder(order)
    fun delete(id: Long) = orderRepository.deleteById(id)
}