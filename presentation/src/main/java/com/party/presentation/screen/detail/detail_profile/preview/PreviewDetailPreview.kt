package com.party.presentation.screen.detail.detail_profile.preview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.party.common.ui.theme.DARK200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.detail.detail_profile.DetailProfileScreen
import com.party.presentation.screen.detail.detail_profile.ProvinceComponent
import com.party.presentation.screen.detail.detail_profile.SelectLocationComponent
import com.party.presentation.screen.detail.detail_profile.SelectProvinceArea

@Preview(showBackground = true)
@Composable
fun DetailProfileScreenPreview() {
    DetailProfileScreen(
        navController = NavHostController(LocalContext.current),
        snackBarHostState = SnackbarHostState(),
        context = LocalContext.current,
    )
}

@Preview(showBackground = true)
@Composable
fun SelectProvinceAreaPreview() {
    SelectProvinceArea(
        modifier = Modifier,
        selectedProvince = "서울",
        onSelectProvince = { "서울" }
    )
}

@Preview(showBackground = true)
@Composable
fun SelectedCityComponentPreview(){
    ProvinceComponent(
        containerColor = LIGHT400,
        text = "서울",
        textColor = DARK200,
        onSelectCity = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UnSelectedCityComponentPreview(){
    ProvinceComponent(
        containerColor = WHITE,
        text = "서울",
        textColor = GRAY400,
        onSelectCity = {}
    )
}


// SelectedLocationArea
@Preview(showBackground = true)
@Composable
fun SelectCityComponentPreview() {
    SelectLocationComponent(
        selectedProvinceName = "서울",
        selectedCity2Name = "강남구",
        onDelete = {}
    )
}