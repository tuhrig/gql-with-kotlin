package de.tuhrig.fetchasyncdatafromapi

import de.tuhrig.fetchasyncdatafromapi.domain.Post
import de.tuhrig.fetchasyncdatafromapi.domain.User
import org.springframework.util.FileCopyUtils
import java.nio.charset.StandardCharsets

object TestUtils {

    fun loadFileAsString(fileName: String): String {
        val stream = this::class.java.getResourceAsStream(fileName)
        return String(FileCopyUtils.copyToByteArray(stream), StandardCharsets.UTF_8)
    }
}

fun anUser(id: String): User {
    return User(
        id = id,
        name = "",
        username = "",
        email = "",
        posts = listOf()
    )
}

fun aPost(id: String): Post {
    return Post(
        id = id,
        title = "",
        body = ""
    )
}