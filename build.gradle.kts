plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    application
}


repositories {
    mavenCentral()
}

dependencies {
    // Kotlin estándar
    implementation(kotlin("stdlib"))

    // Serialización JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // OpenCSV para CSV
    implementation("com.opencsv:opencsv:5.9")

    //Kotlin-CSV
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.1")

    // librería JDOM2
    implementation("org.jdom:jdom2:2.0.6")

    // librerias jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.0")

}