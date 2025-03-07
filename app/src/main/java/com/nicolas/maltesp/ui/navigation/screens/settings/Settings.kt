package com.nicolas.maltesp.ui.navigation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nicolas.maltesp.others.objects.VectorIcons
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    var enabledIcon by remember {mutableStateOf(true)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Configurações")
                },
                colors = ScaffoldColors.TopBar,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            enabledIcon = false
                                  },
                        enabled = enabledIcon
                    )
                    {
                        Icon(
                            ImageVector.vectorResource(id = VectorIcons.backArrow),
                            contentDescription = "BackButton"
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            SettingsContent(
                paddingValues = paddingValues,
            )
        }
    )
}

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val isDarkTheme by settingsViewModel.themeFlow.collectAsState()

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(24.dp)
        ) {
            item {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Tema do Aplicativo",
                        style = MaterialTheme.typography.titleMedium)
                    Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
                        ThemeSwitcher(
                            darkTheme = isDarkTheme,
                            size = 48.dp
                        ) { settingsViewModel.saveTheme(!isDarkTheme) }
                    }
                }
            }

        }

        // TODO: Implementar "Salvar ultima receita enviada true false"}

    }
}

