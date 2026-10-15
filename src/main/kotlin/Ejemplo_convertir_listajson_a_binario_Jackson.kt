package org.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    val rutaJson = "documentos/lista_personas.json"
    val rutaBin = "documentos/lista_personas.dat"

    // Leer JSON con Jackson
    val mapper = jacksonObjectMapper()
    val personas: List<Persona> = mapper.readValue(File(rutaJson))

    // Crear carpeta si no existe
    Files.createDirectories(Paths.get(rutaBin).parent)

    // Escribir como binario estructurado
    val salida = DataOutputStream(FileOutputStream(rutaBin))
    salida.writeInt(personas.size)
    for (persona in personas) {
        salida.writeUTF(persona.nombre)
        salida.writeInt(persona.edad)
    }
    salida.close()

    println("✅ Lista de personas guardada como binario estructurado en: $rutaBin")

    // Leer el binario estructurado
    val entrada = DataInputStream(FileInputStream(rutaBin))
    val cantidad = entrada.readInt()
    for (i in 0 until cantidad) {
        val nombre = entrada.readUTF()
        val edad = entrada.readInt()

        println("Nombre: $nombre, Edad: $edad")
    }
    entrada.close()
}