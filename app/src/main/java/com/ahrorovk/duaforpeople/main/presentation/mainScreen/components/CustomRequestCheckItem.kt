package com.ahrorovk.duaforpeople.main.presentation.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomCheckBox

@Composable
fun CustomRequestCheckItem(
    modifier: Modifier = Modifier,
    isDone: Boolean,
    dua: String,
    senderName: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                senderName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(dua, modifier = Modifier.fillMaxWidth(0.6f))

            CustomCheckBox(
                isDone = isDone,
                onCheckedChange = onCheckedChange
            )
        }
    }
}