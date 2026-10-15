package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun convertirListaXmlAJson(xmlPath: String, jsonPath: String) {
    val jsonMapper = jacksonObjectMapper()
    val xmlMapper = XmlMapper().registerKotlinModule()

    val personas: List<Persona> = xmlMapper.readValue(File(xmlPath))
    jsonMapper.writerWithDefaultPrettyPrinter().writeValue(File(jsonPath), personas)

    println("Conversión XML → JSON completada")
}

fun main() {

    convertirListaXmlAJson("documentos/lista_personas.xml", "documentos/lista_personas.json")
}