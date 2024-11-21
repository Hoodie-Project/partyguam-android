package com.party.presentation.screen.home.tab_party.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.party.common.R
import com.party.common.component.chip.OrderByCreateDtChip

@Composable
fun FilterDate(
    selectedCreateDataOrderByDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    OrderByCreateDtChip(
        text = stringResource(id = R.string.filter1),
        orderByDesc = selectedCreateDataOrderByDesc,
        onChangeSelected = { onChangeOrderBy(it) }
    )
}