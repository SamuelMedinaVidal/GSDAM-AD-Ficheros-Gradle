package org.example.Ejemplos

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Paths

fun main() {
    val ruta = Paths.get("documentos/alumnos.csv").toString()

    val writer = CSVWriter(FileWriter(ruta))

    writer.writeNext(arrayOf("nombre", "nota"))
    writer.writeNext(arrayOf("Lucía", "9"))
    writer.writeNext(arrayOf("Carlos", "8"))
    writer.writeNext(arrayOf("Elena", "10"))
    writer.close()

    println("Archivo CSV creado: $ruta")

    val alumnos = mutableListOf<Alumno>()

    val reader = CSVReader(FileReader(ruta))
    val filas = reader.readAll()

    for (i in filas.indices) {

        // Saltamos la cabecera
        if (i == 0) continue

        val fila = filas[i]

        if (fila.size == 2) {
            val nombre = fila[0]
            val nota = fila[1].toInt()

            alumnos.add(Alumno(nombre, nota))
        } else {
            println("Línea mal formada: ${fila.joinToString(";")}")
        }
    }

    reader.close()

    println("\nListado de alumnos:")
    for (alumno in alumnos) {
        println("Alumno: ${alumno.nombre}, Nota: ${alumno.nota}")
    }
}