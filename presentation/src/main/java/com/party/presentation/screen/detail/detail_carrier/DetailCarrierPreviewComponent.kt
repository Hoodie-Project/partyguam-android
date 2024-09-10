package com.party.presentation.screen.detail.detail_carrier

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun PositionAreaPreview() {
    PositionArea(
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun AddCarrierButtonCardPreview() {
    AddCarrierButtonCard(
        navController = rememberNavController()
    )
}