package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.T2
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.presentation.screen.profile.UserProfileState

@Composable
fun HopeLocationArea(
    userProfileState: UserProfileState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "희망 장소",
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 20.dp)

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = userProfileState.userProfile.userLocations,
                key = { _, item ->
                    item.id
                }
            ){ _, item ->
                Chip(
                    text = "${item.location.province} ${item.location.city}",
                    fontSize = B1,
                    fontWeight = FontWeight.Normal,
                    containerColor = LIGHT400
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HopeLocationAreaPreview() {
    HopeLocationArea(
        userProfileState = UserProfileState(
            userProfile = UserProfile(
                nickname = "",
                birth = "",
                birthVisible = false,
                gender = "",
                genderVisible = false,
                portfolioTitle = "",
                portfolio = "",
                image = "",
                createdAt = "",
                updatedAt = "",
                userPersonalities = emptyList(),
                userCareers = emptyList(),
                userLocations = listOf(
                    UserLocation(
                        id = 0,
                        location = UserProfileLocation(
                            id = 0,
                            province = "서울",
                            city = "영등포구"
                        )
                    ),
                    UserLocation(
                        id = 1,
                        location = UserProfileLocation(
                            id = 1,
                            province = "서울",
                            city = "중구"
                        )
                    ),
                )
            )
        )
    )
}