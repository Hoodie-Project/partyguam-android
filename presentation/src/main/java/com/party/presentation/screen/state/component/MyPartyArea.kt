package com.party.presentation.screen.state.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.component.PartyListItem2
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.component.no_data.NoDataColumn
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.party.Party
import com.party.domain.model.user.party.PartyType
import com.party.domain.model.user.party.PartyUser
import com.party.domain.model.user.party.Position
import com.party.presentation.screen.state.MyPartyState

@Composable
fun MyPartyArea(
    myPartyState: MyPartyState,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("전체") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SelectCategoryArea(
            categoryList = listOf("전체", "진행중", "종료"),
            selectedCategory = selectedCategory,
            onClick = { selectedCategory = it }
        )

        JoinDataOrderBy(
            orderByDesc = myPartyState.orderByDesc,
            onChangeOrderBy = onChangeOrderBy
        )

        HeightSpacer(heightDp = 4.dp)

        when {
            myPartyState.isMyPartyLoading -> { LoadingProgressBar() }
            myPartyState.myPartyList.partyUsers.isEmpty() -> {
                NoDataColumn(
                    title = "파티가 없어요",
                    modifier = Modifier
                        .padding(top = 50.dp)
                )
            }
            else -> {
                MyPartyList(
                    myPartyState = myPartyState
                )
            }
        }
    }
}

@Composable
private fun MyPartyList(
    myPartyState: MyPartyState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = myPartyState.myPartyList.partyUsers,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            PartyListItem2(
                imageUrl = item.party.image,
                active = "",
                partyType = item.party.partyType.type,
                title = item.party.title,
                main = item.position.main,
                sub = item.position.sub,
                onClick = {}
            )
        }
    }
}

@Composable
private fun JoinDataOrderBy(
    orderByDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.End
    ) {
        OrderByCreateDtChip(
            text = "참여일순",
            orderByDesc = orderByDesc,
            onChangeSelected = { onChangeOrderBy(it) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPartyAreaPreview() {
    MyPartyArea(
        myPartyState = MyPartyState(
            isMyPartyLoading = false,
            myPartyList = MyParty(0, emptyList())
        ),
        onChangeOrderBy = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPartyAreaPreview1() {
    MyPartyArea(
        myPartyState = MyPartyState(
            isMyPartyLoading = true,
            myPartyList = MyParty(0, emptyList())
        ),
        onChangeOrderBy = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPartyAreaPreview2() {
    MyPartyArea(
        myPartyState = MyPartyState(
            isMyPartyLoading = false,
            myPartyList = MyParty(
                total = 2,
                partyUsers =  listOf(
                    PartyUser(
                        id = 9138,
                        createdAt = "2024-11-29T16:30:30.171Z",
                        position = Position(main = "개발자", sub = "안드로이드"),
                        party = Party(
                            id = 7464,
                            title = "파티 제목입니다.!!",
                            image = "dictum",
                            partyType = PartyType(type = "포트폴리오")
                        )
                    )
                )
            )
        ),
        onChangeOrderBy = {}
    )
}