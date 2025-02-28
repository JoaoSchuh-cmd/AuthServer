package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.Item
import com.pucpr.br.AuthServer.users.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "tblOrders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Column(name = "number")
    var number: Int,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val items: List<Item>? = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "buyer_id")
    val buyer: User?
)