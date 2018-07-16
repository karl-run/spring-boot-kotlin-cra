package run.karl.starter.example

import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class ExampleResolver : GraphQLResolver<ExampleResponse> {
    fun resolveDeep(exampleResponse: ExampleResponse): String {
        return "This value has been resolved, but only if you ask for it!"
    }
}
