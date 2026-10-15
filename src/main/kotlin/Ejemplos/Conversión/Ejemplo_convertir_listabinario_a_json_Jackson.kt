package org.example.Ejemplos

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val rutaBin = "documentos/lista_personas.dat"
    val rutaJson = "documentos/lista_personas.json"

    // Leer binario estructurado
    val entrada = DataInputStream(FileInputStream(rutaBin))
    val cantidad = entrada.readInt()
    val personas = mutableListOf<Persona>()
    for (i in 0 until cantidad) {
        val nombre = entrada.readUTF()
        val edad = entrada.readInt()
        personas.add(Persona(nombre, edad))
    }
    entrada.close()

    // Convertir a JSON con pretty print usando Jackson
    val mapper = jacksonObjectMapper()
    mapper.writerWithDefaultPrettyPrinter().writeValue(File(rutaJson), personas)

    // Crear carpeta si no existe
    Files.createDirectories(Paths.get(rutaJson).parent)

    println("✅ Lista de personas convertida a JSON con Jackson:")
    println(mapper.writeValueAsString(personas))
}