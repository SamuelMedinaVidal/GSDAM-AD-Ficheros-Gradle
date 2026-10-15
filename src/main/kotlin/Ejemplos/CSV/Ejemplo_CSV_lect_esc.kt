package org.example.Ejemplos

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


fun main() {
    val ruta = Paths.get("documentos/alumnos.csv")

    // 1. Crear contenido CSV (con cabecera)
    val lineas = listOf(
        "nombre;nota",     // cabecera
        "Lucía;9",
        "Carlos;8",
        "Elena;10"
    )

    // 2. Escribir el archivo
    Files.write(
        ruta,
        lineas,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )

    println("Archivo CSV creado: ${ruta.toAbsolutePath()}")

    // 3. Leer el archivo y convertir a objetos Alumno
    val lineasLeidas = Files.readAllLines(ruta)
    val alumnos = mutableListOf<Alumno>()

    for (i in lineasLeidas.indices) {

        // Saltamos la cabecera (línea 0)
        if (i == 0) continue

        val linea = lineasLeidas[i]
        val partes = linea.split(";")

        if (partes.size == 2) {
            val nombre = partes[0]
            val nota = partes[1].toInt()

            val alumno = Alumno(nombre, nota)
            alumnos.add(alumno)
        } else {
            println("Línea mal formada: $linea")
        }
    }

    // 4. Usar los objetos
    println("\nListado de alumnos:")
    for (alumno in alumnos) {
        println("Alumno: ${alumno.nombre}, Nota: ${alumno.nota}")
    }
}