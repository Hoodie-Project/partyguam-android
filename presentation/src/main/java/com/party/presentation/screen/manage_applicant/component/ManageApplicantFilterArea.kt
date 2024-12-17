package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.party.common.component.chip.OrderByCreateDtChip

@Composable
fun ManageApplicantFilterArea(
    text: String,
    isDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OrderByCreateDtChip(
            text = text,
            orderByDesc = isDesc,
            onChangeSelected = { onChangeOrderBy(it) }
        )
    }
}