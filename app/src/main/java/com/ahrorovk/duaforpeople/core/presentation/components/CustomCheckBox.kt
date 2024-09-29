package com.ahrorovk.duaforpeople.core.presentation.components

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Checkbox(
        modifier = modifier,
        checked = isDone,
        onCheckedChange = onCheckedChange
    )
}