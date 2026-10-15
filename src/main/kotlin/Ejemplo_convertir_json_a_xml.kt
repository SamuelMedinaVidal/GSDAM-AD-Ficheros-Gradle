package org.example

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun convertirJsonAXml(jsonPath: String, xmlPath: String) {
    val xmlMapper = XmlMapper().registerKotlinModule()
    val jsonMapper = jacksonObjectMapper()

    //Leer JSON y convertirlo a un objeto Persona
    val persona = jsonMapper.readValue<Persona>(File(jsonPath))

    // Escribir el objeto Persona en formato XML
    xmlMapper.writerWithDefaultPrettyPrinter().writeValue(File(xmlPath), persona)

    println("Conversión JSON → XML completada")
}

fun main() {
    convertirJsonAXml("documentos/persona.json", "documentos/persona.xml")

}