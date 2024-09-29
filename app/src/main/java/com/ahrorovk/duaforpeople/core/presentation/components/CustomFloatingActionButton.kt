package com.ahrorovk.duaforpeople.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .size(60.dp),
        onClick = {
            onClick()
        },
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = null,
            tint = Color.Green
        )
    }
}