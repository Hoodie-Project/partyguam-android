package com.party.presentation.screen.profile_edit_career.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.PRIMARY
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.profile_edit_career.ProfileEditCareerState

@Composable
fun ProfileEditSubPositionArea(
    userProfileState: ProfileEditCareerState,
    selectedSubPosition: String,
    onSelectSubPosition: (PositionList) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(
            items = userProfileState.subPositionList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            SubPositionAreaItem(
                item = item,
                selected = item.sub == selectedSubPosition,
                onSelect = {
                    onSelectSubPosition(it)
                }
            )
        }
    }
}

@Composable
private fun SubPositionAreaItem(
    item: PositionList,
    selected: Boolean,
    onSelect: (PositionList) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable { onSelect(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.size(16.dp),
            selected = selected,
            onClick = { onSelect(item) },
            colors = RadioButtonDefaults.colors(
                selectedColor = PRIMARY,
                unselectedColor = GRAY200,
            )
        )
        WidthSpacer(widthDp = 6.dp)
        Text(
            text = item.sub,
            fontSize = B2,
            color = BLACK
        )
    }
}