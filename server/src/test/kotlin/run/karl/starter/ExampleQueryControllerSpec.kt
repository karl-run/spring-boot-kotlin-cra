package run.karl.starter

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.endsWith
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import run.karl.starter.example.ExampleQueryResolver
import run.karl.starter.example.ExampleResponse
import run.karl.starter.example.ExampleService

object ExampleQueryControllerSpec : Spek({
    describe("a very good controller") {
        val mockedExampleService = mock<ExampleService> {
            on { getSomeValue() } doReturn ExampleResponse("Mocked Message Wahoo!", false)
        }
        val controller = ExampleQueryResolver(exampleService = mockedExampleService)

        it("should return invoke service but return mocked message") {
            val value = controller.getExample()

            assert.that(value.message, equalTo("Mocked Message Wahoo!") and endsWith("Wahoo!"))
        }
    }
})
