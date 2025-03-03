package com.pucpr.br.AuthServer

import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.items.ItemRepository
import com.pucpr.br.AuthServer.order.Order
import com.pucpr.br.AuthServer.order.OrderRepository
import com.pucpr.br.AuthServer.roles.Role
import com.pucpr.br.AuthServer.roles.RoleRepository
import com.pucpr.br.AuthServer.users.User
import com.pucpr.br.AuthServer.users.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import kotlin.math.log

@Component
class Bootstrapper(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository,
): ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        createUsers()
        createItems()
        createOrder()
    }

    fun createItems() {
        itemRepository.saveAll(
            mutableListOf(
                Item(
                    code = "PRD00001",
                    description = "Red Pencil"
                ),
                Item(
                    code = "PRD00002",
                    description = "Blue Pencil",
                ),
                Item(
                    code = "PRD00003",
                    description = "Blue Pencil",
                )
            )
        )
    }

    fun createOrder() {
        val items = itemRepository.findAllById (
            listOf("PRD00001", "PRD00002", "PRD00003")
                .mapNotNull { itemRepository.findByCode(it)?.id }
        )
        val order = Order(
            buyer = userRepository.findByEmail("user3@authserver.com"),
            number = 1
        )

        order.items.addAll(items)

        orderRepository.save(order)
    }

    fun createUsers() {
        val adminRole =
            roleRepository.findByIdOrNull("ADMIN")
                ?: roleRepository.save(Role("ADMIN", "System Administrator"))
                    .also { roleRepository.save(Role("USER", "Premium User")) }
                    .also { roleRepository.save(Role("REGISTER", "Items Registration User")) }
                    .also { roleRepository.save(Role("BUYER", "Orders Registration User")) }

        if (userRepository.findByRole("ADMIN").isEmpty()) {
            val admin = User(
                email="admin@authserver.com",
                password = "admin",
                name="Auth Server Administrator"
            )
            admin.roles.add(adminRole)
            userRepository.save(admin)
        }

        if (userRepository.findByEmail("user1@authserver.com") == null) {
            val user1 = User(
                email = "user1@authserver.com",
                password = "user1",
                name = "USER 1"
            )
            user1.roles.add(roleRepository.findByIdOrNull("USER")!!)
            user1.roles.add(roleRepository.findByIdOrNull("REGISTER")!!)
            userRepository.save(user1)
        }
        if (userRepository.findByEmail("user2@authserver.com") == null) {
            val user2 = User(
                email = "user2@authserver.com",
                password = "user2",
                name = "USER 2"
            )
            user2.roles.add(roleRepository.findByIdOrNull("ADMIN")!!)
            userRepository.save(user2)
        }
        if (userRepository.findByEmail("user3@authserver.com") == null) {
            val user3 = User(
                email = "user3@authserver.com",
                password = "user3",
                name = "USER 3"
            )
            user3.roles.add(roleRepository.findByIdOrNull("USER")!!)
            user3.roles.add(roleRepository.findByIdOrNull("BUYER")!!)
            userRepository.save(user3)
        }
        if (userRepository.findByEmail("user4@authserver.com") == null) {
            val user4 = User(
                email = "user4@authserver.com",
                password = "user4",
                name = "USER 4"
            )
            user4.roles.add(roleRepository.findByIdOrNull("USER")!!)
            user4.roles.add(roleRepository.findByIdOrNull("BUYER")!!)
            userRepository.save(user4)
        }
    }

}