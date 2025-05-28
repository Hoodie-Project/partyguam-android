package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.guam.design.B1
import com.party.guam.design.T2
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
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

        Column {
            userProfileState.userProfile.userLocations.forEachIndexed { _, userLocation ->
                Column {
                    TextComponent(
                        modifier = Modifier
                            .height(22.dp),
                        text = "·${userLocation.location.province} ${userLocation.location.city}",
                        fontSize = B1,
                        fontWeight = FontWeight.Normal,
                    )
                    WidthSpacer(widthDp = 8.dp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HopeLocationAreaPreview() {
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