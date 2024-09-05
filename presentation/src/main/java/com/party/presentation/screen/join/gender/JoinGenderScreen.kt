package com.party.presentation.screen.join.gender

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.navigation.Screens
import com.party.presentation.R
import com.party.presentation.screen.join.JoinScreenButton

@Composable
fun JoinGenderScreen(
    navController: NavHostController,
    context: Context,
    setActionText: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        setActionText("4/4")
    }

    var selectedGender by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.join_gender1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.join_gender2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            SelectGenderArea(
                context = context,
                selectedGender = selectedGender,
                onSelect = {
                    selectedGender = it
                }
            )
        }

        JoinScreenButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(id = R.string.common2),
            buttonTextColor = if(selectedGender.isNotEmpty()) BLACK else GRAY400,
            buttonContainerColor = if(selectedGender.isNotEmpty()) PRIMARY else LIGHT400,
            buttonBorderColor = if(selectedGender.isNotEmpty()) PRIMARY else  LIGHT200,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            navController = navController,
            routeScreen = Screens.JoinComplete
        )

        HeightSpacer(heightDp = 12.dp)
    }
}