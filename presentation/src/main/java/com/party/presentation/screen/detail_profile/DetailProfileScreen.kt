package com.party.presentation.screen.detail_profile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3

@Composable
fun DetailProfileScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    context: Context,
) {
    var selectedCity by remember {
        mutableStateOf("")
    }

    val selectedCountryList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ProfileIndicatorArea()

            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.detail_profile7),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.detail_profile8),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 46.dp)

            SelectLocationArea(
                selectedCity = selectedCity,
                selectedCountryList = selectedCountryList,
                onSelectCity = { selectedCity = it },
                onSelectCountry = {
                    selectedCountryList.add(it)
                },
                onDeleteCountry = { selectedCountryList.remove(it) },
                snackBarHostState = snackBarHostState,
                context = context,
            )
        }

        BottomArea(
            selectedCity = selectedCity,
            selectedCountryList = selectedCountryList,
            onDelete = { selectedCountryList.remove(it) },
        )

    }
}