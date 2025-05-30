package com.party.presentation.screen.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.calculateAgeSafely
import com.party.common.component.NetworkImageLoad
import com.party.common.component.chip.Chip
import com.party.common.component.chip.MainAndSubAndImageChip
import com.party.common.component.icon.DrawableIcon
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT400
import com.party.guam.design.LINK_COLOR
import com.party.guam.design.T1
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfilePosition
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
        elevation = CardDefaults.cardElevation(2.dp),
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

            if(userProfileState.userProfile.userCareers.isNotEmpty()){
                // 커리어
                HeightSpacer(heightDp = 20.dp)
                UserCareerArea(
                    userCareers = userProfileState.userProfile.userCareers
                )

                // 포트폴리오
                val portfolio = userProfileState.userProfile.portfolio
                if(!portfolio.isNullOrBlank()){
                    HeightSpacer(heightDp = 20.dp)
                    TextComponent(
                        text = userProfileState.userProfile.portfolioTitle ?: "",
                        fontSize = B2,
                        textDecoration = TextDecoration.Underline,
                        textColor = LINK_COLOR
                    )
                }
            }

            HeightSpacer(heightDp = 24.dp)

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
            Image(
                painter = painterResource(id = R.drawable.vertical_rectangle),
                contentDescription = "",
                modifier = Modifier
                    .width(2.dp)
                    .height(12.dp)
                    .background(GRAY400)
                    //.clip(RoundedCornerShape(12.dp))
            )
        }

        /*if(genderVisible && birthVisible){
            Text(text = "|")
        }*/
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

@Composable
private fun UserCareerArea(
    userCareers: List<UserCareer>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        UserCareerAreaItem(
            years = userCareers[0].years,
            main = userCareers[0].position.main,
            sub = userCareers[0].position.sub,
            containerColor = LIGHT400,
            contentColor = BLACK,
            image = painterResource(id = R.drawable.vertical_rectangle_primary)
        )

        if(userCareers.size > 1){
            HeightSpacer(heightDp = 8.dp)
            UserCareerAreaItem(
                years = userCareers[1].years,
                main = userCareers[1].position.main,
                sub = userCareers[1].position.sub,
                containerColor = GRAY100,
                contentColor = BLACK,
                image = painterResource(id = R.drawable.vertical_rectangle)
            )
        }
    }
}

@Composable
private fun UserCareerAreaItem(
    years: Int,
    main: String,
    sub: String,
    containerColor: Color,
    contentColor: Color,
    image: Painter,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Chip(
            text = "${years}년",
            containerColor = containerColor,
            fontWeight = FontWeight.Normal,
            contentColor = BLACK,
            fontSize = B2,
        )
        WidthSpacer(widthDp = 8.dp)

        MainAndSubAndImageChip(
            image = image,
            containerColor = containerColor,
            contentColor = contentColor,
            roundedCornerShape = 999.dp,
            main = main,
            sub = sub,
        )
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
                portfolioTitle = "나의 이력서",
                portfolio = "https://www.naver.com",
                image = "",
                createdAt = "",
                updatedAt = "",
                userPersonalities = emptyList(),
                userCareers = listOf(
                    UserCareer(
                        id = 0,
                        years = 2,
                        careerType = "",
                        position = UserProfilePosition(main = "개발자", sub = "안드로이드")
                    ),
                    UserCareer(
                        id = 0,
                        years = 0,
                        careerType = "",
                        position = UserProfilePosition(main = "개발자", sub = "IOS")
                    )
                ),
                userLocations = emptyList()
            )
        ),
        onProfileEditClick = {}
    )
}