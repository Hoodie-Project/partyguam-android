package com.party.presentation.screen.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserPersonality
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.domain.model.user.profile.UserProfilePosition
import com.party.presentation.enum.DetailProfileCardType
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile.UserProfileState

val profileCardTypeList = listOf(
    DetailProfileCardType.CAREER_POSITION,
    DetailProfileCardType.LIKE_LOCATION,
    DetailProfileCardType.LIKE_TIME,
    DetailProfileCardType.CHECK_PERSONALITY,
)

@Composable
fun DetailProfileSettingArea(
    userProfileState: UserProfileState,
    onClick: (Screens) -> Unit,
) {
    val progress = remember(userProfileState) {
        val countNotEmpty = profileCardTypeList.count { cardType ->
            when (cardType) {
                DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isNotEmpty()
                DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isNotEmpty()
                DetailProfileCardType.LIKE_TIME -> userProfileState.userProfile.userPersonalities.any {
                    it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id
                }
                DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.any {
                    it.personalityOption.personalityQuestion.id != PersonalityType.TENDENCY.id
                }
            }
        }
        countNotEmpty.toFloat() / profileCardTypeList.size
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // 세부프로필 설정하기
        DetailProfileSettingAreaTitle(
            userProfileState = userProfileState
        )

        // 인디케이터
        HeightSpacer(heightDp = 20.dp)
        com.party.presentation.screen.profile.component.LinearProgressIndicator(
            progress = progress
        )

        // 세부프로필 카드
        HeightSpacer(heightDp = 16.dp)
        DetailProfileCardArea(
            userProfileState = userProfileState,
            onClick = onClick
        )
    }
}

@Composable
private fun DetailProfileSettingAreaTitle(
    userProfileState: UserProfileState
) {
    val countNotEmpty = profileCardTypeList.count { cardType ->
        when (cardType) {
            DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isNotEmpty()
            DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isNotEmpty()
            DetailProfileCardType.LIKE_TIME -> userProfileState.userProfile.userPersonalities.any {
                it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id
            }
            DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.any {
                it.personalityOption.personalityQuestion.id != PersonalityType.TENDENCY.id
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            text = "세부프로필 설정하기",
            fontSize = T3,
            fontWeight = FontWeight.SemiBold,
        )

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = PRIMARY)){
                append(countNotEmpty.toString())
            }
            append("/")
            append("${profileCardTypeList.size}")
        }

        if(countNotEmpty in 0..3){
            Text(
                text = annotatedString,
                fontSize = T3,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun LinearProgressIndicator(
    progress: Float
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        progress = { if(progress == 0f) 0.01f else progress },
        strokeCap = StrokeCap.Round,
        trackColor = GRAY100,
        color = PRIMARY,
        gapSize = 0.dp,
        drawStopIndicator = {
            drawStopIndicator(
                drawScope = this,
                stopSize = 0.dp,
                color = Color.Transparent,
                strokeCap = StrokeCap.Round,
            )
        }
    )
}

@Composable
private fun DetailProfileCardArea(
    userProfileState: UserProfileState,
    onClick: (Screens) -> Unit,
) {
    val filteredList = profileCardTypeList.filter { cardType ->
        when (cardType) {
            DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isEmpty()
            DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isEmpty()
            DetailProfileCardType.LIKE_TIME -> userProfileState.userProfile.userPersonalities.none { it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id }
            DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.none { it.personalityOption.personalityQuestion.id != PersonalityType.TENDENCY.id }
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(
            items = filteredList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            DetailProfileCard(
                icon = convertProfileTypeIcon(item),
                title = item.title,
                content = item.content,
                onClick = { onClick(item.screens) }
            )
        }
    }
}

@Composable
private fun convertProfileTypeIcon(detailProfileCardType: DetailProfileCardType): Painter {
    return when (detailProfileCardType) {
        DetailProfileCardType.CAREER_POSITION -> painterResource(id = R.drawable.icon_position)
        DetailProfileCardType.LIKE_LOCATION -> painterResource(id = R.drawable.icon_location)
        DetailProfileCardType.LIKE_TIME -> painterResource(id = R.drawable.icon_time)
        DetailProfileCardType.CHECK_PERSONALITY -> painterResource(id = R.drawable.icon_check)
    }
}

@Composable
private fun DetailProfileCard(
    icon: Painter,
    title: String,
    content: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(118.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .noRippleClickable {
                        onClick()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                DrawableIcon(
                    icon = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .width(14.dp)
                        .height(22.dp),
                    tintColor = Color.Unspecified
                )
                WidthSpacer(widthDp = 4.dp)
                TextComponent(
                    text = title,
                    fontSize = B2,
                    fontWeight = FontWeight.SemiBold,
                    onClick = onClick,
                )
            }
            HeightSpacer(heightDp = 12.dp)
            TextComponent(
                text = content,
                fontSize = B2,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailProfileSettingAreaPreview() {
    DetailProfileSettingArea(
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
                userPersonalities = listOf(
                    UserPersonality(
                        id = 283,
                        personalityOption = PersonalityOption(
                            id = 5,
                            content = "새벽 (0시~6시)",
                            personalityQuestion = PersonalityQuestion(
                                id = 1,
                                content = "주로 작업하는 시간대는 어떻게 되시나요?",
                                responseCount = 2
                            )
                        )
                    ),
                ),
                userCareers = emptyList(),
                userLocations = listOf(
                    UserLocation(
                        id = 3695, location = UserProfileLocation(
                            id = 7617,
                            province = "Ramie",
                            city = "Vicki"
                        )
                    )
                ),
            ),
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailProfileSettingAreaPreview2() {
    DetailProfileSettingArea(
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
                userPersonalities = listOf(
                    UserPersonality(
                        id = 737,
                        personalityOption = PersonalityOption(
                            id = 2571,
                            content = "Roshawnda",
                            personalityQuestion = PersonalityQuestion(
                                id = 1,
                                content = "Theron",
                                responseCount = 553
                            )
                        )
                    )
                ),
                userCareers = listOf(
                    UserCareer(
                        id = 6007, years = 238, careerType = "Judith", position = UserProfilePosition(
                            main = "Natoya",
                            sub = "Danah"
                        )
                    )
                ),
                userLocations = listOf(
                    UserLocation(
                        id = 3695, location = UserProfileLocation(
                            id = 7617,
                            province = "Ramie",
                            city = "Vicki"
                        )
                    )
                ),
            ),
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailProfileCardPreview() {
    DetailProfileCard(
        icon = painterResource(id = R.drawable.icon_profile),
        title = DetailProfileCardType.CAREER_POSITION.title,
        content = DetailProfileCardType.CAREER_POSITION.content,
        onClick = {}
    )
}