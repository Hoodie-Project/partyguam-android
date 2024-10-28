package com.party.presentation.screen.detail.detail_carrier.preview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.party.presentation.screen.detail.detail_carrier.AddCarrierCard
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierScreen
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierViewModel
import com.party.presentation.screen.detail.detail_carrier.PositionArea

@Preview
@Composable
fun PreviewDetailCarrierScreen() {
    DetailCarrierScreen(
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun PositionAreaPreview() {
    PositionArea(
        context = LocalContext.current,
        navController = rememberNavController(),
    )
}

@Preview(showBackground = true)
@Composable
fun AddCarrierButtonCardPreview() {
    AddCarrierCard(
        context = LocalContext.current,
        navController = rememberNavController(),
        selectedCarrier = "신입",
        selectedPosition = "개발자",
        selectedDetailPosition = "안드로이드",
        isMain = true,
    )
}