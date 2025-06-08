package com.party.presentation.screen.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.terms.component.DescriptionTitleArea
import com.party.presentation.screen.terms.component.TermsScaffoldArea

@Composable
fun ServiceIntroduceScreenRoute(
    navController: NavHostController,
) {
    ServiceIntroduceScreen(
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun ServiceIntroduceScreen(
    onNavigationClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TermsScaffoldArea(
                title = "서비스 소개",
                onNavigationClick = onNavigationClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
                .verticalScroll(scrollState)
        ) {
            HeightSpacer(heightDp = 24.dp)
            DescriptionTitleArea(
                title = ServiceIntroduceDescription.FIRST.title
            )
            HeightSpacer(heightDp = 20.dp)
            TextComponent(
                text = ServiceIntroduceDescription.FIRST.description1,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            HeightSpacer(heightDp = 20.dp)
            TextComponent(
                text = ServiceIntroduceDescription.FIRST.description2,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            HeightSpacer(heightDp = 4.dp)
            TextComponent(
                text = ServiceIntroduceDescription.FIRST.description3,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            HeightSpacer(heightDp = 20.dp)
            TextComponent(
                text = ServiceIntroduceDescription.FIRST.description4,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            TextComponent(
                text = ServiceIntroduceDescription.FIRST.description5,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )

            HeightSpacer(heightDp = 60.dp)
            DescriptionTitleArea(
                title = ServiceIntroduceDescription.SECOND.title
            )
            HeightSpacer(heightDp = 20.dp)
            TextComponent(
                text = ServiceIntroduceDescription.SECOND.description1,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            HeightSpacer(heightDp = 4.dp)
            TextComponent(
                text = ServiceIntroduceDescription.SECOND.description2,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            HeightSpacer(heightDp = 4.dp)
            TextComponent(
                text = ServiceIntroduceDescription.SECOND.description3,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            TextComponent(
                text = ServiceIntroduceDescription.SECOND.description4,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
            TextComponent(
                text = ServiceIntroduceDescription.SECOND.description5,
                fontSize = B2,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

private enum class ServiceIntroduceDescription(
    val title: String,
    val description1: String,
    val description2: String,
    val description3: String,
    val description4: String,
    val description5: String,
){
    FIRST(
        title = "\"파티구함\"은 프로젝트 구성원을 쉽고 효율적으로 모집할 수 있는 플랫폼입니다.",
        description1 = "프로젝트를 진행하는 그룹을 \"파티\"라고 지칭하며, 이를 바탕으로 서비스를 설계했습니다.",
        description2 = "현실에서 프로젝트를 성공적으로 수행하려면 적합한 팀원을 찾는 것이 무엇보다 중요합니다.",
        description3 = "\"파티구함\"은 모든 직군에서 필요한 인원을 모집하고, 잘 맞는 사람들과 협력할 수 있는 기회를 제공하며 프로젝트뿐만 아니라 스터디, 해커톤 등 다양한 목표에 맞춘 팀 구성을 지원합니다.",
        description4 = "세심하게 팀원을 선택해 구성한 팀은 목표를 효과적으로 달성하고 더 나은 결과를 만들어냅니다",
        description5 = "반대로. 무작위로 팀을 구성한 경우에는 목표 달성에 어려움을 겪거나 불필요한 갈등으로 와해되는 일이 발생하기도 합니다",
    ),
    SECOND(
        title = "\"파티구함\"은 이 자유를 실현할 수 있는 플랫폼입니다.",
        description1 = "회사에서는 내가 원하는 사람과 일할 기회가 제한적일 때가 많지만, 프로젝트에서는 내가 원하는 팀원들과 함께 목표를 이룰 수 있는 자유가 주어집니다.",
        description2 = "적합한 팀원을 만나고, 자신의 목표와 성향에 맞는 프로젝트를 성공적으로 수행할 수 있도록 돕는 데 초점을 맞췄습니다.",
        description3 = "· 다양한 모집 옵션: 프로젝트, 스터디, 해커톤 등 목적에 맞는 다양한 팀 구성 가능",
        description4 = "· 효율적 매칭: 팀원의 세부 정보를 확인해 적합한 인원을 선택",
        description5 = "· 협력 중심 설계: 팀워크의 중요성을 기반으로 성공적인 협업 지원",
    )
}