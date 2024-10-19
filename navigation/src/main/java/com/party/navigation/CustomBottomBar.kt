package com.party.navigation

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.party.common.R
import com.party.common.ui.theme.BOTTOM_NAVIGATION_SELECTED_TEXT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_SELECTED_TINT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_UNSELECTED_TEXT
import com.party.common.ui.theme.BOTTOM_NAVIGATION_UNSELECTED_TINT
import com.party.common.ui.theme.ICON_SIZE

@Composable
fun BottomNavigationBar(
    context: Context,
    navController: NavHostController,
){
    val screenList = listOf(
        Screens.Home,
        Screens.State,
        Screens.Profile,
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromRoute()

    AppBottomNavigationBar(
        show = navController.shouldShowBottomBar
    ) {
        screenList.forEach { screenItem ->
            AppBottomNavigationBarItem(
                icon = BottomIconSetting(screenItem),
                label = screenItem.title,
                onClick = {
                    if (currentScreen != screenItem) {
                        navController.navigate(screenItem) {
                            popUpTo(navController.currentBackStackEntry.fromRoute())
                        }
                    }
                },
                selected = currentScreen == screenItem,
                onBack = {
                    if (currentScreen == Screens.Home) {
                        (context as Activity).finish()
                    } else {
                        navController.navigate(Screens.Home)
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
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = Color.White,
        modifier = modifier.windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        if (show) {
            Column {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
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
    onClick: () -> Unit,
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
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(ICON_SIZE),
            painter = icon,
            contentDescription = "",
            tint = if (selected) BOTTOM_NAVIGATION_SELECTED_TINT else BOTTOM_NAVIGATION_UNSELECTED_TINT
        )

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) BOTTOM_NAVIGATION_SELECTED_TEXT else BOTTOM_NAVIGATION_UNSELECTED_TEXT,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry.fromRoute()) {
        Screens.Home,
        Screens.State,
        Screens.Profile,
        -> true
        else -> false
    }

@Composable
private fun BottomIconSetting(screens: Screens): Painter {
    return when (screens) {
        Screens.Home -> painterResource(id = R.drawable.home_icon)
        Screens.State -> painterResource(id = R.drawable.state_icon)
        Screens.Profile -> painterResource(id = R.drawable.profile_icon)
        else -> painterResource(id = R.drawable.home_icon)
    }
}