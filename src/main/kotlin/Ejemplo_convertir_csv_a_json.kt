package org.example

import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVParserBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.io.FileReader


fun main() {
    val rutaCSV = "documentos/alumnos.csv"
    val rutaJSON = "documentos/alumnos.json"

    val reader = CSVReaderBuilder(FileReader(rutaCSV))
        .withCSVParser(CSVParserBuilder().withSeparator(';').build())
        .withSkipLines(1)
        .build()

    val registros = reader.readAll()

    //lista alumnos para guardar los objetos Alumno
    val alumnos = mutableListOf<Alumno>()

    for (campos in registros) {
        val nombre = campos[0]
        val nota = campos[1].toInt()

        //  Cada línea del CSV se transforma en un objeto
        val alumno = Alumno(nombre, nota)
        //Todos los objetos se guardan en la lista alumnos
        alumnos.add(alumno)
    }

    reader.close()


    val mapper = jacksonObjectMapper()
    mapper.writerWithDefaultPrettyPrinter().writeValue(File(rutaJSON), alumnos)

    println("✅ Conversión CSV → JSON completada: $rutaJSON")
}