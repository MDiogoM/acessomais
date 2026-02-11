package pt.ipvc.acessomais.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.ipvc.acessomais.data.model.Local

@Composable
fun AddLocalScreen(onSave: (Local) -> Unit, onCancel: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var lon by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(3) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Novo Local", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome do Local") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lat, onValueChange = { lat = it }, label = { Text("Latitude") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lon, onValueChange = { lon = it }, label = { Text("Longitude") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = comentario, onValueChange = { comentario = it }, label = { Text("Comentário") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

        Text("Avaliação", modifier = Modifier.padding(top = 16.dp))
        Slider(value = rating.toFloat(), onValueChange = { rating = it.toInt() }, valueRange = 1f..5f, steps = 3)

        Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Cancelar") }
            Button(
                onClick = {
                    onSave(Local(
                        nome = nome,
                        tipo = "Público",
                        latitude = lat.toDoubleOrNull() ?: 0.0,
                        longitude = lon.toDoubleOrNull() ?: 0.0,
                        temRampa = false, temElevador = false, larguraEntradaCm = null, temWcAdaptado = false,
                        comentario = comentario.takeIf { it.isNotBlank() },
                        rating = rating
                    ))
                },
                enabled = nome.isNotBlank() && lat.isNotBlank() && lon.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) { Text("Guardar") }
        }
    }
}