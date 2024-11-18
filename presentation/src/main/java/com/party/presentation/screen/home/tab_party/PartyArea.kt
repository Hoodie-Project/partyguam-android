package com.party.presentation.screen.home.tab_party

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.NetworkImageLoad
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.TextComponent
import com.party.common.UIState
import com.party.common.WidthSpacer
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.HOME_PARTY_LIST_CATEGORY_BACKGROUND
import com.party.common.ui.theme.HOME_PARTY_LIST_CATEGORY_TEXT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyListResponse
import com.party.presentation.enum.PartyType
import com.party.presentation.screen.home.HomeViewModel
import com.party.presentation.screen.home.tab_main.PartyItemBottomAreaDescription
import com.party.presentation.screen.home.tab_party.component.FilterArea
import com.party.presentation.screen.home.tab_party.component.FilterDate
import com.party.presentation.screen.home.tab_recruitment.PartyTypeModal
import com.party.presentation.screen.home.tab_recruitment.validSelectedPartyType

@Composable
fun PartyArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 선택된 파티 타입 리스트
    val selectedPartyTypeList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    var checked by remember { mutableStateOf(true) }

    // 등록일 순 내림 차순
    var selectedCreateDataOrderByDesc by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        homeViewModel.getPartyList(page = 1, size = 10, sort = "createdAt", order = "DESC")
    }

    val getPartyListState by homeViewModel.getPartyListState.collectAsState()
    val partyListResponse = getPartyListState.data

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FilterArea(
            checked = checked,
            onToggle = { checked = it },
            isPartyTypeFilterClick = {
                isPartyTypeSheetOpen = it
            }
        )

        FilterDate(
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeOrderBy = { selectedCreateDataOrderByDesc = it }
        )
        HeightSpacer(heightDp = 12.dp)

        when(getPartyListState){
            is UIState.Idle -> {}
            is UIState.Loading -> { LoadingProgressBar() }
            is UIState.Success -> {
                val successResult = partyListResponse as SuccessResponse<PartyListResponse>
                PartyListArea(
                    partyListResponse = successResult.data,
                    selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
                    checked = checked,
                )
            }
            is UIState.Error -> {}
            is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
        }
    }

    if(isPartyTypeSheetOpen){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = selectedPartyTypeList,
            onClick = { validSelectedPartyType(selectedPartyTypeList, it) },
            onModelClose = { isPartyTypeSheetOpen = false },
            onReset = { selectedPartyTypeList.clear() },
            onApply = {
                isPartyTypeSheetOpen = false
                homeViewModel.getPartyList(
                    page = 1,
                    size = 20,
                    sort = "createdAt",
                    order = "DESC",
                    partyTypes = convertToIntList(selectedPartyTypeList)
                )
            }
        )
    }
}

fun convertToIntList(selectedPartyTypeList: MutableList<String>): List<Int> {
    return selectedPartyTypeList.mapNotNull { name ->
        PartyType.entries.find { it.type == name }?.id
    }
}

@Composable
private fun PartyListArea(
    partyListResponse: PartyListResponse?,
    selectedCreateDataOrderByDesc: Boolean,
    checked: Boolean,
) {
    val list = partyListResponse?.parties?.let { parties ->
        if (selectedCreateDataOrderByDesc) {
            parties.sortedByDescending { it.createdAt } // 내림차순 정렬
        } else {
            parties.sortedBy { it.createdAt } // 오름차순 정렬
        }
    } ?: emptyList()

    val list2 = list.filter {
        if (checked) {
            it.tag == "진행중"
        } else {
            it.tag != "진행중"
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(
            items = list2,
            key = { index, _ ->
                index
            }
        ){_, item ->
            PartyItem(
                partyItemResponse = item
            )
        }
    }
}

@Composable
private fun PartyItem(
    partyItemResponse: PartyItemResponse,
){
    Card(
        onClick = {},
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .width(161.dp)
                .height(248.dp)
                .padding(12.dp),
        ) {
            PartyItemTopArea(
                imageUrl = partyItemResponse.image,
            )
            HeightSpacer(heightDp = 12.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CategoryState(
                    category = partyItemResponse.tag,
                    containerColor = if(partyItemResponse.tag == "진행중") Color(0xFFD5F0E3) else GRAY600,
                    contentColor = if(partyItemResponse.tag == "진행중") Color(0xFF016110) else WHITE
                )
                WidthSpacer(widthDp = 4.dp)
                CategoryState(
                    category = partyItemResponse.partyType.type,
                    containerColor = HOME_PARTY_LIST_CATEGORY_BACKGROUND,
                    contentColor = HOME_PARTY_LIST_CATEGORY_TEXT
                )
            }
            HeightSpacer(heightDp = 4.dp)
            TextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                text = partyItemResponse.title,
                fontSize = T3,
                fontWeight = FontWeight.SemiBold,
                textAlign = Alignment.TopStart,
            )
            HeightSpacer(heightDp = 12.dp)
            PartyItemBottomAreaDescription(
                recruitmentCount = partyItemResponse.recruitmentCount,
            )
        }
    }
}

@Composable
private fun PartyItemTopArea(
    imageUrl: String? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(107.dp),
        border = BorderStroke(1.dp, GRAY100),
        shape = RoundedCornerShape(MEDIUM_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .width(137.dp)
                .height(107.dp)
        ){
            NetworkImageLoad(
                url = imageUrl,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun CategoryState(
    category: String,
    containerColor: Color,
    contentColor: Color,
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = category,
                fontSize = B3,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}