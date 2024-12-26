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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserPersonality
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.domain.model.user.profile.UserProfilePosition
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
) {
    val progress = remember {
        val countNotEmpty = profileCardTypeList.count {
            when (it) {
                DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isEmpty()
                DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isEmpty()
                DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.isEmpty()
                else -> true
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
            userProfileState = userProfileState
        )
    }
}

@Composable
private fun DetailProfileSettingAreaTitle(
    userProfileState: UserProfileState
) {
    val countNotEmpty = profileCardTypeList.count {
        when (it) {
            DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isEmpty()
            DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isEmpty()
            DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.isEmpty()
            else -> true
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
        Text(
            text = annotatedString,
            fontSize = T3,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun LinearProgressIndicator(
    progress: Float
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        progress = { if(progress == 0f) 0.01f else progress },
        strokeCap = StrokeCap.Round,
        trackColor = GRAY100,
        color = PRIMARY,
    )
}

@Composable
private fun DetailProfileCardArea(
    userProfileState: UserProfileState
) {
    val filteredList = profileCardTypeList.filter { cardType ->
        when (cardType) {
            DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isEmpty()
            DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isEmpty()
            DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.isEmpty()
            else -> true // 다른 경우 기본적으로 표시
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
) {
    Card(
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
                )
            }
            HeightSpacer(heightDp = 12.dp)
            TextComponent(
                text = content,
                fontSize = B2,
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
                        id = 0, personalityOption = PersonalityOption(
                            id = 0,
                            content = "",
                            personalityQuestion = PersonalityQuestion(
                                id = 0,
                                content = "",
                                responseCount = 0
                            )
                        )
                    )
                ),
                userCareers = listOf(
                    UserCareer(
                        id = 0,
                        years = 0,
                        careerType = "",
                        position = UserProfilePosition(main = "", sub = "")
                    )
                ),
                userLocations = listOf(
                    UserLocation(
                        id = 0,
                        location = UserProfileLocation(
                            id = 0,
                            province = "",
                            city = ""
                        )
                    )
                )
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailProfileCardPreview() {
    DetailProfileCard(
        icon = painterResource(id = R.drawable.icon_profile),
        title = DetailProfileCardType.CAREER_POSITION.title,
        content = DetailProfileCardType.CAREER_POSITION.content,
    )
}

enum class DetailProfileCardType(
    val title: String,
    val content: String
) {
    CAREER_POSITION(
        title = "경력/포지션",
        content = "경력과 포지션을 입력하면\n파티 합류 가능성 UP"
    ),
    LIKE_LOCATION(
        title = "관심 장소",
        content = "관심 지역을 선택하고\n파티를 추천 받아보세요!"
    ),
    LIKE_TIME(
        title = "희망 시간",
        content = "주로 작업하는 시간대는\n어떻게 되시나요?"
    ),
    CHECK_PERSONALITY(
        title = "성향 체크",
        content = "성향을 체크하고\n파티원을 추천받으세요"
    ),
}
