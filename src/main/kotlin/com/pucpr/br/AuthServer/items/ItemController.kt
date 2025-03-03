package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.auxfunctions.SortDir
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController(
    val itemService: ItemService
) {
    @PostMapping
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN') || hasRole('REGISTER')")
    fun insert(@RequestBody item: Item) {
        itemService.insert(item).let {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(it)
        }
    }

    @GetMapping()
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN') || hasRole('REGISTER')")
    fun findAll(@RequestParam dir: String = "ASC"): ResponseEntity<List<Item>> {
        val sortDir = SortDir.findOrNull(dir) ?:
            return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(itemService.findAll(sortDir))
    }

    @GetMapping("/id={id}")
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN') || hasRole('REGISTER')")
    fun findById(@PathVariable id: Long) =
        itemService.findByIdOrNull(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/code={code}")
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN') || hasRole('REGISTER')")
    fun findByCode(@PathVariable code: String, @RequestParam dir: String = "ASC") {
        itemService.findByCode(code)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "AuthServer")
    fun delete(@PathVariable id: Long) =
        itemService.delete(id).let { ResponseEntity.ok(it) }
}