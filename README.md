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

If you already have a Spring Boot project running with Kotlin from [Spring Initializr](https://start.spring.io/), and you would like to look at what this template has done instead, I'll go through the following steps:

1. Serving a SPA (single page application) from Spring Boot 2
2. Building and bundling the web-app into the server artifact
3. Testing Spring Boot with Spek, testing dependency injected fields.

### Serving a SPA (single page application) from Spring Boot 2
TODO

### Building and bundling the web-app into the server artifact
TODO

### Testing Spring Boot with Spek, testing dependency injected fields.
TODO
