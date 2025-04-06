package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.PRIMARY
import com.party.domain.model.banner.Banner
import com.party.domain.model.banner.BannerItem
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun BannerArea(
    homeState: HomeState,
    onClickBanner: (String) -> Unit,
) {
    when {
        homeState.isLoadingBanner -> LoadingProgressBar()
        homeState.banner.banner.isNotEmpty() -> {
            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { homeState.banner.banner.size }
            )

            LaunchedEffect(key1 = pagerState){
                autoScrollInfinity(pagerState)
            }

            HeightSpacer(heightDp = 20.dp)

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                pageSpacing = 0.dp,
            ) { page ->
                BannerItemImage(
                    bannerItem = homeState.banner.banner[page],
                    onClickBanner = onClickBanner
                )
            }

            HeightSpacer(heightDp = 12.dp)

            BannerIndicator(pagerState = pagerState)
        }
    }
}

@Composable
private fun BannerItemImage(
    bannerItem: BannerItem,
    onClickBanner: (String) -> Unit,
) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .noRippleClickable {
                onClickBanner(bannerItem.link)
            },
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