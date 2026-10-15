package Ejercicio3

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class ConfigApp(
    val directorio_inicial: String = System.getProperty("user.home"),
    val mostrar_archivos_ocultos: Boolean = false
)

class GestorConfiguracion {

    fun cargarConfiguracion(): ConfigApp {
        val archivoConfig = File("config.json")

        if (!archivoConfig.exists()) {
            println("config.json no encontrado. Usando configuración por defecto.")
            return ConfigApp()
        }

        return try {
            val mapper = jacksonObjectMapper()
            mapper.readValue<ConfigApp>(archivoConfig)
        } catch (e: Exception) {
            println("Error al leer config.json: ${e.message}. Usando configuración por defecto.")
            ConfigApp()
        }
    }
}