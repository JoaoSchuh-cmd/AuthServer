package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.order.Order
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "tblItems")
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    @Column(name = "code", nullable = false)
    val code: String,

    @NotNull
    @Column(name = "description", nullable = false)
    val description: String,

    @ManyToOne
    @JoinColumn(name = "id_order", foreignKey = ForeignKey(name = "fk_item_id_order"))
    val order: Order? = null
)