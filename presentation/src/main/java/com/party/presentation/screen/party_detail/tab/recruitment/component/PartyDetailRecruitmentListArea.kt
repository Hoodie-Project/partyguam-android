package com.party.presentation.screen.party_detail.tab.recruitment.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.RecruitmentListItem4
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.no_data.NoDataColumn
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.presentation.enum.PartyAuthorityType

@Composable
fun PartyDetailRecruitmentListArea(
    authority: PartyAuthority,
    list: List<PartyRecruitment>,
    onAddRecruitment: () -> Unit,
    onClickRecruitment: (Int) -> Unit,
) {

    if(list.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { index, _ ->
                    index
                }
            ){ index, item ->
                RecruitmentListItem4(
                    status = item.status,
                    createdAt = item.createdAt,
                    main = item.position.main,
                    sub = item.position.sub,
                    recruitedCount = item.recruitedCount,
                    recruitingCount = item.recruitingCount,
                    applicationCount = item.applicationCount,
                    onClick = { onClickRecruitment(item.id) },
                    onPartyRecruitmentCompleted = {}
                )

                if(index == list.size-1){
                    if(authority.authority == PartyAuthorityType.MASTER.authority){

                        HeightSpacer(12.dp)
                        PartyDetailAddRecruitCard(
                            onAddRecruitment = onAddRecruitment
                        )
                    }
                }
            }
        }
    }else {
        NoRecruitmentArea(
            authority = authority,
            onAddRecruitment = onAddRecruitment
        )
        HeightSpacer(heightDp = 20.dp)
    }
}

@Composable
fun NoRecruitmentArea(
    authority: PartyAuthority,
    onAddRecruitment: () -> Unit,
) {
    if (authority.authority == PartyAuthorityType.MASTER.authority) {
        PartyDetailAddRecruitCard(
            onAddRecruitment = onAddRecruitment
        )
    } else {
        NoDataColumn(title = "모집 공고가 없어요.")
    }
}

@Composable
fun PartyDetailAddRecruitCard(
    onAddRecruitment: () -> Unit,
) {
    Card(
        onClick = onAddRecruitment,
        modifier = Modifier
            .fillMaxWidth()
            .height(127.dp),
        border = BorderStroke(1.dp, GRAY100),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DrawableIcon(
                    icon = painterResource(id = R.drawable.icon_add_circle),
                    contentDescription = "add_circle",
                    modifier = Modifier
                        .size(32.dp),
                    tintColor = GRAY400
                )
                HeightSpacer(heightDp = 4.dp)
                TextComponent(
                    text = "모집공고 추가하기",
                    fontSize = B2,
                    fontWeight = FontWeight.Bold,
                    textColor = GRAY500
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoRecruitmentAreaPreview() {
    NoRecruitmentArea(
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onAddRecruitment = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailRecruitmentListAreaPreview() {
    PartyDetailRecruitmentListArea(
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        list = listOf(
            PartyRecruitment(
                id = 2293,
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                status = "active",
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z"
            ),
            PartyRecruitment(
                id = 2293,
                status = "active",
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z"
            )
        ),
        onAddRecruitment = {},
        onClickRecruitment = {}
    )
}