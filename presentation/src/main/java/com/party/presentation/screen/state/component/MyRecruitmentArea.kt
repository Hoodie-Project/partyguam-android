package com.party.presentation.screen.state.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.RecruitmentListItem3
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.no_data.NoDataColumn
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.recruitment.PartyApplication
import com.party.presentation.enum.RecruitmentStatusType
import com.party.presentation.screen.party_create.component.TriangleEdge
import com.party.presentation.screen.state.MyPartyState

@Composable
fun MyRecruitmentArea(
    listState: LazyListState,
    myPartyState: MyPartyState,
    onSelectRecruitmentTab: (String) -> Unit,
    onShowHelpCard: (Boolean) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {

    val filteredList = if (myPartyState.selectedRecruitmentStatus == "전체") {
        myPartyState.myRecruitmentList.partyApplications // 전체 리스트 반환
    } else {
        myPartyState.myRecruitmentList.partyApplications.filter {
            it.status == RecruitmentStatusType.fromDisplayText(myPartyState.selectedRecruitmentStatus)
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SelectCategoryArea(
            categoryList = listOf("전체", "검토중", "응답대기", "수락", "거절"),
            selectedCategory = myPartyState.selectedRecruitmentStatus,
            onClick = onSelectRecruitmentTab,
        )

        RecruitmentStateAndOrderByArea(
            orderByDesc = myPartyState.orderByRecruitmentDateDesc,
            onShowHelpCard = { onShowHelpCard(true) },
            onChangeOrderBy = onChangeOrderBy,
        )

        HeightSpacer(heightDp = 4.dp)

        Box {
            when {
                myPartyState.isMyRecruitmentLoading -> { LoadingProgressBar() }
                filteredList.isEmpty() -> {
                    NoDataColumn(
                        title = "지원한 파티가 없어요",
                        modifier =
                        Modifier
                            .padding(top = 50.dp),
                    )
                }
                else -> {
                    MyRecruitmentList(
                        listState = listState,
                        filteredList = filteredList,
                        onRefusal = onRefusal,
                        onAccept = onAccept,
                    )
                }
            }

            if(myPartyState.isShowHelpCard){
                StateHelpCard(
                    onCloseHelpCard = { onShowHelpCard(false) }
                )
            }
        }
    }
}

@Composable
private fun MyRecruitmentList(
    listState: LazyListState,
    filteredList: List<PartyApplication>,
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = filteredList,
            key = { index, _ ->
                index
            },
        ) { _, item ->
            RecruitmentListItem3(
                date = item.createdAt,
                status = RecruitmentStatusType.fromStatus(item.status).toDisplayText(),
                statusColor = RecruitmentStatusType.fromStatus(item.status).toColor(),
                partyType = item.partyRecruitment.party.partyType.type,
                title = item.partyRecruitment.party.title,
                main = item.partyRecruitment.position.main,
                sub = item.partyRecruitment.position.sub,
                content = item.message,
                onClick = {},
                onRefusal = onRefusal,
                onAccept = onAccept,
            )
        }
    }
}

@Composable
private fun RecruitmentStateAndOrderByArea(
    orderByDesc: Boolean,
    onShowHelpCard: () -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        RecruitmentState(
            onShowHelpCard = onShowHelpCard,
        )

        OrderByCreateDtChip(
            text = "지원일순",
            orderByDesc = orderByDesc,
            onChangeSelected = { onChangeOrderBy(it) },
        )
    }
}

@Composable
private fun RecruitmentState(
    onShowHelpCard: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "지원상태")
        WidthSpacer(widthDp = 2.dp)
        DrawableIcon(
            icon = painterResource(id = R.drawable.help),
            contentDescription = "help",
            tintColor = GRAY500,
            modifier =
            Modifier
                .size(16.dp)
                .noRippleClickable { onShowHelpCard() },
        )
    }
}

@Composable
private fun StateHelpCard(
    onCloseHelpCard: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .offset(x = 66.dp)
                .width(10.dp)
                .height(5.dp)
                .background(color = GRAY500, shape = TriangleEdge()),
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ,
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = GRAY500,
            ),
            border = BorderStroke(1.dp, GRAY500),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextComponent(
                        text = "상태에 대해 알려드릴게요",
                        fontSize = T3,
                        textColor = WHITE,
                        fontWeight = FontWeight.Bold,
                    )
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.close2),
                        iconColor = WHITE,
                        iconSize = 16.dp,
                        contentDescription = "close",
                        onClick = onCloseHelpCard
                    )
                }
                HeightSpacer(heightDp = 12.dp)
                TextComponent(
                    text = "검토중 : 파티장이 지원서 확인 전이에요.",
                    fontSize = B2,
                    textColor = WHITE,
                )
                TextComponent(
                    text = "응답대기 : 파티장 수락 후, 지원자의 수락을 기다려요.",
                    fontSize = B2,
                    textColor = WHITE,
                )
                TextComponent(
                    text = "수락 : 파티장과 지원자 모두 수락했어요.",
                    fontSize = B2,
                    textColor = WHITE,
                )
                TextComponent(
                    text = "거절 : 파티장 또는 지원자가 거절했어요.",
                    fontSize = B2,
                    textColor = WHITE,
                )

                HeightSpacer(heightDp = 20.dp)
                TextComponent(
                    text = "일주일 이내 수락하지 않으면 거절됩니다.",
                    fontSize = B2,
                    textColor = PRIMARY,
                )

                HeightSpacer(heightDp = 20.dp)
                TextComponent(
                    text = "지원목록은 지원일 기준 30일까지 보관됩니다.",
                    fontSize = B2,
                    textColor = WHITE,
                )

                HeightSpacer(heightDp = 12.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyRecruitmentAreaPreview() {
    MyRecruitmentArea(
        listState = LazyListState(),
        myPartyState = MyPartyState(),
        onSelectRecruitmentTab = {},
        onShowHelpCard = {},
        onChangeOrderBy = {},
        onRefusal = {},
        onAccept = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun StateHelpCardPreview() {
    StateHelpCard(
        onCloseHelpCard = {},
    )
}
