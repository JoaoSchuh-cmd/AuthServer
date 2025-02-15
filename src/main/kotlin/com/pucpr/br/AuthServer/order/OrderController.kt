package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.Item
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService
) {
    @PostMapping
    fun insert(@RequestBody order: Order) =
        orderService.insert(order).let {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(it)
        }

    @GetMapping
    fun findAll() : ResponseEntity<List<Order>> {
        return ResponseEntity.ok(orderService.findAll())
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

    @GetMapping("/{id}")
    fun findAllItems(@PathVariable id: Long): ResponseEntity<List<Item>>? {
        return orderService.findAllItems(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        orderService.delete(id).let { ResponseEntity.ok(it) }
}