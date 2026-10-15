package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun convertirListaXmlAJsonNodo(xmlPath: String, jsonPath: String) {
    val jsonMapper = jacksonObjectMapper()
    val xmlMapper = XmlMapper().registerKotlinModule()

    // Lee el XML y lo convierte a un objeto de tipo ListaPersonas
    // Aquí utilizamos la clase contenedora para representar el nodo raíz del XML
    val lista: ListaPersonas = xmlMapper.readValue(File(xmlPath))

    // Extrae la lista de objetos Persona desde la clase contenedora
    val personas = lista.persona

    // Escribe la lista de personas en formato JSON
    // El JSON generado será un array de objetos
    jsonMapper.writerWithDefaultPrettyPrinter()
        .writeValue(File(jsonPath), personas)

    println("Conversión XML → JSON completada")
}

fun main() {

    convertirListaXmlAJsonNodo("documentos/lista_personas_nodo.xml", "documentos/lista_personas.json")
}