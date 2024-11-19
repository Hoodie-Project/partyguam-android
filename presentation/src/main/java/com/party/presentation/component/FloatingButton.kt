package com.party.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.shared.SharedViewModel

@Composable
fun FloatingButtonArea(
    isExpandedFloatingButton: Boolean,
    currentScreens: Screens,
    selectedTabText: String,
    onExpanded: (Boolean) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    when(currentScreens){
        Screens.Home -> {
            FloatingButton(
                isExpandedFloatingButton = isExpandedFloatingButton,
                selectedTabText = selectedTabText,
                onExpanded = onExpanded,
                sharedViewModel = sharedViewModel,
            )
        }
        else -> {}
    }
}

@Composable
fun FloatingButton(
    isExpandedFloatingButton: Boolean,
    selectedTabText: String,
    onExpanded: (Boolean) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    Column(horizontalAlignment = Alignment.End) {
        if (isExpandedFloatingButton) {
            FabItem(
                title = stringResource(id = R.string.common8),
                onClicked = { } // 페이지 이동
            )
        }
        HeightSpacer(12.dp)
        if(selectedTabText == "파티" || selectedTabText == "모집공고") {
            if(sharedViewModel.isScrollPartyArea){
                if(!isExpandedFloatingButton){
                    FloatingActionButton(
                        modifier = Modifier,
                        onClick = { sharedViewModel.scrollToTopFun() },
                        shape = CircleShape,
                        containerColor = WHITE
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Rounded.ArrowUpward,
                            tint = GRAY500,
                            contentDescription = "This is Expand Button",
                        )
                    }
                }

            }
            if(sharedViewModel.isScrollRecruitmentArea){
                if(!isExpandedFloatingButton){
                    FloatingActionButton(
                        modifier = Modifier,
                        onClick = { sharedViewModel.scrollToTopFun() },
                        shape = CircleShape,
                        containerColor = WHITE
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Rounded.ArrowUpward,
                            tint = GRAY500,
                            contentDescription = "This is Expand Button",
                        )
                    }
                }

            }

        }

        HeightSpacer(12.dp)
        if(selectedTabText == "메인" || selectedTabText == "파티") {
            FloatingActionButton(
                modifier = Modifier,
                onClick = { onExpanded(!isExpandedFloatingButton) },
                shape = CircleShape,
                containerColor = if (isExpandedFloatingButton) WHITE else PRIMARY
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if(isExpandedFloatingButton) Icons.Rounded.Close else Icons.Rounded.Add,
                    tint = if (isExpandedFloatingButton) BLACK else WHITE,
                    contentDescription = "This is Expand Button",
                )
            }
        }

    }
}

@Composable
fun FabItem(
    title: String,
    onClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(56.dp),
        onClick = { onClicked() },
        shape = RoundedCornerShape(MEDIUM_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp),
            contentAlignment = Alignment.CenterStart,
        ){
            TextComponent(
                text = title,
                fontSize = B1,
                textColor = BLACK
            )
        }
    }
}