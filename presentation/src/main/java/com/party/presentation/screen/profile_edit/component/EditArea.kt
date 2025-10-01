package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.guam.design.GRAY500
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.T2
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile.UserProfileState

val editList = listOf(
    "경력/포지션",
    "관심 지역",
    "희망 시간",
    "성향",
    "이력서 및 포트폴리오 링크"
)

@Composable
fun EditArea(
    userProfileState: UserProfileState,
    onGotoProfileEditCareer: () -> Unit,
    onGotoProfileEditLocation: () -> Unit,
    onGotoProfileEditTime: () -> Unit,
    onGotoProfileEditTendency: () -> Unit,
    onGotoProfileEditPortfolio: () -> Unit,
    onGotoWebView: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING_SIZE)
    ) {
        HeightSpacer(heightDp = 32.dp)
        EditAreaItem(
            text = editList[0],
            onClick = onGotoProfileEditCareer,
            content = {
                UserProfileMainAndSubPositionArea(
                    userCareers = userProfileState.userProfile.userCareers
                )
            }
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[1],
            onClick = onGotoProfileEditLocation,
            content = {
                UserLocationArea(
                    userLocationList = userProfileState.userProfile.userLocations
                )
            }
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[2],
            onClick = onGotoProfileEditTime,
            content = {
                val userPersonalityList = userProfileState.userProfile.userPersonalities.filter { it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id }
                UserTimeArea(
                    userPersonalityList = userPersonalityList
                )
            }
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[3],
            onClick = onGotoProfileEditTendency,
            content = {
                val userTendencyList = userProfileState.userProfile.userPersonalities.filter { it.personalityOption.personalityQuestion.id != PersonalityType.TIME.id }
                UserTendencyArea(
                    userTendencyList = userTendencyList
                )
            }
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[4],
            onClick = onGotoProfileEditPortfolio,
            content = {
                val portfolio = userProfileState.userProfile.portfolio
                if(!portfolio.isNullOrBlank()){
                    ProfileEditPortfolioArea(
                        portfolioLink = portfolio,
                        onClick = { onGotoWebView(portfolio)}
                    )
                }
            }
        )
        HeightSpacer(heightDp = 60.dp)

        // 참여파티목록
        val text = buildAnnotatedString {
            append("참여 파티 목록 ")
            withStyle(
                SpanStyle(
                    color = PRIMARY
                )
            ){
                append("${userProfileState.myPartyList.total}")
            }
            append("건")
        }
        Text(
            text = text,
            fontSize = fs(T2),
            fontWeight = FontWeight.SemiBold
        )

        HeightSpacer(heightDp = 60.dp)
    }
}

@Composable
private fun EditAreaItem(
    text: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .noRippleClickable { onClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                text = text,
                fontSize = T2,
                fontWeight = FontWeight.SemiBold,
            )

            DrawableIcon(
                icon = painterResource(id = R.drawable.icon_arrow_right),
                contentDescription = "",
                tintColor = GRAY500,
            )
        }

        HeightSpacer(heightDp = 20.dp)
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun EditAreaPreview() {
    EditArea(
        userProfileState = UserProfileState(),
        onGotoProfileEditCareer = {},
        onGotoProfileEditLocation = {},
        onGotoProfileEditTime = {},
        onGotoProfileEditTendency = {},
        onGotoProfileEditPortfolio = {},
        onGotoWebView = {},
    )
}
