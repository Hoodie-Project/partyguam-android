package com.party.presentation.screen.state.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.RecruitmentListItem3
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.no_data.NoDataColumn
import com.party.common.noRippleClickable
import com.party.common.ui.theme.GRAY500
import com.party.presentation.screen.state.MyPartyState

@Composable
fun MyRecruitmentArea(
    myPartyState: MyPartyState,
    onChangeOrderBy: (Boolean) -> Unit,
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("전체") }

    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        SelectCategoryArea(
            categoryList = listOf("전체", "검토중", "응답대기", "수락", "거절"),
            selectedCategory = selectedCategory,
            onClick = { selectedCategory = it },
        )

        RecruitmentStateAndOrderByArea(
            orderByDesc = myPartyState.orderByRecruitmentDateDesc,
            onChangeOrderBy = onChangeOrderBy,
        )

        HeightSpacer(heightDp = 4.dp)

        when {
            myPartyState.isMyRecruitmentLoading -> {
                LoadingProgressBar()
            }
            myPartyState.myRecruitmentList.partyApplications.isEmpty() -> {
                NoDataColumn(
                    title = "지원한 파티가 없어요",
                    modifier =
                        Modifier
                            .padding(top = 50.dp),
                )
            }
            else -> {
                MyRecruitmentList(
                    myPartyState = myPartyState,
                    onRefusal = onRefusal,
                    onAccept = onAccept,
                )
            }
        }
    }
}

@Composable
private fun MyRecruitmentList(
    myPartyState: MyPartyState,
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = myPartyState.myRecruitmentList.partyApplications,
            key = { index, _ ->
                index
            },
        ) { _, item ->
            RecruitmentListItem3(
                date = item.createdAt,
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
            onClick = {},
        )

        OrderByCreateDtChip(
            text = "지원일순",
            orderByDesc = orderByDesc,
            onChangeSelected = { onChangeOrderBy(it) },
        )
    }
}

@Composable
private fun RecruitmentState(onClick: () -> Unit) {
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
                    .noRippleClickable {
                        onClick()
                    },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyRecruitmentAreaPreview() {
    MyRecruitmentArea(
        myPartyState = MyPartyState(),
        onChangeOrderBy = {},
        onRefusal = {},
        onAccept = {},
    )
}
