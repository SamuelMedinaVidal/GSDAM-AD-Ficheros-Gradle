package org.example

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun convertirXmlAJson(xmlPath: String, jsonPath: String) {
    val xmlMapper = XmlMapper().registerKotlinModule()
    val jsonMapper = jacksonObjectMapper()


    val persona = xmlMapper.readValue<Persona>(File(xmlPath))
    jsonMapper.writerWithDefaultPrettyPrinter().writeValue(File(jsonPath), persona)

    println("Conversión XML → JSON completada")
}

fun main() {

    convertirXmlAJson("documentos/persona.xml", "documentos/persona.json")

}