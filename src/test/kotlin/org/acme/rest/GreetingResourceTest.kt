package org.acme.rest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
open class GreetingResourceTest {

    @Test
    fun testHelloNormalEndpoint() {
        given()
            .`when`().get("/greeting/normal")
            .then()
            .statusCode(200)
            .body(`is`(Foo("normal").toString()))
    }

}