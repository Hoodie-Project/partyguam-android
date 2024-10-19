package com.party.presentation.screen.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import kotlinx.coroutines.delay

val contentPadding = 16.dp
val pageSpacing = 6.dp //  페이지 사이 간격

@Composable
fun BannerArea() {
    val imageList = listOf(
        painterResource(id = R.drawable.testbanner),
        painterResource(id = R.drawable.testbanner2),
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { imageList.size }
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
        BannerItem(image = imageList[page])
    }

    HeightSpacer(heightDp = 12.dp)

    BannerIndicator(pagerState = pagerState)
}

@Composable
fun BannerItem(image: Painter) {
    Image(
        painter = image,
        contentDescription = "banner",
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
        //.aspectRatio(2f) // 가로세로 비율 2:1
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