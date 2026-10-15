package org.example.Ejemplos

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


fun escribirJson() {

    val mapper = jacksonObjectMapper()

    val persona = Persona("Mario", 35)
    val archivo = File("documentos/persona.json")

    mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, persona)

    println("JSON generado correctamente en: ${archivo.absolutePath}")
}
fun leerJson() {

    val mapper = jacksonObjectMapper()
    val archivo = File("documentos/persona.json")

    val persona = mapper.readValue<Persona>(archivo)
    println("Lectura correcta: ${persona.nombre} tiene ${persona.edad} años.")
}

fun main() {
    escribirJson()
    leerJson()
}