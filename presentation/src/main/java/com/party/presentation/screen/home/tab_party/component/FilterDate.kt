package com.party.presentation.screen.home.tab_party.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.party.common.R
import com.party.common.component.chip.OrderByCreateDtChip

@Composable
fun FilterDate(
    selectedCreateDataOrderByDesc: Boolean,
    onChangeOrderByPartyArea: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        OrderByCreateDtChip(
            text = stringResource(id = R.string.filter1),
            orderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = { onChangeOrderByPartyArea(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterDatePreview() {
    FilterDate(
        selectedCreateDataOrderByDesc = true,
        onChangeOrderByPartyArea = {}
    )
}