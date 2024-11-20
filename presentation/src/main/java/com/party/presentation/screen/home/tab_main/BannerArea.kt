package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.ImageLoading
import com.party.common.snackBarMessage
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.domain.model.banner.Banner
import com.party.domain.model.banner.BannerItem
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

val contentPadding = 16.dp
val pageSpacing = 6.dp //  페이지 사이 간격

@Composable
fun BannerArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState
) {
    val bannerList by homeViewModel.bannerListState.collectAsStateWithLifecycle()

    when(bannerList){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = bannerList.data as SuccessResponse<Banner>
            val imageListResult = successResult.data?.banner ?: emptyList()

            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { imageListResult.size }
            )

            LaunchedEffect(key1 = pagerState){
                autoScrollInfinity(pagerState)
            }

            HeightSpacer(heightDp = 20.dp)

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = contentPadding),
                pageSpacing = pageSpacing,
            ) { page ->
                BannerItemImage(bannerItem = imageListResult[page])
            }

            HeightSpacer(heightDp = 12.dp)

            BannerIndicator(pagerState = pagerState)
        }
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun BannerItemImage(bannerItem: BannerItem) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp),
        imageUrl = bannerItem.image,
    )
}

@Composable
fun BannerIndicator(
    pagerState: PagerState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount){ iteration ->
            val color = if (pagerState.currentPage == iteration) PRIMARY else GRAY300
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

private suspend fun autoScrollInfinity(pagerState: PagerState){
    while (true){
        delay(2000)
        val currentPage = pagerState.currentPage
        var nextPage = currentPage + 1
        if(nextPage >= pagerState.pageCount){
            nextPage = 0
        }
        pagerState.animateScrollToPage(nextPage)
    }
}