package com.pucpr.br.AuthServer.order

import com.pucpr.br.AuthServer.items.Item

data class OrderWithoutBuyerResponse(
    val id: Long,
    val items: MutableList<Item>
)
