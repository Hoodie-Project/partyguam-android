package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.utils.fs
import com.party.guam.design.LIGHT400
import com.party.guam.design.T3
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.guam.design.B2

@Composable
fun UserLocationArea(
    userLocationList: List<UserLocation>
) {
    Row {
        userLocationList.forEachIndexed { _, userLocation ->
            Chip(
                text = "${userLocation.location.province} ${userLocation.location.city}",
                containerColor = LIGHT400,
                fontWeight = FontWeight.Normal,
                fontSize = fs(T3),
            )
            WidthSpacer(widthDp = 8.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserLocationAreaPreview(){
    UserLocationArea(
        userLocationList = listOf(
            UserLocation(
                id = 1,
                location = UserProfileLocation(
                    province = "서울",
                    city = "강남구",
                    id = 1
                )
            ),
        )
    )
}