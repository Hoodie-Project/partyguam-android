package com.party.presentation.screen.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StateScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "상태창")
    }
}