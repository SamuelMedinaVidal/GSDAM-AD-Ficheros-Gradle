package Ejercicio3

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVWriter
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

data class ArchivoReporte(
    val nombre: String,
    val tipo: String,
    val tamano: Long
)

data class Reporte(
    val archivo: List<ArchivoReporte>
)

class GestorExportacion {

    // Función principal que coordina todo el proceso
    fun exportarReporte(currentPath: Path, mostrarOcultos: Boolean) {
        val exportDirPath = currentPath.resolve("exportar")

        try {
            // Creamos el directorio 'exportar' en caso de no existir
            if (!Files.exists(exportDirPath)) {
                Files.createDirectory(exportDirPath)
            }

            val csvPath = exportDirPath.resolve("reporte.csv")
            val jsonPath = exportDirPath.resolve("reporte.json")
            val xmlPath = exportDirPath.resolve("reporte.xml")

            // Generamos el CSV desde el sistema de archivos
            generarCSV(currentPath, csvPath, mostrarOcultos)

            // Leemos el CSV y lo pasamos a memoria
            val listaReporte = leerCSV(csvPath)

            // Generamos JSON y XML desde la lista en memoria
            generarJSON(listaReporte, jsonPath)
            generarXML(listaReporte, xmlPath)

            println("Reportes generados correctamente en la carpeta 'exportar'.")

        } catch (e: IOException) {
            println("Error durante la exportación: ${e.message}")
        }
    }

    private fun generarCSV(origen: Path, destinoCSV: Path, mostrarOcultos: Boolean) {
        val writer = CSVWriter(FileWriter(destinoCSV.toFile()), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)

        // Escribimos la cabecera
        writer.writeNext(arrayOf("Nombre", "Tipo", "Tamaño"))

        // Listamos los archivos del directorio actual, no subdirectorios
        Files.list(origen).use { stream ->
            stream.filter { mostrarOcultos || !it.fileName.toString().startsWith(".") }
                .forEach { path ->
                    val atributos = Files.readAttributes(path, BasicFileAttributes::class.java)
                    val tipo = if (atributos.isDirectory) "Directorio" else "Archivo"

                    // Escribimos la fila
                    writer.writeNext(arrayOf(path.fileName.toString(), tipo, atributos.size().toString()))
                }
        }
        writer.close()
    }

    private fun leerCSV(csvPath: Path): List<ArchivoReporte> {
        val reader = CSVReaderBuilder(FileReader(csvPath.toFile()))
            .withSkipLines(1) // Error resuelto: se salta la cabecera para no leerla como si fuera un archivo
            .build()

        val registros = reader.readAll()
        val lista = mutableListOf<ArchivoReporte>()

        // Mapeamos los textos del CSV al objeto ArchivoReporte de Kotlin
        for (campos in registros) {
            val nombre = campos[0]
            val tipo = campos[1]
            val tamano = campos[2].toLong()

            lista.add(ArchivoReporte(nombre, tipo, tamano))
        }

        reader.close()
        return lista
    }

    private fun generarJSON(lista: List<ArchivoReporte>, jsonPath: Path) {
        val mapper = jacksonObjectMapper()
        // Jackson detecta la lista y la guarda como array [...]
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonPath.toFile(), lista)
    }

    private fun generarXML(lista: List<ArchivoReporte>, xmlPath: Path) {
        val xmlMapper = XmlMapper().registerKotlinModule()
        // Metemos la lista en el objeto Reporte para que el XML tenga el nodo raíz <Reporte>
        val reporteWrapper = Reporte(lista)
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlPath.toFile(), reporteWrapper)
    }
}