# Spring Boot with Kotlin & bundled web with create-react-app

This is an example of a Spring Boot 2 server built with Kotlin, that automatically bundles and serves a [create-react-app](https://github.com/facebook/create-react-app) web-app when build.

The modules are structured in a way that makes it easy to use IntelliJ IDEA for both modules, or if you prefer you can open them modules individually with your favourite tools.

Uses [Spek](http://spekframework.org/) for testing in Kotlin.

## Development

When developing you want to use the dev server that create-react-app provides. All XHR requests from the web-app are proxied to the backend. A typical development workflow will involve running the Spring Boot server and the create-react-app server separately.

1. Run Spring Boot server, either through `./gradlew bootRun` in the `server`-folder or through IntelliJ's default run configuration.
2. Run create-react-app dev server by running `yarn start` in the `web`-folder.

Open `http://localhost:3000` (create-react-app dev server) in your browser.

Note that any requests made to `http://localhost:3000/api/...` are proxied directly to Spring Boot that is running on `http://localhost:8082/api/...`. To make any requests directly to the API use `8082`, but when doing XHR requests from the web app use relative paths with `/api`.

## Build

To produce a single build artifact, with spring boot that is a single `.jar`-file, simply run `./gradlew build` in the `server`-folder.

This will only succeed if all your tests both in server and web are green, and both build fine.

Run the compiled artifact:

`java -jar build/libs/server-0.0.1-SNAPSHOT.jar`

## Use this template

To use this as a template do the following:

1. Pull the repository
2. Delete the `.git` folder
3. In `server/build.gradle` change `group = 'run.karl'` to `group = 'your.group'`
4. Select the folder `run.karl.starter` in IntelliJ, hit Shift+F6 (rename), choose `RENAME PACKAGE` and rename it to the package of your choice.
    Make sure the test folder was renamed as well.
5. Run `./gradlew build` in the `server`-folder to verify that everything is working.

The template is now clean and ready to use.

## Learn from this template

I wrote a [full, beginner-friendly blog post](https://karl.run/2018/05/07/kotlin-spring-boot-react/) on this. Some of the more important points a bit more terse:

If you already have a Spring Boot project running with Kotlin from [Spring Initializr](https://start.spring.io/), and you would like to look at what this template has done instead, I'll go through the following steps:

1. Serving a SPA (single page application) from Spring Boot 2
2. Building and bundling the web-app into the server artifact
3. Testing Spring Boot with Spek, testing dependency injected fields.

### Serving a SPA (single page application) from Spring Boot 2
SPAs handle their own routing, which means that the Spring Boot server needs to route any request to index.html, but keep the path and query-params intact. It should also look completely transparent to the user that any redirect has happened.

This can be tricky to do in Spring Boot without having to define every single static file you want to serve. The simple solution is to configure an error page to redirect to `/` . This is configured in [WebConfig.kt](https://github.com/karl-run/spring-boot-kotlin-cra/blob/master/server/src/main/kotlin/run/karl/starter/WebConfig.kt#L14-L19), by simply creating a bean that adds the error page that redirects to root on any text/html 404 request.

```kotlin
@Bean
fun webServerFactory(): ConfigurableServletWebServerFactory {
    val factory = TomcatServletWebServerFactory()
    factory.errorPages.add(ErrorPage(HttpStatus.NOT_FOUND, "/"))
    return factory
}
```

### Building and bundling the web-app into the server artifact

Use [copyfiles](https://www.npmjs.com/package/copyfiles) and [cross-env](https://www.npmjs.com/package/cross-env).

```json
    "build:gradle": "cross-env CI=true npm run test && npm run build",
    "postbuild": "copyfiles -u 1 build/**/* ../server/src/main/resources/static"
```

Then execute `build:gradle` from the gradle build with [gradle-node-plugin](https://github.com/srs/gradle-node-plugin).

Add it to your buildscript dependencies: `classpath("com.moowork.gradle:gradle-node-plugin:1.2.0")`

Apply the plugin: `apply plugin: 'com.moowork.node'`

Create tasks for installing dependencies and running `build:gradle` and chain them into the gradle `build` target.

```groovy
task installDependencies(type: YarnTask) {
    execOverrides {
        it.workingDir = '../web'
    }
}

task buildWeb(type: YarnTask) {
    args = ['build:gradle']
    execOverrides {
        it.workingDir = '../web'
    }
}

buildWeb.dependsOn installDependencies
build.dependsOn buildWeb
```

Now `./gradlew build` builds your web app, moves it to the static folder and then builds the Spring Boot artifact. Ship it!

### Testing Spring Boot with Spek, testing dependency injected fields.

I found the easiest way to test the Spring Boot Kotlin code with dependency injection is to use Spek, hamkrest, mockito-kotlin 
and use constructor autowiring.

````groovy
    testCompile('org.springframework.boot:spring-boot-starter-test')
    testCompile('org.jetbrains.spek:spek-api:1.1.5')
    testCompile('com.natpryce:hamkrest:1.4.2.2')
    testCompile 'com.nhaarman:mockito-kotlin:1.5.0'
    testRuntime('org.jetbrains.spek:spek-junit-platform-engine:1.1.5')
````

Controller and service example:

```kotlin
/* src/main/run/karl/starter/example/ExampleService.kt */

@Service
class ExampleService {
    data class ExampleResponse(val message: String)

    fun getSomeValue() = ExampleResponse("Hello Service!")
}
```

```kotlin
/* src/main/run/karl/starter/example/ExampleController.kt */

@RestController()
class ExampleController @Autowired constructor(
        private val exampleService: ExampleService
) {
    @GetMapping("/api/hello", produces = ["application/json"])
    fun example() = exampleService.getSomeValue()
}
```

Note the constructor-autowiring in the controller. To test mock the service:

```kotlin
object ExampleControllerSpec : Spek({
    describe("a very good controller") {
        // Create a mock of ExampleService
        val mockedExampleService = mock<ExampleService> {
            /* Define that when getSomeValue is invoked, return this
               value instead of executing the original code */
            on { getSomeValue() } doReturn ExampleService.ExampleResponse("Mocked Message Wahoo!")
        }
        /* Specify that we want to use our mocked
           ExampleService by passing it in as a named parameter */
        val controller = ExampleController(exampleService = mockedExampleService)

        it("should return invoke service but return mocked message") {
            val value = controller.example()

            assert.that(value.message, equalTo("Mocked Message Wahoo!") and endsWith("Wahoo!"))
        }
    }
})
```

## Contribute

I'm not a master of gradle nor Kotlin, if you see something strange feel free to open an issue or submit a pull request.