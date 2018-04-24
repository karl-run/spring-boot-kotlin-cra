package run.karl.starter

import com.natpryce.hamkrest.and
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import run.karl.starter.example.ExampleService

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.*

object ExampleServiceSpec : Spek({
    describe("a very good service") {
        val exampleService = ExampleService()

        it("should return the correct value") {
            val value = exampleService.getSomeValue()

            assert.that(value.message, equalTo("Hello World!") and startsWith("Hello"))
        }

        it("should not return the not correct value") {
            val value = exampleService.getSomeValue()

            assert.that(value.message, !equalTo("Something completely different!"))
        }
    }
})
