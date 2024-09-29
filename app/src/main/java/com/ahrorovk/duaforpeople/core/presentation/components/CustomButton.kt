package com.ahrorovk.duaforpeople.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    textSize: Int,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onBackground,
    selected: Boolean = true,
    isLoading: Boolean,
    height: Int = 50,
    shape: Int = 10,
    textColor: Color = MaterialTheme.colorScheme.background,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        shape = RoundedCornerShape(shape.dp),
        modifier = modifier
            .height(height.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) color else Color(
                0xFFDADADA
            ), contentColor = Color.White
        ),
        content = {
            if (!isLoading) {
                Text(
                    text = text,
                    fontSize = textSize.sp,
                    color = if (selected) MaterialTheme.colorScheme.background else textColor
                )
            } else {
                CircularProgressIndicator()
            }
        },
    )
}