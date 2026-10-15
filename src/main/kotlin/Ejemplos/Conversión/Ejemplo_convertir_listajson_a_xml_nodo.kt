package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun convertirListaJsonAXml_nodo(jsonPath: String, xmlPath: String) {
    val jsonMapper = jacksonObjectMapper()
    val xmlMapper = XmlMapper().registerKotlinModule()

    // Leer JSON y convertirlo a lista de objetos Persona
    val personas: List<Persona> = jsonMapper.readValue(File(jsonPath))

    // Envolver la lista en la clase contenedora para XML
    val listaPersonas = ListaPersonas(personas)

    // Escribir el XML
    xmlMapper.writerWithDefaultPrettyPrinter()
        .writeValue(File(xmlPath), listaPersonas)

    println("✅ Conversión JSON → XML completada")
}

fun main() {
    convertirListaJsonAXml_nodo(
        "documentos/lista_personas.json",
        "documentos/lista_personas_nodo.xml"
    )
}