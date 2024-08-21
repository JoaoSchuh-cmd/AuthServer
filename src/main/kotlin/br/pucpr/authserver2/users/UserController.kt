package br.pucpr.authserver2.users

import br.pucpr.authserver2.auxiliar.SortDir
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @PostMapping()
    fun insert(@RequestBody @Valid user: UserRequest) =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.save(user.toUser()))

    @GetMapping()
    fun findAll(sortDir: String? = null) =
        SortDir.entries.firstOrNull { it.name == (sortDir ?: "ASC").uppercase() }
            ?.let { userService.findAll(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.badRequest().build()

    @GetMapping("/id={id}")
    fun findById(@PathVariable(value = "id") id: Long) =
        userService.findByIdOrNull(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/email={email}")
    fun findByEmail(@PathVariable(value = "email") email: String) =
        userService.findByEmail(email)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long) =
        userService.findByIdOrNull(id)
            ?.let {
                userService.deleteById(id)
                ResponseEntity.ok(it)
            }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}/roles/{role}")
    fun grant(
        @PathVariable id: Long,
        @PathVariable role: String
    ): ResponseEntity<Void> =
        if (userService.addRole(id, role)) ResponseEntity.ok().build()
        else ResponseEntity.noContent().build()

}