object Versions {
    /* **********************************************************************************
     * Alphabetised list of versions for dependencies
     * **********************************************************************************/
    val hibernate_types = "2.4.3"
    val jackson = "2.10.3"
    val jackson_jsr310_java8_datetime = "2.10.3"
    val jackson_jdk8_module = "2.10.3"
    val lombok = "1.18.12"

    /////////////////////////////////////////////////////////////
    //                      Axon specific
    /////////////////////////////////////////////////////////////
    // 4.2.1 - works when making query to get multiple instances (a list) as a return type
    // 4.3   - does not work
    // 4.3.1 - does not work
    val axon = "4.3"
    /////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    //                      Spring specific
    /////////////////////////////////////////////////////////////
    val spring_boot = "2.2.6.RELEASE"
    val springdoc_openapi_ui = "1.2.29"
    /////////////////////////////////////////////////////////////
}

/**
 * Set dependency strings
 */
object Dependencies {
    /////////////////////////////////////////////////////////////
    //                      Axon specific
    /////////////////////////////////////////////////////////////
    val axon_spring_boot_starter = "org.axonframework:axon-spring-boot-starter:${Versions.axon}"
    /////////////////////////////////////////////////////////////

    val hibernate_types = "com.vladmihalcea:hibernate-types-52:${Versions.hibernate_types}"
    val jackson_core = "com.fasterxml.jackson.core:jackson-core:${Versions.jackson}"
    val jackson_databind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
    val jackson_annotation = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
    val jackson_jsr310_java8_datetime = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson_jsr310_java8_datetime}"
    val jackson_jdk8_module = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson_jdk8_module}"

    val lombok = "org.projectlombok:lombok:${Versions.lombok}"
}

/**
 * The spring dependencies do not require a version string (unless they are for a BOM) because they
 * will use whatever version is specified in the bill of materials or by the
 * 'io.spring.dependency-management' plugin.
 */
object SpringDependencies {
    // Spring Boot
    val spring_boot_dependency_bom = "org.springframework.boot:spring-boot-dependencies:${Versions.spring_boot}"
    val spring_boot_starter = "org.springframework.boot:spring-boot-starter"
    val spring_boot_starter_data_jpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    val spring_boot_starter_log4j2 = "org.springframework.boot:spring-boot-starter-log4j2"
    val spring_boot_starter_web = "org.springframework.boot:spring-boot-starter-web"

    // Springdoc is not a Spring project - it is community supported.
    // It generates API documentation and a swagger UI for your app at runtime.
    // See: https://github.com/springdoc/springdoc-openapi
    val springdoc_openapi_ui = "org.springdoc:springdoc-openapi-ui:${Versions.springdoc_openapi_ui}"

    // For persistence. The versions will be taken to match based on the version of the
    // org.springframework.boot plugin e.g.:
    // id "org.springframework.boot" version '2.1.4.RELEASE'
    // will mean the versions tested for spring boot '2.1.4.RELEASE' will be pulled in
    val spring_postgresql_jdbc = "org.postgresql:postgresql"
    val spring_javax_persistence_api = "javax.persistence:javax.persistence-api"
}