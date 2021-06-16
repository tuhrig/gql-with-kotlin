package de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode

import de.tuhrig.fetchasyncdatafromapi.domain.Post
import de.tuhrig.fetchasyncdatafromapi.domain.User
import de.tuhrig.fetchasyncdatafromapi.domain.UserApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class TypicodeRestClient(
    private val restTemplate: RestTemplate,
    @Value("\${typicode.base-url}") private val baseUrl: String,
) : UserApi {

    override fun findUserById(id: String): User? {
        return try {
            restTemplate.getForObject("$baseUrl/users/$id", TypicodeUser::class.java)?.let {
                User(
                    id = it.id,
                    name = it.name,
                    username = it.username,
                    email = it.username,
                    posts = listOf()
                )
            }
        } catch (e: HttpClientErrorException.NotFound) {
            null
        }
    }

    override fun findPostsByUserId(userId: String): List<Post> {
        val posts = restTemplate.exchange(
            "$baseUrl/posts?userId=$userId",
            GET,
            null,
            object : ParameterizedTypeReference<List<TypicodePost>>() {}
        ).body ?: emptyList()
        return posts
            .map {
                Post(
                    id = it.id,
                    title = it.title,
                    body = it.body
                )
            }
    }
}
