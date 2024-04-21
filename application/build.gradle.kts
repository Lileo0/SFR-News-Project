import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "7.5.0"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
}

group = "com.news"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        java.srcDir("$buildDir/generated/src/main/java")
    }
}

repositories {
    mavenCentral()
}
tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("news_delegate_api") {
    generatorName.set("spring")
    inputSpec.set("$projectDir/src/main/resources/api/NewsApi.yaml".replace("\\", "/"))
    outputDir.set("$buildDir/generated")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)
    apiPackage.set("com.news.application.api")
    modelPackage.set("com.news.application.api.model")
    modelNameSuffix.set("ApiDto")

    configOptions.put("useJakartaEe", "true")
    configOptions.put("useSpringBoot3", "true")
    configOptions.put("delegatePattern", "true")
    configOptions.put("openApiNullable", "false")
}
tasks.withType<KotlinCompile> {
    dependsOn(tasks["news_delegate_api"])
    kotlinOptions {
        jvmTarget = "17"
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
    // https://mvnrepository.com/artifact/io.swagger.core.v3/swagger-annotations
    implementation("io.swagger.core.v3:swagger-annotations:2.2.21")

    //implementation("org.openapitools:openapi-generator-gradle-plugin:7.2.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka
    implementation("org.springframework.kafka:spring-kafka:3.1.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}