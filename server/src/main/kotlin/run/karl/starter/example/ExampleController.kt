package run.karl.starter.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    @GetMapping("hello")
    fun example() = "Hello World!"
}
