package com.party.presentation.screen.user_delete.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE

@Composable
fun UserDeleteButton(
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = onClick,
        buttonText = "탈퇴하기",
        containerColor = if (isChecked) RED else Color(0xFFF7CDCC),
        borderColor = if (isChecked) RED else Color(0xFFF7CDCC),
        contentColor = WHITE,
    )
}

@Preview(showBackground = true)
@Composable
private fun UserDeleteButtonPreview() {
    UserDeleteButton(
        isChecked = false,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun UserDeleteButtonPreview2() {
    UserDeleteButton(
        isChecked = true,
        onClick = {}
    )
}
