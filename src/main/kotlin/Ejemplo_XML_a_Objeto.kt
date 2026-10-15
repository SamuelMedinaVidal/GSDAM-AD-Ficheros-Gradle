package org.example

import org.jdom2.input.SAXBuilder
import java.io.File


fun main() {

    //Crea una lista mutable de tipo Alumno.
    val alumnos = mutableListOf<Alumno>()

    val archivo = File("documentos/alumnos.xml")
    val builder = SAXBuilder()
    val documento = builder.build(archivo)
    val raiz = documento.rootElement

    val listaAlumnos = raiz.getChildren("alumno")

    //Por cada nodo <alumno> del XML, crea un objeto Alumno con sus atributos.
    for (elemento in listaAlumnos) {
        val nombre = elemento.getChildText("nombre")
        val nota = elemento.getChildText("nota").toIntOrNull() ?: 0
        alumnos.add(Alumno(nombre, nota))
    }

    // Mostrar los objetos
    alumnos.forEach { println(it) }
}