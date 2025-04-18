package com.nicolas.maltesp.presentation.ui.navigation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerMenuItem(
    val title: String,
    val route: String
)

@Composable
fun DrawerMenuItems(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
){
    val menuItems = listOf(
        DrawerMenuItem(
            title = "Início",
            route = "home"
        ),
        DrawerMenuItem(
            title = "Configurações",
            route = "settings"
        )
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        menuItems.forEach { menuItem ->
            DrawerItemButton(
                navController = navController,
                menuItem = menuItem,
                drawerState = drawerState,
                scope = scope
            )
        }
    }
}

@Composable
fun DrawerItemButton(
    navController: NavController,
    menuItem: DrawerMenuItem,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    val isAlredySelected = navController.currentBackStackEntry?.destination?.route == menuItem.route

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                if(!isAlredySelected) {
                    navController.navigate(route = menuItem.route)
                    scope.launch { drawerState.close() }
                }
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {

            Row {
                Text(
                    text = menuItem.title,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
            }
            if(isAlredySelected) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                ) {}
            }
        }
    }
}