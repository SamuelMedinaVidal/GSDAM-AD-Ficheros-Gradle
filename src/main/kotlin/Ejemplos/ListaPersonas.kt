package org.example.Ejemplos

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

data class ListaPersonas(
    @JacksonXmlElementWrapper(useWrapping = false)
    val persona: List<Persona>
)