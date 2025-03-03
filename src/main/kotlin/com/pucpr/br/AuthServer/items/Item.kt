package com.pucpr.br.AuthServer.items

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pucpr.br.AuthServer.order.Order
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "tblItems")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    @Column(name = "code", nullable = false)
    val code: String,

    @NotNull
    @Column(name = "description", nullable = false)
    val description: String,

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    @JsonIgnore
    val orders: MutableList<Order> = mutableListOf()
)