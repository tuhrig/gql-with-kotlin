package de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode

import java.math.BigDecimal

data class TypicodeUser(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company,
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo,
)

data class Geo(
    val lat: BigDecimal,
    val lng: BigDecimal,
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)
