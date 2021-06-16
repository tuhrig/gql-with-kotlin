package de.tuhrig.fetchasyncdatafromapi.domain

interface UserApi {
    fun findUserById(id: String): User?
    fun findPostsByUserId(userId: String): List<Post>
}
