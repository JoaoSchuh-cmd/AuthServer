package com.pucpr.br.AuthServer.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("security")
data class SecurityProperties @ConstructorBinding constructor(
    val token: String,
    val issuer: String,
    val expireHours: Long,
    val adminExpireHours: Long
)
