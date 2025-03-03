package com.pucpr.br.AuthServer.order

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.users.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "tblOrders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Column(name = "number")
    var number: Int,

    @ManyToMany
    @JoinTable(
        name = "order_items",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<Item> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "buyer_id")
    val buyer: User?
)