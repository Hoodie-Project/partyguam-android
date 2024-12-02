package com.party.navigation

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.blur
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
import com.party.common.component.icon.DrawableIcon
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
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
    ) {
        bottomDestinations.forEach { screenItem ->
            AppBottomNavigationBarItem(
                icon = bottomIconSetting(screenItem),
                label = screenItem.name,
                onTabClick = {
                    navController.navigate(screenItem.screen){
                        popUpTo(navController.graph.findStartDestination().route!!){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                selected = currentScreen == screenItem,
                onBack = { if (currentScreen == BottomBarScreen.Home) (context as Activity).finish() else navController.navigate(Screens.Home) },
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
            .height(86.dp)
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
                        .height(if(isExpandedFloatingButton) 0.dp else 1.dp),
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
        BottomBarScreen.Home,
        BottomBarScreen.State,
        BottomBarScreen.Profile
        -> true
    }

@Composable
private fun bottomIconSetting(screens: BottomBarScreen): Painter {
    return when (screens) {
        BottomBarScreen.Home -> painterResource(id = R.drawable.home_icon)
        BottomBarScreen.State -> painterResource(id = R.drawable.state_icon)
        BottomBarScreen.Profile -> painterResource(id = R.drawable.profile_icon)
    }
}

fun NavBackStackEntry?.fromBottomRoute(): BottomBarScreen{
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            BottomBarScreen.Home::class.simpleName -> BottomBarScreen.Home
            BottomBarScreen.State::class.simpleName -> BottomBarScreen.State
            BottomBarScreen.Profile::class.simpleName -> BottomBarScreen.Profile
            else -> BottomBarScreen.Home
        }
    }
    return BottomBarScreen.Home
}