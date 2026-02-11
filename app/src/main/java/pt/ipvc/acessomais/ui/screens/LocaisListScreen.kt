package pt.ipvc.acessomais.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.ipvc.acessomais.viewmodel.LocalViewModel
import pt.ipvc.acessomais.data.model.Local

@Composable
fun LocaisListScreen(viewModel: LocalViewModel, onLocalSelected: (Local) -> Unit) {
    val locais by viewModel.locais.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(locais) { local ->
            OutlinedCard(
                modifier = Modifier.fillMaxWidth().clickable { onLocalSelected(local) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp).padding(end = 12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(local.nome, style = MaterialTheme.typography.titleLarge)
                        Text("${local.tipo} • ⭐ ${local.rating}/5", style = MaterialTheme.typography.bodyMedium)
                    }
                    IconButton(onClick = { viewModel.deleteLocal(local) }) {
                        Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}