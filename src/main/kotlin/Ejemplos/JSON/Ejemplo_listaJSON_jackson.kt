package org.example.Ejemplos

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


fun escribirListaJson() {

    val mapper = jacksonObjectMapper()

    val personas = listOf(
        Persona("Lucía", 28),
        Persona("Pepe", 30),
        Persona("Ana", 50),
        Persona("Juan", 12)
    )
    val archivo = File("documentos/lista_personas.json")

    mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, personas)

    println("JSON generado correctamente en: ${archivo.absolutePath}")
}

fun leerListaJson() {

    val mapper = jacksonObjectMapper()
    val archivo = File("documentos/lista_personas.json")

    val lista=mapper.readValue<List<Persona>>(archivo)


    for (p in lista) {
        println("${p.nombre} tiene ${p.edad} años.")
    }

}

fun main() {
    escribirListaJson()
    leerListaJson()
}