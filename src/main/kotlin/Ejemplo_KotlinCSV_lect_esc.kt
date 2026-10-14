package org.example

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.nio.file.Paths


fun main() {
    val ruta = Paths.get("documentos/alumnos.csv").toString()

    // Escribir con cabecera
    csvWriter().open(ruta) {
        writeRow(listOf("nombre", "nota"))
        writeRow(listOf("Lucía", "9"))
        writeRow(listOf("Carlos", "8"))
        writeRow(listOf("Elena", "10"))
    }

    // Leer con cabecera (como Map)
    val filas: List<Map<String, String>> = csvReader().readAllWithHeader(File(ruta))

    val alumnos = filas.mapNotNull { fila ->
        val nombre = fila["nombre"]
        val notaStr = fila["nota"]
        if (nombre != null && notaStr != null) Alumno(nombre, notaStr.toInt()) else null
    }

    println("Listado de alumnos:")
    alumnos.forEach { println("Alumno: ${it.nombre}, Nota: ${it.nota}") }
}