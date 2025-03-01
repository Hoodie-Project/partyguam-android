package com.party.common.component

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BOTTOM_NAVIGATION_SELECTED_TEXT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_SELECTED_TINT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_UNSELECTED_TEXT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_UNSELECTED_TINT
import com.party.common.ui.theme.ICON_SIZE
import com.party.common.ui.theme.WHITE

@Composable
fun BottomNavigationBar(
    context: Context,
    navController: NavHostController,
    isExpandedFloatingButton: Boolean = false,
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromBottomRoute()

    AppBottomNavigationBar(
        isExpandedFloatingButton = isExpandedFloatingButton,
        show = navController.shouldShowBottomBar,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        bottomDestinations.forEach { screenItem ->
            AppBottomNavigationBarItem(
                icon = bottomIconSetting(screenItem),
                label = bottomLabelSetting(screenItem),
                onTabClick = {
                    navController.navigate(screenItem){
                        popUpTo(navController.graph.findStartDestination().route!!){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                selected = currentScreen == screenItem,
                onBack = {
                    when (currentScreen) {
                        Screens.Home -> (context as Activity).finish() // ✅ 홈에서 뒤로 가기 → 앱 종료
                        Screens.State, Screens.Profile -> {
                            // ✅ 활동 & 프로필에서 뒤로 가기 → 홈으로 이동
                            navController.navigate(Screens.Home) {
                                popUpTo(Screens.Home) { saveState = true }
                                launchSingleTop = true
                            }
                        }
                        else -> {
                            // ✅ 디테일 화면 같은 다른 화면에서 뒤로 가기 → 이전 화면으로 이동
                            if (!navController.popBackStack()) {
                                (context as Activity).finish() // 이전 화면이 없으면 앱 종료
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    show: Boolean,
    isExpandedFloatingButton: Boolean,
    content: @Composable (RowScope.() -> Unit)
) {
    Surface(
        color = WHITE,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        if (show) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isExpandedFloatingButton) 0.dp else 1.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp)
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Composable
fun RowScope.AppBottomNavigationBarItem(
    label: String,
    onTabClick: () -> Unit,
    selected: Boolean,
    icon: Painter,
    onBack: () -> Unit,
) {
    BackHandler(
        enabled = true,
        onBack = { onBack() }
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                onClick = onTabClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        DrawableIcon(
            icon = icon,
            contentDescription = "",
            iconSize = ICON_SIZE,
            tintColor = if (selected) BOTTOM_NAVIGATION_SELECTED_TINT else BOTTOM_NAVIGATION_UNSELECTED_TINT
        )

        Text(
            text = label,
            fontSize = B3,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) BOTTOM_NAVIGATION_SELECTED_TEXT else BOTTOM_NAVIGATION_UNSELECTED_TEXT,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry.fromBottomRoute()) {
        Screens.Home,
        Screens.State,
        Screens.Profile,
        Screens.Search,
        Screens.PartyDetail(partyId = 0),
        Screens.RecruitmentDetail(partyRecruitmentId = 0, partyId = 0)
        -> true

        else -> false
    }

@Composable
private fun bottomIconSetting(screens: Screens): Painter {
    return when (screens) {
        Screens.Home -> painterResource(id = R.drawable.icon_home)
        Screens.State -> painterResource(id = R.drawable.icon_state)
        Screens.Profile -> painterResource(id = R.drawable.icon_profile)
        else -> painterResource(id = R.drawable.icon_home)
    }
}

@Composable
private fun bottomLabelSetting(screens: Screens): String {
    return when(screens){
        Screens.Home -> "홈"
        Screens.State -> "활동"
        Screens.Profile -> "프로필"
        else -> ""
    }
}

fun NavBackStackEntry?.fromBottomRoute(): Screens? {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            Screens.Home::class.simpleName -> Screens.Home
            Screens.State::class.simpleName -> Screens.State
            Screens.Profile::class.simpleName -> Screens.Profile
            Screens.Search::class.simpleName -> Screens.Search
            Screens.PartyDetail::class.simpleName -> Screens.PartyDetail(partyId = 0)
            Screens.RecruitmentDetail::class.simpleName -> Screens.RecruitmentDetail(partyId = 0, partyRecruitmentId = 0)
            else -> null
        }
    }
    return null
}

val bottomDestinations = listOf(
    Screens.Home,
    Screens.State,
    Screens.Profile,
)