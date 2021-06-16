package de.tuhrig.fetchasyncdatafromapi.infrastructure.gql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import de.tuhrig.fetchasyncdatafromapi.domain.Post
import de.tuhrig.fetchasyncdatafromapi.domain.User
import de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode.TypicodeRestClient
import java.util.concurrent.CompletableFuture

@DgsComponent
class GqlPostFetcher(
    private val typicodeRestClient: TypicodeRestClient
) {
    @DgsData(parentType = "User", field = "posts")
    fun reviews(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val user: User = dfe.getSource()
        return CompletableFuture.supplyAsync {
            typicodeRestClient.findPostsByUserId(user.id)
        }
    }
}
