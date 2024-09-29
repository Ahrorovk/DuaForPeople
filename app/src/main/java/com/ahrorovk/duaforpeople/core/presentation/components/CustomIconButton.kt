package com.ahrorovk.duaforpeople.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomIconButton(
    icon: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            icon,
            null,
            tint = tint
        )
    }
}