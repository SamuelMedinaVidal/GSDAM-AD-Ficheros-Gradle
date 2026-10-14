package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.nio.file.Files
import java.nio.file.Paths
import java.io.IOException
import java.nio.file.Files.readString

fun escribirJSON() {

    val ruta = Paths.get("documentos/persona.json")
    val persona = Persona("Lucía", 28)
    try {

        // Convertir a String con formato bonito
        val jsonString = Json { prettyPrint = true }.encodeToString(persona)

        // Crear carpeta si no existe
        Files.createDirectories(ruta.parent)

        // Escribir JSON en archivo
        Files.writeString(ruta, jsonString)

        println("Archivo JSON creado en: ${ruta.toAbsolutePath()}")
        println("Contenido:\n$jsonString")

    } catch (e: IOException) {
        println("⚠️ Error de entrada/salida: ${e.message}")
    } catch (e: Exception) {
        println("⚠️ Error inesperado: ${e.message}")
    }
}


fun leerJSON(){
    val rutaEntrada = Paths.get("documentos/persona.json")

    // --- Lectura segura ---
    try {
        if (!Files.exists(rutaEntrada)) {
            println("El archivo no existe: $rutaEntrada")
            return
        }

        val contenidoJson = readString(rutaEntrada)
        val persona = Json.decodeFromString<Persona>(contenidoJson)
        println("Lectura correcta: $persona")


    } catch (e: IOException) {
        println("Error de E/S: ${e.message}")
    }
}


fun main() {
    escribirJSON()
    leerJSON()

}