package com.party.presentation.screen.detail.detail_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.navigation.Screens
import com.party.presentation.screen.detail.DetailProfileNextButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectedLocationArea(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onDelete: (Pair<String, Int>) -> Unit,
    accessToken: String,
    detailProfileViewModel: DetailProfileViewModel,
) {
    LaunchedEffect(key1 = Unit) {
        detailProfileViewModel.saveSuccess.collectLatest {
            navController.navigate(Screens.DetailCarrier)
        }
    }

    LaunchedEffect(key1 = Unit) {
        detailProfileViewModel.saveFail.collectLatest {
            snackBarMessage(snackBarHostState, it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SelectedLocationList(
            selectedProvince = selectedProvince,
            selectedLocationList = selectedLocationList,
            onDelete = onDelete,
        )

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            text = stringResource(id = R.string.common1),
            textColor = if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) BLACK else GRAY400,
            containerColor = if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) PRIMARY else LIGHT400,
            onClick = {
                if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) {
                    detailProfileViewModel.saveInterestLocation(
                        accessToken = accessToken,
                        locations = InterestLocationList(
                            locations = selectedLocationList.map {
                                InterestLocationRequest(id = it.second)
                            }
                        )
                    )
                }
            }
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textColor = GRAY500,
            textAlign = Alignment.Center,
            textDecoration = TextDecoration.Underline,
            onClick = { navController.navigate(Screens.DetailCarrier) }
        )
    }
}

@Composable
fun SelectedLocationList(
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onDelete: (Pair<String, Int>) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = selectedLocationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectLocationComponent(
                item = item,
                selectedProvinceName = selectedProvince,
                onDelete = { onDelete(it) }
            )
            WidthSpacer(widthDp = 8.dp)
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SelectLocationComponent(
    item: Pair<String, Int>,
    selectedProvinceName: String,
    onDelete: (Pair<String, Int>) -> Unit,
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "$selectedProvinceName ${item.first}",
                    color = BLACK,
                    fontSize = B2,
                )
                IconButton(
                    onClick = { onDelete(item) },
                    interactionSource = MutableInteractionSource(),
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                    )
                }
            }
        }
    }
}