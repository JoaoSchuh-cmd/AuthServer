package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.auxfunctions.exceptions.NotFoundException
import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.items.ItemService
import com.pucpr.br.AuthServer.security.UserToken
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.aspectj.weaver.ast.Not
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService,
    private val itemService: ItemService
) {
    @PostMapping
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('BUYER')")
    fun insert(@RequestBody order: Order) =
        orderService.insert(order).let {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(it)
        }

    @GetMapping
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN') || hasRole('BUYER')")
    fun findAll(auth: Authentication) : ResponseEntity<List<Order>> {
        val user = auth.principal as UserToken

        val orders = when {
            user.isAdmin -> orderService.findAll()
            user.isBuyer -> orderService.findAllUsersOrders(user.id)
            else -> return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    fun findByIdOrNull(@PathVariable id: Long) =
        orderService.findByIdOrNull(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/number={number}")
    fun findByIdOrNull(@PathVariable number: Int) =
        orderService.findByNumberOrNull(number)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}/items")
    fun findAllItems(@PathVariable id: Long): ResponseEntity<List<Item>>? {
        val order = orderService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(orderService.findAllItemsByOrder(order))
    }

    @DeleteMapping("/{id}/items/{item_id}")
    fun deleteItemFromItemList(@PathVariable id: Long, @PathVariable item_id: Long) {
        val order = orderService.findByIdOrNull(id) ?: throw NotFoundException("Order not found!")
        val itemToRemove = itemService.findByIdOrNull(item_id) ?: throw NotFoundException("Item not found!")

        if (!order.items.contains(itemToRemove)) throw NotFoundException("Item not found on order's item list!")
        else {
            order.items.remove(itemToRemove)
            ResponseEntity.ok(order)
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long) =
        orderService.delete(id).let { ResponseEntity.ok(it) }
}