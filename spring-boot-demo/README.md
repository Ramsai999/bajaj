# Spring Boot Demo

This is a simple Spring Boot application that demonstrates the basic structure and functionality of a Spring Boot project.

## Project Structure

```
spring-boot-demo
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── demo
│   │   │               ├── DemoApplication.java
│   │   │               ├── controller
│   │   │               │   └── HelloController.java
│   │   │               ├── service
│   │   │               │   └── HelloService.java
│   │   │               ├── repository
│   │   │               │   └── HelloRepository.java
│   │   │               └── model
│   │   │                   └── Greeting.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│   │           └── (static files)
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── demo
│                       └── DemoApplicationTests.java
├── pom.xml
├── .gitignore
├── .vscode
│   └── launch.json
└── README.md
```

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the project directory**:
   ```
   cd spring-boot-demo
   ```

3. **Build the project**:
   ```
   mvn clean install
   ```

4. **Run the application**:
   ```
   mvn spring-boot:run
   ```

## Usage

Once the application is running, you can access the greeting endpoint:

```
GET http://localhost:8080/greeting
```

This will return a greeting message.

## Dependencies

This project uses Maven for dependency management. The `pom.xml` file contains all the necessary dependencies for the Spring Boot application.

## License

This project is licensed under the MIT License.