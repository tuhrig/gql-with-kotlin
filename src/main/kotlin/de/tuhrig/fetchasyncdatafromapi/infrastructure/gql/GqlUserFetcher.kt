package de.tuhrig.fetchasyncdatafromapi.infrastructure.gql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import de.tuhrig.fetchasyncdatafromapi.domain.User
import de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode.TypicodeRestClient
import java.util.concurrent.CompletableFuture

@DgsComponent
class GqlUserFetcher(
    private val typicodeRestClient: TypicodeRestClient
) {
    @DgsQuery
    fun find(@InputArgument userId: String): CompletableFuture<User?> {
        return CompletableFuture.supplyAsync {
            typicodeRestClient.findUserById(userId)
        }
    }
}
