package org.example.Ejemplos

import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import java.io.File



data class Alumno(
    val nombre: String,
    val nota: Int
)


fun main() {
    // Lista de alumnos
    val alumnos = listOf(
        Alumno("Lucía", 8),
        Alumno("Carlos", 6),
        Alumno("Elena", 10)
    )

    // Crear elemento raíz <alumnos>
    val raiz = Element("alumnos")

    // Añadir cada alumno como <alumno>
    for (alumno in alumnos) {
        val alumnoElement = Element("alumno")
        alumnoElement.addContent(Element("nombre").setText(alumno.nombre))
        alumnoElement.addContent(Element("nota").setText(alumno.nota.toString()))
        raiz.addContent(alumnoElement)
    }

    // Crear el documento XML
    val documento = Document(raiz)

    // Escribir en archivo con formato bonito
    val salida = XMLOutputter()
    salida.format = Format.getPrettyFormat()
    salida.output(documento, File("documentos/alumnos.xml").outputStream())

    println("Archivo XML creado con éxito.")
}

