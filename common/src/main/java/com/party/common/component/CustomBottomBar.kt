package com.party.common.component

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.party.common.BottomBarScreen
import com.party.common.Screens
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.fs
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
    navController: NavHostController,
    currentScreen: String,
    onTabClick: (Screens) -> Unit,
    isExpandedFloatingButton: Boolean = false,
){

    AppBottomNavigationBar(
        isExpandedFloatingButton = isExpandedFloatingButton,
        show = shouldShowBottomBar(navController),
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        bottomDestinations.forEach { item ->
            AppBottomNavigationBarItem(
                icon = item.icon,
                label = item.name,
                selected = currentScreen == item::class.simpleName,
                onTabClick = { onTabClick(item.screen) },
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
    if(show){
        Surface(
            color = WHITE,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
        ) {
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
            fontSize = fs(B3),
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) BOTTOM_NAVIGATION_SELECTED_TEXT else BOTTOM_NAVIGATION_UNSELECTED_TEXT,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val backStackEntry by navController.currentBackStackEntryAsState()
    return when {
        backStackEntry?.destination?.hasRoute<Screens.Home>() == true -> true
        backStackEntry?.destination?.hasRoute<Screens.State>() == true -> true
        backStackEntry?.destination?.hasRoute<Screens.Profile>() == true -> true
        backStackEntry?.destination?.hasRoute<Screens.Search>() == true -> true
        backStackEntry?.destination?.hasRoute<Screens.PartyDetail>() == true -> true
        backStackEntry?.destination?.hasRoute<Screens.RecruitmentDetail>() == true -> true
        else -> false
    }
}


val bottomDestinations = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.State,
    BottomBarScreen.Profile,
)

@Composable
fun getCurrentScreen(
    navController: NavHostController,
): String?{
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.substringAfterLast(".")

    return currentScreen
}