package org.acme.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.runtime.annotations.RegisterForReflection
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns

@Path("/greeting")
class GreetingResource {

    @Inject
    lateinit var objectMapper: ObjectMapper


    /**
     * This function causes the test to fail, even though the test is written against the other endpoint.
     * Comment out this function (through line 33), and the test will pass
     *
     * This leads me to believe that the presence of the coroutine classes in the native image somehow corrupts
     * access Kotlin builtins.
     */
    @Path("/coroutines")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun helloCoroutines() = runBlocking {
        jacksonSerialization("coroutines")
    }
    // Comment out to here

    @Path("/normal")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun helloNormal() = jacksonSerialization("normal")

    fun jacksonSerialization(name: String): String {
        val json = objectMapper.writeValueAsString(Foo(name))
        val foo = objectMapper.readValue(json, Foo::class.java)
        println(foo)
        return foo.toString()
    }
}


@RegisterForReflection
data class Foo(var name: String = "")


/**
 * Try to force Graal to leave these on the classpath
 */
@RegisterForReflection(
    targets = [
        KotlinBuiltIns::class,
        JvmBuiltIns::class,
        DefaultBuiltIns::class
    ]
)
class GraalReflectionConfig