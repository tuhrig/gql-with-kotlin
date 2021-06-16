package de.tuhrig.fetchasyncdatafromapi.infrastructure.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.tuhrig.fetchasyncdatafromapi.TestUtils
import de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode.TypicodePost
import de.tuhrig.fetchasyncdatafromapi.infrastructure.typicode.TypicodeUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [JacksonAutoConfiguration::class])
class JacksonConfigurationTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should deserialize an user from typicode`() {

        val expected = TestUtils.loadFileAsString("/typicode/TypicodeUser.json")
        val kotlinObject = objectMapper.readValue<TypicodeUser>(expected)

        with(kotlinObject) {
            assertThat(this.id).isEqualTo("1")
        }
    }

    @Test
    fun `should deserialize a post from typicode`() {

        val expected = TestUtils.loadFileAsString("/typicode/TypicodePost.json")
        val kotlinObject = objectMapper.readValue<TypicodePost>(expected)

        with(kotlinObject) {
            assertThat(this.userId).isEqualTo("1")
            assertThat(this.id).isEqualTo("1")
            assertThat(this.title).startsWith("sunt aut facere repellat provident")
            assertThat(this.body).startsWith("quia et suscipit")
        }
    }
}
