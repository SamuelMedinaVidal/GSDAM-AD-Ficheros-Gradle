package Ejercicio3

import java.nio.file.Paths
import java.util.Scanner

fun main() {
    val scannerMenu = Scanner(System.`in`)

    // Cargamos la configuración usando su gestor
    val gestorConfiguracion = GestorConfiguracion()
    val config = gestorConfiguracion.cargarConfiguracion()

    // Establecemos la ruta de trabajo inicial
    val currentPath = Paths.get(config.directorio_inicial)

    // Instanciamos los gestores de trabajo
    val gestorArchivos = GestorArchivos()
    val gestorExportacion = GestorExportacion()

    var salir = false
    while (!salir) {
        // Le pasamos a mostrarInformacionHome la preferencia de archivos ocultos
        gestorArchivos.mostrarInformacionHome(currentPath, config.mostrar_archivos_ocultos)
        mostrarMenu()

        when (scannerMenu.nextLine()) {
            "1" -> gestorArchivos.crearDirectorio(currentPath)
            "2" -> gestorArchivos.eliminarDirectorio(currentPath)
            "3" -> gestorArchivos.verContenidoRecursivo(currentPath)
            "4" -> gestorArchivos.crearArchivoDeTexto(currentPath)
            "5" -> gestorArchivos.leerArchivoDeTexto(currentPath)
            "6" -> gestorArchivos.encriptarOcultarArchivo(currentPath)
            "7" -> gestorArchivos.copiarUnArchivo(currentPath)
            "8" -> gestorExportacion.exportarReporte(currentPath, config.mostrar_archivos_ocultos)
            "0" -> salir = true
            else -> println("Opción no válida. Introduce un número correcto.")
        }
    }
}

fun mostrarMenu() {
    println("===== MENÚ =====")
    println("1. Crear un directorio en el home")
    println("2. Eliminar un directorio del home")
    println("3. Ver contenido de un directorio del home")
    println("4. Crear un archivo de texto")
    println("5. Leer un archivo de texto")
    println("6. Encriptar/Ocultar archivo (Texto a Binario)")
    println("7. Copiar un archivo a otra ruta")
    println("8. Exportar reporte del directorio actual")
    println("0. Salir")
    print("Elige una opción: ")
}