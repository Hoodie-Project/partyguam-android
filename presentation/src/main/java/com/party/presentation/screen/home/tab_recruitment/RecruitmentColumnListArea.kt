package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.RecruitmentListItem2
import com.party.common.component.no_data.NoDataColumn
import com.party.common.snackBarMessage
import com.party.domain.model.party.RecruitmentItem
import com.party.domain.model.party.RecruitmentList
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecruitmentColumnListArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    selectedCreateDataOrderByDesc: Boolean,
    selectedPartyType: MutableList<String>,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val listState = rememberLazyListState()
    val isFabVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    sharedViewModel.isScrollRecruitmentArea = isFabVisible.value

    LaunchedEffect(Unit) {
        homeViewModel.getRecruitmentList(page = 1, size = 20, sort = "createdAt", order = "DESC", titleSearch = null)
    }

    LaunchedEffect(Unit) {
        sharedViewModel.scrollToUp.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    val getRecruitmentListState by homeViewModel.getRecruitmentListState.collectAsStateWithLifecycle()
    val recruitmentListResponse = getRecruitmentListState.data

    when (getRecruitmentListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = recruitmentListResponse as SuccessResponse<RecruitmentList>
            val resultList = successResult.data?.partyRecruitments ?: emptyList()

            if(resultList.isEmpty()){
                HeightSpacer(heightDp = 76.dp)
                NoDataColumn(title = "모집공고가 없어요.")
            }else {
                val sortedList = if(selectedCreateDataOrderByDesc) resultList.sortedByDescending { it.createdAt } else resultList?.sortedBy { it.createdAt }
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(
                        items = test(sortedList, selectedPartyType) ?: emptyList() ,
                        key = { index, _ ->
                            index
                        }
                    ){_, item ->
                        RecruitmentListItem2(
                            id = item.id,
                            partyId = item.party.id,
                            imageUrl = item.party.image,
                            category = item.party.partyType.type,
                            title = item.party.title,
                            main = item.position.main,
                            sub = item.position.sub,
                            recruitingCount = item.recruitingCount,
                            recruitedCount = item.recruitedCount,
                            onClick = onRecruitmentItemClick
                        )
                    }
                }
            }
        }
        is UIState.Error -> {}
        is UIState.Exception -> snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
    }
}

fun test(
    sortedList: List<RecruitmentItem>?,
    selectedPartyType: MutableList<String>,
): List<RecruitmentItem>? {
    return if(selectedPartyType.isNotEmpty()){
        sortedList?.filter { selectedPartyType.contains(it.party.partyType.type) }
    } else {
        sortedList
    }
}