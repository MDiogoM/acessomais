package pt.ipvc.acessomais.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import pt.ipvc.acessomais.viewmodel.LocalViewModel

@Composable
fun LocaisMapScreen(viewModel: LocalViewModel) {
    val locais by viewModel.locais.collectAsState()
    val novoLocal by viewModel.localRecemAdicionado.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(41.6932, -8.8327), 12f)
    }

    LaunchedEffect(novoLocal) {
        novoLocal?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 16f)
            )
            viewModel.limparFoco()
        }
    }

    GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
        locais.forEach { local ->
            Marker(
                state = MarkerState(position = LatLng(local.latitude, local.longitude)),
                title = local.nome,
                // Removido o rating do snippet para mostrar apenas o comentário
                snippet = local.comentario ?: "Sem comentários"
            )
        }
    }
}