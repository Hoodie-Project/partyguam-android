package com.party.presentation.screen.join.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.join.action.JoinAction
import com.party.presentation.screen.join.component.JoinScreenButton
import com.party.presentation.screen.join.component.JoinTitleSection
import com.party.presentation.screen.join.state.JoinState
import com.party.presentation.screen.join.viewmodel.JoinViewModel
import kotlin.math.roundToInt

@Composable
fun JoinBirthDayScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    joinViewModel: JoinViewModel,
) {
    val joinState by joinViewModel.state.collectAsStateWithLifecycle()

    JoinBirthDayScreen(
        joinState = joinState,
        snackBarHostState = snackBarHostState,
        onNavigateBack = { navController.popBackStack() },
        onAction = { joinViewModel.onAction(action = it) },
        onGotoJoinGender = { navController.navigate(Screens.JoinGender) }
    )
}



@Composable
private fun JoinBirthDayScreen(
    joinState: JoinState,
    snackBarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit = {},
    onAction: (JoinAction) -> Unit = {},
    onGotoJoinGender: () -> Unit = {},
){
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            JoinTitleSection(
                onNavigateBack = onNavigateBack,
                actionIcon = {
                    Text(
                        text = "3/4",
                        fontSize = B2,
                        color = GRAY500,
                        modifier = Modifier.padding(end = 20.dp),
                    )
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                ScreenExplainArea(
                    mainExplain = "${joinState.userNickName}님의\n생년월일을 알려주세요",
                    subExplain = "프로필에서 노출 여부를 설정할 수 있어요."
                )

                HeightSpacer(heightDp = 40.dp)

                BirthDatePicker(
                    yearRange = 1980..2025,
                    initialYear = 1990,
                    initialMonth = 12,
                    initialDay = 30,
                    onDateChange = { y, m, d ->
                        val formatted = "${y}-${m.toString().padStart(2, '0')}-${d.toString().padStart(2, '0')}"
                        onAction(JoinAction.OnInputBirthDay(formatted))
                    }
                )
            }

            JoinScreenButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                ,
                buttonText = "다음",
                buttonTextColor = BLACK,
                buttonContainerColor = PRIMARY,
                buttonBorderColor = PRIMARY,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onGotoJoinGender
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}



@Composable
fun WheelPicker(
    items: List<String>,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    itemHeight: Dp = 44.dp,
    initialIndex: Int = 0,
    highlightRadius: Dp = 4.dp,
    onSelected: (Int) -> Unit
) {
    require(visibleCount % 2 == 1) { "visibleCount must be odd" }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val fling = rememberSnapFlingBehavior(lazyListState = listState)

    // px 계산 (KMP 안전)
    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }

    // 중앙 인덱스 계산
    val selectedIndex by remember {
        derivedStateOf {
            val raw = listState.firstVisibleItemIndex +
                    (listState.firstVisibleItemScrollOffset / itemHeightPx).roundToInt()
            raw.coerceIn(0, items.lastIndex.coerceAtLeast(0))
        }
    }
    LaunchedEffect(selectedIndex) { onSelected(selectedIndex) }

    val totalHeight = itemHeight * visibleCount

    Box(
        modifier = modifier.height(totalHeight),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .shadow(4.dp, RoundedCornerShape(highlightRadius), clip = false)
                .clip(RoundedCornerShape(highlightRadius))
                .background(Color.White)
        )

        LazyColumn(
            state = listState,
            flingBehavior = fling,
            contentPadding = PaddingValues(vertical = (totalHeight - itemHeight) / 2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { idx ->
                val isSelected = idx == selectedIndex
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[idx],
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isSelected) BLACK else GRAY400,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun BirthDatePicker(
    yearRange: IntRange = 1900..2025,
    initialYear: Int = 1995,
    initialMonth: Int = 12,
    initialDay: Int = 30,
    onDateChange: (y: Int, m: Int, d: Int) -> Unit = { _,_,_ -> }
) {
    var year by rememberSaveable { mutableIntStateOf(initialYear.coerceIn(yearRange)) }
    var month by rememberSaveable { mutableIntStateOf(initialMonth.coerceIn(1..12)) }
    var day by rememberSaveable { mutableIntStateOf(initialDay) }

    // 월/년 바뀌면 일수 보정
    LaunchedEffect(year, month) {
        val maxDay = daysInMonth(year, month)
        if (day > maxDay) day = maxDay
    }

    // 연/월/일이 바뀔 때마다 콜백 호출
    LaunchedEffect(year, month, day) {
        onDateChange(year, month, day)
    }

    // 표시 리스트
    val years  = remember(yearRange) { yearRange.map { "${it}년" } }
    val months = remember { (1..12).map { "${it}월" } }
    val days   = remember(year, month) { (1..daysInMonth(year, month)).map { "${it}일" } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 년
        WheelPicker(
            items = years,
            initialIndex = year - yearRange.first,
            itemHeight = 52.dp,
            modifier = Modifier.weight(1f)
        ) { idx -> year = yearRange.first + idx }

        // 월
        WheelPicker(
            items = months,
            initialIndex = month - 1,
            itemHeight = 52.dp,
            modifier = Modifier.width(78.dp)
        ) { idx -> month = idx + 1 }

        // 일
        WheelPicker(
            items = days,
            initialIndex = (day - 1).coerceAtMost(days.lastIndex),
            itemHeight = 52.dp,
            modifier = Modifier.width(78.dp)
        ) { idx -> day = idx + 1 }
    }
}

private fun isLeap(y: Int) =
    (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)

private fun daysInMonth(y: Int, m: Int) = when (m) {
    1,3,5,7,8,10,12 -> 31
    4,6,9,11 -> 30
    2 -> if (isLeap(y)) 29 else 28
    else -> 30
}

@Preview(showBackground = true)
@Composable
private fun PreviewJoinBirthDayScreen() {
    JoinBirthDayScreen(
        joinState = JoinState(),
        snackBarHostState = SnackbarHostState()
    )
}