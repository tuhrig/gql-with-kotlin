package de.tuhrig.fetchasyncdatafromapi.ports.rest

import com.example.demo.generated.client.FindGraphQLQuery
import com.example.demo.generated.client.FindProjectionRoot
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserQueryController(
    private val dgsQueryExecutor: DgsQueryExecutor
) {

    @GetMapping("/users/{id}")
    fun findUser(@PathVariable id: String): ResponseEntity<Any> {
        log.debug("Get user. [userId={}]", id)
        val gqlQuery = GraphQLQueryRequest(
            FindGraphQLQuery
                .Builder()
                .userId(id)
                .build(),
            FindProjectionRoot()
                .id()
                .email()
                .posts().id()
        )
        val result = dgsQueryExecutor.execute(gqlQuery.serialize())
        return when (val user = result.toSpecification().values.firstOrNull()) {
            null -> ResponseEntity(NOT_FOUND)
            else -> ResponseEntity(user, OK)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
