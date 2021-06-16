package de.tuhrig.fetchasyncdatafromapi.domain

data class User(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val posts: List<Post>,
)
