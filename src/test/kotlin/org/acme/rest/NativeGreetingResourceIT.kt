package org.acme.rest

import io.quarkus.test.junit.NativeImageTest

@NativeImageTest
open class NativeGreetingResourceIT : GreetingResourceTest()