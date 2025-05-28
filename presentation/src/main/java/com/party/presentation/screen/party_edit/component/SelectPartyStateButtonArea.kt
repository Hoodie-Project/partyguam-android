package com.party.presentation.screen.party_edit.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.component.button.CustomButton
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY200
import com.party.guam.design.LIGHT300
import com.party.guam.design.WHITE
import com.party.presentation.enum.StatusType

@Composable
fun SelectPartyStateButtonArea(
    selectedStatus: String,
    onProgress: () -> Unit,
    onFinish: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        CustomButton(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            onClick = onProgress,
            buttonText = StatusType.ACTIVE.toDisplayText(),
            containerColor = if(selectedStatus == StatusType.ACTIVE.type) LIGHT300 else WHITE,
            borderColor = if(selectedStatus == StatusType.ACTIVE.type) DARK100 else GRAY200,
        )
        WidthSpacer(widthDp = 12.dp)
        CustomButton(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            onClick = onFinish,
            buttonText = StatusType.ARCHIVED.toDisplayText(),
            containerColor = if(selectedStatus == StatusType.ARCHIVED.type) LIGHT300 else WHITE,
            borderColor = if(selectedStatus == StatusType.ARCHIVED.type) DARK100 else GRAY200,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectPartyStateButtonAreaPreview() {
    SelectPartyStateButtonArea(
        selectedStatus = StatusType.ARCHIVED.type,
        onProgress = {},
        onFinish = {},
    )
}