package Ejercicio3

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.Scanner
import kotlin.streams.toList

// Al poner el Scanner en el constructor (private val), todas las funciones de la clase pueden usarlo sin pedirlo por parámetro.
class GestorArchivos() {
    private val scanner = Scanner(System.`in`)

    fun mostrarInformacionHome(homePath: Path, mostrarOcultos: Boolean) {
        println("\nHOME: $homePath\n")
        try {
            val elementos = Files.list(homePath)
                .filter { mostrarOcultos || !it.fileName.toString().startsWith(".") }
                .limit(5)
                .toList()

            elementos.forEach { path ->
                val atributos = Files.readAttributes(path, BasicFileAttributes::class.java)
                val tipo = if (atributos.isDirectory) "Directorio" else if (atributos.isRegularFile) "Archivo" else "Otro"
                val tamano = if (atributos.isRegularFile) "${atributos.size()} bytes" else "0 bytes"


                println("--------------------------------")
                println("Nombre: ${path.fileName}")
                println("Tipo: $tipo")
                println("Tamaño: ${atributos.size()} bytes")
                println("Creado: ${atributos.creationTime()}")
                println("Modificado: ${atributos.lastModifiedTime()}")
                println("Legible: ${Files.isReadable(path)}")
                println("Escribible: ${Files.isWritable(path)}")
            }

            if (Files.exists(homePath)) {
                val fileStore = Files.getFileStore(homePath)
                println("--------------------------------")
                println("\nInformación del sistema de archivos")
                println("Tipo: ${fileStore.type()}")
                println("Espacio total: ${fileStore.totalSpace} bytes")
                println("Espacio libre: ${fileStore.usableSpace} bytes\n")
            }
        } catch (e: Exception) {
            println("Error al acceder a la información del sistema: ${e.message}")
        }
    }

    fun crearDirectorio(homePath: Path) {
        print("Introduce el nombre del nuevo directorio: ")
        val nombreDirectorio = scanner.nextLine()
        val nuevoPath = homePath.resolve(nombreDirectorio)

        try {
            Files.createDirectory(nuevoPath)
            println("Directorio '$nombreDirectorio' creado.")
        } catch (e: FileAlreadyExistsException) {
            println("Error: El directorio '$nombreDirectorio' ya existe")
        } catch (e: IOException) {
            println("Error de entrada/salida: ${e.message}")
        }
    }

    fun eliminarDirectorio(homePath: Path) {
        print("Introduce el nombre del directorio a eliminar: ")
        val nombreDirectorio = scanner.nextLine()
        val pathAEliminar = homePath.resolve(nombreDirectorio)

        try {
            Files.delete(pathAEliminar)
            println("Directorio '$nombreDirectorio' eliminado.")
        } catch (e: NoSuchFileException) {
            println("Error: El directorio '$nombreDirectorio' no existe.")
        } catch (e: DirectoryNotEmptyException) {
            println("Error: El directorio no está vacío. No se puede eliminar.")
        } catch (e: IOException) {
            println("Error al intentar eliminar: ${e.message}")
        }
    }

    fun verContenidoRecursivo(homePath: Path) {
        print("Introduce el nombre del directorio a explorar (pulsa Enter para el home): ")
        val nombreDirectorio = scanner.nextLine()
        val targetPath = if (nombreDirectorio.isNotBlank()) homePath.resolve(nombreDirectorio) else homePath

        if (!Files.exists(targetPath) || !Files.isDirectory(targetPath)) {
            println("Error: La ruta no existe o no es un directorio.")
            return
        }

        println("\nExplorando de forma recursiva: $targetPath")
        try {
            Files.walk(targetPath).use { stream ->
                stream.forEach { path ->
                    println(" - $path")
                }
            }
        } catch (e: AccessDeniedException) {
            println("Error de permisos: No tienes acceso a ciertos subdirectorios.")
        } catch (e: IOException) {
            println("Error de entrada/salida: ${e.message}")
        }
    }

    fun crearArchivoDeTexto(homePath: Path) {
        print("Introduce el nombre del archivo de texto a crear: ")
        val nombreArchivo = scanner.nextLine()
        val pathArchivo = homePath.resolve(nombreArchivo)

        println("Escribe líneas de texto (escribe 'FIN' en una línea nueva para terminar):")
        val lineas = mutableListOf<String>()
        while (true) {
            val linea = scanner.nextLine()
            if (linea == "FIN") break
            lineas.add(linea)
        }

        try {
            Files.write(pathArchivo, lineas, StandardCharsets.UTF_8)
            println("Archivo '$nombreArchivo' creado correctamente.")
        } catch (e: IOException) {
            println("Error al guardar el archivo: ${e.message}")
        }
    }

    fun leerArchivoDeTexto(homePath: Path) {
        print("Introduce el nombre del archivo de texto a leer: ")
        val nombreArchivo = scanner.nextLine()
        val pathArchivo = homePath.resolve(nombreArchivo)

        try {
            Files.newBufferedReader(pathArchivo, StandardCharsets.UTF_8).use { reader ->
                println("\n--- Contenido de $nombreArchivo ---")
                reader.lineSequence().forEach { println(it) }
                println("----------------------------------\n")
            }
        } catch (e: NoSuchFileException) {
            println("Error: El archivo '$nombreArchivo' no existe. Comprueba el nombre.")
        } catch (e: IOException) {
            println("Error de lectura: ${e.message}")
        }
    }

    fun encriptarOcultarArchivo(homePath: Path) {
        print("Introduce el nombre del archivo de texto a encriptar: ")
        val nombreArchivo = scanner.nextLine()
        val pathOrigen = homePath.resolve(nombreArchivo)

        val nombreSinExtension = nombreArchivo.substringBeforeLast(".")
        val pathDestino = homePath.resolve("$nombreSinExtension.bin")

        try {
            val bytesOriginales = Files.readAllBytes(pathOrigen)
            val bytesEncriptados = ByteArray(bytesOriginales.size) { i ->
                (bytesOriginales[i] + 1).toByte()
            }
            Files.write(pathDestino, bytesEncriptados)
            println("Archivo encriptado y guardado con éxito como '${pathDestino.fileName}'.")
        } catch (e: NoSuchFileException) {
            println("Error: El archivo origen '$nombreArchivo' no existe.")
        } catch (e: IOException) {
            println("Error al encriptar el archivo: ${e.message}")
        }
    }

    fun copiarUnArchivo(homePath: Path) {
        print("Introduce el nombre del archivo original: ")
        val nombreOrigen = scanner.nextLine()
        val pathOrigen = homePath.resolve(nombreOrigen)

        print("Introduce el nombre de la copia (destino): ")
        val nombreDestino = scanner.nextLine()
        val pathDestino = homePath.resolve(nombreDestino)

        try {
            Files.copy(pathOrigen, pathDestino, StandardCopyOption.REPLACE_EXISTING)
            println("Archivo '$nombreOrigen' copiado a '$nombreDestino' exitosamente.")
        } catch (e: NoSuchFileException) {
            println("Error: El archivo original no existe en el directorio.")
        } catch (e: IOException) {
            println("Error al intentar copiar el archivo: ${e.message}")
        }
    }
}