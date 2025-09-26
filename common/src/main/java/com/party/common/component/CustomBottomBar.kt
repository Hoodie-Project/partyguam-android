package com.party.common.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import com.party.common.MainTab
import com.party.common.Screens
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B3
import com.party.guam.design.BOTTOM_NAVIGATION_SELECTED_TEXT
import com.party.guam.design.BOTTOM_NAVIGATION_SELECTED_TINT
import com.party.guam.design.BOTTOM_NAVIGATION_UNSELECTED_TEXT
import com.party.guam.design.BOTTOM_NAVIGATION_UNSELECTED_TINT
import com.party.guam.design.ICON_SIZE
import com.party.guam.design.WHITE

@Composable
fun BottomNavigationBar(
    currentMainTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    navController: NavHostController,
    isExpandedFloatingButton: Boolean = false,
){
    AppBottomNavigationBar(
        isExpandedFloatingButton = isExpandedFloatingButton,
        show = navController.shouldShowBottomBar,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        bottomDestinations.forEach { mainTab ->
            AppBottomNavigationBarItem(
                icon = mainTab.tabIcon,
                label = mainTab.tabName,
                selected = currentMainTab == mainTab,
                onTabClick = { onTabClick(mainTab) },
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
    icon: Int,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .noRippleClickable(
                onClick = onTabClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        DrawableIcon(
            icon = painterResource(icon),
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

fun NavBackStackEntry?.toMainTab(): MainTab = when {
    this?.destination?.hasRoute<Screens.State>()  == true -> MainTab.Active
    this?.destination?.hasRoute<Screens.Profile>() == true -> MainTab.Profile
    else -> MainTab.Home
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry.toMainTab()) {
        MainTab.Home,
        MainTab.Active,
        MainTab.Profile,
            -> true
    }

val bottomDestinations = listOf(
    MainTab.Home,
    MainTab.Active,
    MainTab.Profile
)