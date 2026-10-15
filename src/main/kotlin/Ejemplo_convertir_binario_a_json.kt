package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val rutaBin = "documentos/persona.dat"
    val rutaJson = "documentos/persona.json"

    // Leer binario estructurado
    val entrada = DataInputStream(FileInputStream(rutaBin))
    val nombre = entrada.readUTF()
    val edad = entrada.readInt()
    entrada.close()

    // Crear objeto
    val persona = Persona(nombre, edad)

    // Convertir a JSON con pretty print
    val json = Json { prettyPrint = true }.encodeToString(persona)

    // Crear carpeta si no existe
    Files.createDirectories(Paths.get(rutaJson).parent)

    // Escribir JSON en archivo
    File(rutaJson).writeText(json)

    println("✅ Binario estructurado convertido a JSON:")
    println(json)
}