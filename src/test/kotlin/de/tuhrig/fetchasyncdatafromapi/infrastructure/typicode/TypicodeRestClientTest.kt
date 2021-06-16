package de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode

import de.tuhrig.fetchasyncdatafromapi.TestUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate

class TypicodeRestClientTest {

    private val baseUrl = "https://my-dummy-typicode-api.com"

    private lateinit var mockServer: MockRestServiceServer
    private lateinit var restClient: TypicodeRestClient

    @BeforeEach
    fun setUp() {
        val restTemplate = RestTemplate()
        mockServer = MockRestServiceServer.createServer(restTemplate)
        restClient = TypicodeRestClient(restTemplate, baseUrl)
    }

    @AfterEach
    fun tearDown() {
        mockServer.verify()
    }

    @Test
    fun `should request user from typicode API`() {
        mockServer
            .expect(requestTo("$baseUrl/users/1"))
            .andRespond(withSuccess(TestUtils.loadFileAsString("/typicode/TypicodeUser.json"), APPLICATION_JSON))

        val user = restClient.findUserById("1")

        assertThat(user?.id).isEqualTo("1")
    }

    @Test
    fun `should return null if user is not found`() {
        mockServer
            .expect(requestTo("$baseUrl/users/1"))
            .andRespond(withStatus(HttpStatus.NOT_FOUND))

        val user = restClient.findUserById("1")

        assertThat(user).isNull()
    }

    @Test
    fun `should request posts from typicode API`() {
        mockServer
            .expect(requestTo("$baseUrl/posts?userId=1"))
            .andRespond(withSuccess(TestUtils.loadFileAsString("/typicode/TypicodePosts.json"), APPLICATION_JSON))

        val posts = restClient.findPostsByUserId("1")

        assertThat(posts).hasSize(10)
    }
}