package com.nicolas.maltesp.presentation.ui.theme.appcolors

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ComponentsColors {
    object TextField {
        val equalState: TextFieldColors
            @Composable
            get() = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                errorIndicatorColor = Color.Red,
                errorContainerColor = Color.Red.copy(alpha = 0.05f)
            )

        val notEqualState: TextFieldColors
            @Composable
            get() = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                errorIndicatorColor = Color.Red,
                errorContainerColor = Color.Red.copy(alpha = 0.05f)
            )

        val invalidState: TextFieldColors
            @Composable
            get() = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.LightGray,
                focusedContainerColor = Color.LightGray,
                errorIndicatorColor = Color.Red,
                errorContainerColor = Color.Red.copy(alpha = 0.05f)
            )


    }
}