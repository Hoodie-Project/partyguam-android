package com.party.presentation.screen.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.calculateAgeSafely
import com.party.common.component.NetworkImageLoad
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T1
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.profile.UserProfile
import com.party.presentation.enum.GenderType
import com.party.presentation.screen.profile.UserProfileState

@Composable
fun ProfileTopArea(
    userProfileState: UserProfileState,
    onProfileEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NetworkImageLoad(
                url = userProfileState.userProfile.image,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            HeightSpacer(heightDp = 12.dp)

            UserNickNameAndGenderAndAge(
                nickName = userProfileState.userProfile.nickname,
                gender = userProfileState.userProfile.gender,
                genderVisible = userProfileState.userProfile.genderVisible,
                birth = userProfileState.userProfile.birth,
                birthVisible = userProfileState.userProfile.birthVisible,
            )

            HeightSpacer(heightDp = 12.dp)

            EditProfileCard(
                onClick = onProfileEditClick
            )
        }
    }
}

@Composable
private fun UserNickNameAndGenderAndAge(
    nickName: String,
    gender: String,
    genderVisible: Boolean,
    birth: String,
    birthVisible: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextComponent(
            text = nickName,
            fontSize = T1,
            fontWeight = FontWeight.SemiBold
        )

        if(genderVisible){
            WidthSpacer(widthDp = 8.dp)
            TextComponent(
                text = GenderType.fromType(gender).toDisplayText(),
                fontSize = T3,
            )
        }

        WidthSpacer(widthDp = 8.dp)
        if(genderVisible && birthVisible){
            Text(text = "|")
        }
        WidthSpacer(widthDp = 8.dp)
        if(birthVisible){
            TextComponent(
                text = "${calculateAgeSafely(birth)}살",
                fontSize = T3,
            )
        }
    }
}

@Composable
private fun EditProfileCard(
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, GRAY200),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = "프로필 편집",
                    fontSize = B2,
                    textColor = GRAY500,
                )
                WidthSpacer(widthDp = 4.dp)
                DrawableIcon(
                    icon = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "프로필 편집",
                    tintColor = GRAY400,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileTopAreaPreview() {
    ProfileTopArea(
        userProfileState = UserProfileState(
            userProfile = UserProfile(
                nickname = "무화과케이크",
                birth = "1992-03-29",
                birthVisible = true,
                gender = "M",
                genderVisible = true,
                portfolioTitle = "",
                portfolio = "",
                image = "",
                createdAt = "",
                updatedAt = "",
                userPersonalities = emptyList(),
                userCareers = emptyList(),
                userLocations = emptyList()
            )
        ),
        onProfileEditClick = {}
    )
}