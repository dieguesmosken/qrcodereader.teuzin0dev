package com.example.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre este App") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "QR Code Reader",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Desenvolvedor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Teuzin0Dev")

            Text(
                text = "Copyright",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "© 2026 Teuzin0Dev. Todos os direitos reservados.")

            Text(
                text = "Bibliotecas Usadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "• Jetpack Compose\n" +
                        "• ML Kit Barcode Scanning\n" +
                        "• CameraX\n" +
                        "• Room Database\n" +
                        "• Credential Manager (Google Sign-In)\n" +
                        "• Accompanist"
            )

            Text(
                text = "Privacidade e Termos (LGPD)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Este aplicativo foi desenvolvido em conformidade com a Lei Geral de Proteção de Dados (LGPD). " +
                        "Seus dados de histórico de leitura e informações de login são gravados localmente no seu dispositivo. " +
                        "Nós não compartilhamos, vendemos ou transmitimos suas informações pessoais para servidores de terceiros sem o seu consentimento explícito. " +
                        "Você tem total controle sobre seus dados e pode apagá-los a qualquer momento utilizando as configurações do aplicativo."
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
