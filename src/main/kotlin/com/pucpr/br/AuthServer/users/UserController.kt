package com.pucpr.br.AuthServer.users

import com.pucpr.br.AuthServer.auxfunctions.SortDir
import com.pucpr.br.AuthServer.security.UserToken
import com.pucpr.br.AuthServer.users.controller.requests.LoginRequest
import com.pucpr.br.AuthServer.users.controller.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @GetMapping("/check")
    fun ping() = "Pong"

    @PostMapping
    fun insert(@RequestBody user: User) =
        userService.insert(user)
            .let { UserResponse(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping
    fun findAll(@RequestParam dir: String = "ASC", @RequestParam role: String?): ResponseEntity<List<UserResponse>> {
        val sortDir = SortDir.findOrNull(dir)
        if (sortDir == null)
            return ResponseEntity.badRequest().build()
        return userService.findAll(sortDir, role)
            .map { UserResponse(it) }
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        userService.findByIdOrNull(id)
            ?.let { UserResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "AuthServer") // Configurado na SecurityConfig
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: String, auth: Authentication): ResponseEntity<Void> {
        val user = auth.principal as UserToken
        val uid = if (id == "me") user.id else id.toLong()
        return if (user.id == uid || user.isAdmin)
            userService.delete(uid).let { ResponseEntity.ok().build() }
        else
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()

    }

    @PutMapping("/{id}/roles/{role}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "AuthServer")
    fun grant(@PathVariable id: Long, @PathVariable role: String): ResponseEntity<Void> =
        if (userService.addRole(id, role)) ResponseEntity.ok().build()
        else ResponseEntity.noContent().build()

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: LoginRequest) =
        userService.login(user.email!!, user.password!!)
            ?.let { ResponseEntity.ok().body(it) }
            ?: ResponseEntity.notFound().build()
}
