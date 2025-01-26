plugins {
    kotlin("jvm") version "1.9.0"
    id("antlr")
    antlr
    application
}

group = "me.pazma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.0")
    implementation("org.antlr:antlr4-runtime:4.13.0")
    testImplementation("org.antlr:antlr4-runtime:4.13.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-core:5.3.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.generateGrammarSource {
    // set output directory to some arbitrary location in `/build` directory.
    // by convention `/build/generated/sources/main/java/<generator name>` is often used
    outputDirectory = file("${project.layout.buildDirectory}/generated/sources/main/java/antlr")

    // pass -package to make generator put code in not default space
    arguments = listOf("-visitor")
}

tasks.generateTestGrammarSource {
    outputDirectory = file("${layout.buildDirectory}/generated-src/antlr/test")
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

sourceSets {
    main {
        java {
            // telling that output generateGrammarSource should be part of main source set
            // actuall passed value will be equal to `outputDirectory` that we configured above
            srcDir(tasks.generateGrammarSource)
        }
    }
    test {
        // Instead of referencing the folder directly, reference the task
        // This automatically tells Gradle that compileTestKotlin depends on generateTestGrammarSource
        java.srcDir(tasks.generateTestGrammarSource)
    }
}