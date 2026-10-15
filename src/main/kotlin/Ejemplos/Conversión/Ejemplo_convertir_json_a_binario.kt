package org.example.Ejemplos

import kotlinx.serialization.json.Json
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    val rutaJson = "documentos/persona.json"
    val rutaBin = "documentos/persona.dat"

    // Leer JSON
    val contenido = File(rutaJson).readText()
    val persona = Json.decodeFromString<Persona>(contenido)

    // Crear carpeta si no existe
    Files.createDirectories(Paths.get(rutaBin).parent)

    // Escribir como binario estructurado
    val salida = DataOutputStream(FileOutputStream(rutaBin))
    salida.writeUTF(persona.nombre)
    salida.writeInt(persona.edad)
    salida.close()

    println("✅ .Persona guardada como binario estructurado en: $rutaBin")

    //Leer el binario estructurado
    val entrada = DataInputStream(FileInputStream(rutaBin))
    val nombre = entrada.readUTF()
    val edad = entrada.readInt()
    entrada.close()

    println("📄 Persona leída del binario:")
    println("Nombre: $nombre, Edad: $edad")

}