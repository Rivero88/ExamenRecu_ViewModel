package com.example.examenrecu_viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AcademiaViewModel: ViewModel() {
    /**
     *  "MutableStateFlow" es una clase que proporciona una secuencia de valores que pueden cambiar con el tiempo.
     *  Se inicializa con un nuevo objeto de AcademiaUIState, una clase que contiene el estado de la interfaz de usuario
     *  de la aplicación de la academia.
     */
    private val _uiState = MutableStateFlow(AcademiaUIState())

    /**
     * Se declara una variable pública "uiState" que es inmutable "StateFlow", que contiene objetos de tipo
     * "AcademiaUIState".
     * "uiState" se inicializa con el valor de "_uiState" convertido a una secuencia de estado mediante asStateFlow().
     * La función asStateFlow() convierte el MutableStateFlow de "_uiState" en un StateFlow, lo que significa que
     * la referencia pública "uiState" proporciona acceso de solo lectura al estado actual de la interfaz de usuario.
     */
    val uiState: StateFlow<AcademiaUIState> = _uiState.asStateFlow()

    /**
     * "valorHorasNuevo2" es una variable mutable que puede ser cambiada en el código que reside dentro de la clase que lo contiene,
     * pero no puede ser modificada desde fuera de esa clase debido a la restricción "private set".
     * Esto es útil para mantener el control sobre el estado de la variable y restringir su modificación a partes específicas del código.
     */
    var valorHorasNuevo2 by mutableStateOf("")
        private set;

    fun nuevoValorHoras(valorHorasNuevo1: String) {
        valorHorasNuevo2 = valorHorasNuevo1
    }

    private fun actTextoUltimaAccion(asignaturas: ArrayList<Asignatura>): String {
        var textoActualizar = ""
        /**
         * recorremos el array de asignaturas
         */
        for (asig in asignaturas) {
            if (asig.recuentoHoras > 0) {
                textoActualizar += "Asig: ${asig.nombre} precio hora: ${asig.precioHora} total horas: ${asig.recuentoHoras}\n."
            }
        }
        return textoActualizar;
    }

    /**
     * Pasamos por parámetro 1 asignatura, las horas del textfield y el array.
     */
    fun sumaHoras (asignatura: Asignatura, anadirHoras: String, asignaturas: ArrayList<Asignatura>) {
        /**
         * Las horas que recibidos del textfield son tipo String por lo que la convertimos a Int para trabajar con ella.
         * Luego más adelante se vuelve a pasar a String.
         */
        var anadirHorasFin = anadirHoras.toInt()
        var textoUltimaAccionActualizar = ""
        var textoResumenActualizar = ""

        if (anadirHorasFin > 0) {
            asignatura.recuentoHoras += anadirHorasFin
            textoUltimaAccionActualizar = generarTextoResumen("añadido", anadirHorasFin.toString(), asignatura.nombre, asignatura.precioHora.toString())
            textoResumenActualizar = actTextoUltimaAccion(asignaturas)
        }
        /**
         * La lambda que se pasa a update toma el estado actual (currentState) como entrada y devuelve el nuevo estado.
         * "currentState.copy()" crea una copia del estado actual, y modifica los campos que se especifican con los nuevos valores.
         */
        _uiState.update { currentState ->
            currentState.copy(
                textoUltimaAccion=textoUltimaAccionActualizar,
                textoResumen=textoResumenActualizar
            )
        }
    }

    /**
     * Pasamos por parámetro 1 asignatura, las horas del textfield y el array.
     */
    fun restarHoras (asignatura: Asignatura, restarHoras: String, asignaturas: ArrayList<Asignatura>) {
        /**
         * Las horas que recibidos del textfield son tipo String por lo que la convertimos a Int para trabajar con ella.
         * Luego más adelante se vuelve a pasar a String.
         */
        var restarHorasFin = restarHoras.toInt()
        var textoUltimaAccionActualizar = ""
        var textoResumenActualizar =""

        if (restarHorasFin > 0) {
            if(asignatura.recuentoHoras >= restarHorasFin){
                asignatura.recuentoHoras -= restarHorasFin
                textoUltimaAccionActualizar = generarTextoResumen("restado", restarHorasFin.toString(), asignatura.nombre, asignatura.precioHora.toString())
                textoResumenActualizar = actTextoUltimaAccion(asignaturas)
            }else if(asignatura.recuentoHoras == 0){
                textoUltimaAccionActualizar = "No se ha restado ningún valor."
                textoResumenActualizar = actTextoUltimaAccion(asignaturas)
            }else {
                restarHorasFin = asignatura.recuentoHoras
                asignatura.recuentoHoras = 0
                textoUltimaAccionActualizar = generarTextoResumen("restado", restarHorasFin.toString(), asignatura.nombre, asignatura.precioHora.toString())
                textoResumenActualizar = actTextoUltimaAccion(asignaturas)
            }

        }
        /**
         * La lambda que se pasa a update toma el estado actual (currentState) como entrada y devuelve el nuevo estado.
         * "currentState.copy()" crea una copia del estado actual, y modifica los campos que se especifican con los nuevos valores.
         */
        _uiState.update { currentState ->
            currentState.copy(
                textoUltimaAccion=textoUltimaAccionActualizar,
                textoResumen=textoResumenActualizar
            )
        }
    }

    private fun generarTextoResumen(accion: String, horas: String, nombre: String, precio: String): String {
        return "Se han $accion $horas horas a la asignatura $nombre a un precio de $precio €.";
    }
}