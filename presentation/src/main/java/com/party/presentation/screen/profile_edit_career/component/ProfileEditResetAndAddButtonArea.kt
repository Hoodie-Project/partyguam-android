package com.party.presentation.screen.profile_edit_career.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.WHITE

@Composable
fun ProfileEditResetAndAddButtonArea(
    onReset: () -> Unit,
    onAdd: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomButton(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            onClick = onReset,
            buttonText = "초기화",
            textWeight = FontWeight.Bold,
            containerColor = WHITE,
        )
        WidthSpacer(widthDp = 8.dp)
        CustomButton(
            modifier = Modifier
                .weight(2f)
                .height(48.dp),
            onClick = onAdd,
            buttonText = "추가하기",
            textWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditResetAndAddButtonAreaPreview() {
    ProfileEditResetAndAddButtonArea(
        onReset = {},
        onAdd = {}
    )
}