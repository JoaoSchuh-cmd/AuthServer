package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.auxfunctions.SortDir
import org.springframework.data.domain.Sort
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
@RequestMapping("/items")
class ItemController(
    val itemService: ItemService
) {
    @PostMapping
    fun insert(@RequestBody item: Item) {
        itemService.insert(item).let {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(it)
        }
    }

    @GetMapping()
    fun findAll(@RequestParam dir: String = "ASC"): ResponseEntity<List<Item>> {
        val sortDir = SortDir.findOrNull(dir) ?:
            return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(itemService.findAll(sortDir))
    }

    @GetMapping("/id={id}")
    fun findById(@PathVariable id: Long) =
        itemService.findByIdOrNull(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/code={code}")
    fun findByCode(@PathVariable code: Int, @RequestParam dir: String = "ASC") {
        when (dir) {
            "ASC" -> itemService.findByCode(code, SortDir.ASC).let { ResponseEntity.ok(it) }
            "DESC" -> itemService.findByCode(code, SortDir.DESC).let { ResponseEntity.ok(it) }
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        itemService.delete(id).let { ResponseEntity.ok(it) }
}