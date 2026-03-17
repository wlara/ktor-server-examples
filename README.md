# ktor-server-examples

Standalone Ktor server examples built with Gradle, from a minimal hello-world service to a more feature-rich REST API sample.

## What is in this repository

| Module | Purpose                                                                                                              |
| --- |----------------------------------------------------------------------------------------------------------------------|
| `hello-world-server` | Smallest possible Ktor server with one route, using the CIO engine                                                   |
| `basic-server` | REST API example using the CIO engine, with Koin DI, JSON serialization, logging, static resources, and status pages |
| `advanced-server` | Will include everything from `basic-server`, plus a real database using Exposed/PostgreSQL and JWT authentication (coming soon). |

## Prerequisites

- JDK 21 (modules set `jvmToolchain(21)`)
- macOS/Linux/Windows
- Use the included Gradle wrapper (`./gradlew` or `gradlew.bat`)

## Quick start

Run the hello-world module:

```zsh
./gradlew :hello-world-server:run
```

Then call:

```zsh
curl http://localhost:8080/api/v1/hello
```

## Run modules

```zsh
./gradlew :hello-world-server:run
./gradlew :basic-server:run
```

Both modules default to port `8080`, so stop one before starting the other.

## Useful API calls (`basic-server`)

```zsh
curl http://localhost:8080/api/v1/users
curl http://localhost:8080/api/v1/users/<user-id>
curl http://localhost:8080/api/v1/products
curl "http://localhost:8080/api/v1/products?query=phone"
```

## Build and test

```zsh
./gradlew clean build
./gradlew test
```

Build artifacts are generated under each module's `build/libs` directory.

## Project layout

- `hello-world-server/`: minimal embedded CIO example
- `basic-server/`: multi-feature API sample
- `advanced-server/`: advanced sample scaffold
- `gradle/libs.versions.toml`: centralized versions/plugins

## Troubleshooting

- **Port already in use**: free port `8080` or change module config.
- **Java version mismatch**: check with `java -version` and use JDK 21.
- **Gradle oddities**: run `./gradlew --stop` and retry.

## License

See [LICENSE](LICENSE).
