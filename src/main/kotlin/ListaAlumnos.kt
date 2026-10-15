package org.example

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ListaAlumnos(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "alumno")
    val alumno: List<Alumno> = emptyList()
)