package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.auxfunctions.exceptions.BadRequestException
import com.pucpr.br.AuthServer.auxfunctions.exceptions.UnhauthorizedUser
import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.security.UserToken
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
    val orderRepository: OrderRepository
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
    fun findAll(@PathVariable auth: Authentication) : ResponseEntity<List<Order>> {
        val user = auth.principal as UserToken
        return if (user.isAdmin)
            ResponseEntity.ok(orderService.findAll())
        else if (user.isBuyer)
            ResponseEntity.ok(orderService.findAllUsersOrders(user.id))
        else
            throw BadRequestException("User role not authorized")
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
        val order = orderRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(orderService.findAllItemsByOrder(order))
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long) =
        orderService.delete(id).let { ResponseEntity.ok(it) }
}