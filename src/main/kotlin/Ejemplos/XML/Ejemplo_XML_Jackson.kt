package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun escribirXml() {
    val xmlMapper = XmlMapper().registerModule(KotlinModule.Builder().build())

    val lista = ListaAlumnos(
        listOf(
            Alumno("Lucía", 8),
            Alumno("Carlos", 6),
            Alumno("María", 10)
        )
    )

    val archivo = File("documentos/alumnos.xml")
    xmlMapper.writerWithDefaultPrettyPrinter().writeValue(archivo, lista)

    println("XML escrito correctamente en: ${archivo.absolutePath}")
}


fun leerXml() {
    val xmlMapper = XmlMapper().registerKotlinModule()

    val archivo = File("documentos/alumnos.xml")

    //val lista = xmlMapper.readValue(archivo, ListaAlumnos::class.java)
    val lista=xmlMapper.readValue<ListaAlumnos>(archivo)

    println("Lectura correcta:")

    lista.alumno.forEach {
        println("${it.nombre} tiene un ${it.nota}")
    }
}

fun main() {
    escribirXml()
    leerXml()
}