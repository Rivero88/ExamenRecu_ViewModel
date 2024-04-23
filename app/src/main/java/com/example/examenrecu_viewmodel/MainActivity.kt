package com.example.examenrecu_viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenrecu_viewmodel.ui.theme.ExamenRecu_ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenRecu_ViewModelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaAcademia(asignaturas = DataSource.asignaturas, viewModelAcademia = viewModel())
                }
            }
        }
    }
}

@Composable
fun PantallaAcademia(modifier: Modifier = Modifier, asignaturas: ArrayList<Asignatura>,
    viewModelAcademia: AcademiaViewModel) {
    val uiState by viewModelAcademia.uiState.collectAsState()
    Column(modifier) {
        Text(text = "Bienvenid@ a la Academia de Ester.",
            modifier = Modifier.fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        )
        AcademiaApp(modifier = Modifier,
            asignaturas,
            viewModelAcademia,
            uiState,
        )

        EntradaTextField(modifier = Modifier,
            viewModelAcademia,
            uiState,
        )
        CuadroTexto(modifier = Modifier, uiState)

    }
}


@Composable
fun AcademiaApp(modifier: Modifier = Modifier, asignaturas: ArrayList<Asignatura>,
                viewModelAcademia: AcademiaViewModel,
                uiState: AcademiaUIState){
        LazyVerticalGrid(
            modifier = Modifier.height(300.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.End
        ){
            items(asignaturas) { asignatura ->
                Card (modifier = Modifier.padding(5.dp)) {
                    Text(
                        text = "Asig: ${asignatura.nombre}",
                        modifier = Modifier
                            .background(Color.Yellow)
                            .padding(15.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "€/hora: ${asignatura.precioHora}",
                        modifier = Modifier
                            .background(Color.Cyan)
                            .padding(15.dp)
                            .fillMaxWidth()
                    )
                    Row(
                    ) {
                        Button(onClick = {
                            viewModelAcademia.sumaHoras(asignatura, viewModelAcademia.valorHorasNuevo2, asignaturas)
                        }) {
                            Text(text = "+")
                        }
                        Button(onClick = {
                            viewModelAcademia.restarHoras(asignatura, viewModelAcademia.valorHorasNuevo2, asignaturas)
                        }) {
                            Text(text = "-")
                        }
                    }
                }

            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradaTextField(modifier: Modifier = Modifier, viewModelAcademia: AcademiaViewModel, uiState: AcademiaUIState){
    TextField(
        value = viewModelAcademia.valorHorasNuevo2,
        onValueChange = { viewModelAcademia.nuevoValorHoras(it) },
        label = { Text(text = "Horas a contratar o eliminar") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
}

@Composable
fun CuadroTexto(modifier: Modifier = Modifier, uiState: AcademiaUIState){
    Column(
        modifier = Modifier.height(300.dp)
            .width(500.dp)
            .background(Color.LightGray)
            .padding(20.dp)
    ) {
        Text(
            text = "Ultima acción:\n" + uiState.textoUltimaAccion,
            modifier = Modifier
                .background(Color.Magenta)
                .padding(10.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = "Ultima acción:\n" + uiState.textoResumen,
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
