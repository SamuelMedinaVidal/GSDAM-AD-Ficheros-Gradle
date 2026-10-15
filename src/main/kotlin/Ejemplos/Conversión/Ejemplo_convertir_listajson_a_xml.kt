package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule


fun convertirListaJsonAXml(jsonPath: String, xmlPath: String) {

    val jsonMapper = jacksonObjectMapper()
    val xmlMapper = XmlMapper().registerKotlinModule()

    // Leer JSON y convertirlo a lista de objetos Persona
    val personas: List<Persona> = jsonMapper.readValue(File(jsonPath))

    // Escribir el XML
    xmlMapper.writerWithDefaultPrettyPrinter().writeValue(File(xmlPath), personas)

    println("Conversión JSON → XML completada")
}

fun main() {

    convertirListaJsonAXml("documentos/lista_personas.json", "documentos/lista_personas.xml")

}