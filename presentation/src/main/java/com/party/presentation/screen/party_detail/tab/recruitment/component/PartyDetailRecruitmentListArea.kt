package com.party.presentation.screen.party_detail.tab.recruitment.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.no_data.NoRecruitment
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.enum.PartyAuthorityType

@Composable
fun PartyDetailRecruitmentListArea(
    authority: PartyAuthority,
    selectedCreateDataOrderByDesc: Boolean,
    list: List<PartyRecruitment>,
) {
    val sortedList = if(selectedCreateDataOrderByDesc) {
        list.sortedByDescending { it.createdAt }
    } else {
        list.sortedBy { it.createdAt }
    }

    println("sortedList: $sortedList")

    if(sortedList.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
        ) {
            itemsIndexed(
                items = sortedList,
                key = { index, _ ->
                    index
                }
            ){ index, item ->
                PartyDetailRecruitmentListItem(
                    partyRecruitment = item
                )

                if(index == sortedList.size-1){
                    if(authority.authority == PartyAuthorityType.MASTER.authority){

                        PartyDetailAddRecruitCard()
                    }
                }
            }
        }
    }else {
        HeightSpacer(heightDp = 60.dp)
        NoRecruitmentArea(
            authority = authority
        )
    }
}

@Composable
fun NoRecruitmentArea(
    authority: PartyAuthority
) {
    if (authority.authority == PartyAuthorityType.MASTER.authority) {
        PartyDetailAddRecruitCard()
    } else {
        NoRecruitment()
    }
}

@Composable
fun PartyDetailAddRecruitCard() {
    Card(
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
                    icon = painterResource(id = R.drawable.add_circle),
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
            userId = 1,
            authority = "master"
        )
    )
}

@Composable
fun PartyDetailRecruitmentListItem(
    partyRecruitment: PartyRecruitment,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            colors = CardDefaults.cardColors(
                containerColor = WHITE,
            ),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            border = BorderStroke(1.dp, GRAY100),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                TextComponent(
                    text = "모집일 ${convertIsoToCustomDateFormat(partyRecruitment.createdAt)}",
                    fontSize = B3,
                    textColor = GRAY500
                )
                HeightSpacer(heightDp = 8.dp)
                TextComponent(
                    text = "${partyRecruitment.position.main} | ${partyRecruitment.position.sub}",
                    fontSize = T3,
                    fontWeight = FontWeight.SemiBold,
                )
                HeightSpacer(heightDp = 20.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RecruitingCountArea(
                        text = "모집중",
                        number = "${partyRecruitment.recruitedCount} / ${partyRecruitment.recruitingCount}",
                        textColor = RED
                    )
                    WidthSpacer(widthDp = 24.dp)
                    RecruitingCountArea(
                        text = "지원자",
                        number = "${partyRecruitment.applicationCount}",
                        textColor = PRIMARY
                    )
                }
            }
        }
        HeightSpacer(heightDp = 12.dp)
    }
}

@Composable
fun RecruitingCountArea(
    text: String,
    number: String,
    textColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = text,
            fontSize = T3,
        )
        WidthSpacer(widthDp = 4.dp)
        TextComponent(
            text = number,
            fontSize = T3,
            textColor = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailRecruitmentListItemPreview(){
    PartyDetailRecruitmentListItem(
        partyRecruitment = PartyRecruitment(
            id = 2293,
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
    )
}

@Preview(showBackground = true)
@Composable
fun PartyDetailRecruitmentListAreaPreview() {
    PartyDetailRecruitmentListArea(
        authority = PartyAuthority(
            userId = 1,
            authority = "master"
        ),
        selectedCreateDataOrderByDesc = true,
        list = listOf(
            PartyRecruitment(
                id = 2293,
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z"
            ),
            PartyRecruitment(
                id = 2293,
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
    )
}