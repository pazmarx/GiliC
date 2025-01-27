plugins {
    kotlin("jvm") version "1.9.0"
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
    outputDirectory = (layout.buildDirectory.dir("generated/sources/antlr/main").get().asFile)
    arguments = listOf("-visitor")
}

tasks.generateTestGrammarSource {
    outputDirectory = (layout.buildDirectory.dir("generated/sources/antlr/test").get().asFile)
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.compileTestKotlin {
    dependsOn(tasks.generateTestGrammarSource)
}


kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

sourceSets {
    main {
        java.srcDir(tasks.generateGrammarSource.map { it.outputDirectory })
    }
    test {
        java.srcDir(tasks.generateTestGrammarSource.map { it.outputDirectory })
    }
}
