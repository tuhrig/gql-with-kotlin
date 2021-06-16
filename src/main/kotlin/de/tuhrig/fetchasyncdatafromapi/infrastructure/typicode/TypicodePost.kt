package de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode

data class TypicodePost(
    val userId: String,
    val id: String,
    val title: String,
    val body: String,
)
