package com.party.presentation.screen.state.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.component.floating.FabItem

@Composable
fun StateFloatingArea(
    modifier: Modifier,
    isExpandedFloatingButton: Boolean,
    partyCreateFloating: @Composable () -> Unit = {},
    navigateUpFloating: @Composable () -> Unit = {},
    onGoPartyCreate: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        if(isExpandedFloatingButton){
            FabItem(
                title = stringResource(id = R.string.common8),
                onClicked = onGoPartyCreate
            )
        }

        HeightSpacer(12.dp)
        navigateUpFloating()

        HeightSpacer(12.dp)
        partyCreateFloating()
    }
}