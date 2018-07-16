package run.karl.starter.example

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ExampleQueryResolver @Autowired constructor(
        private val exampleService: ExampleService
) : GraphQLQueryResolver {
    fun getExample() = exampleService.getSomeValue()
}
