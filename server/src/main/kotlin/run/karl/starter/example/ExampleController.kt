package run.karl.starter.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
class ExampleController @Autowired constructor(
        private val exampleService: ExampleService
) {
    @GetMapping("/api/hello", produces = ["application/json"])
    fun example() = exampleService.getSomeValue()
}
