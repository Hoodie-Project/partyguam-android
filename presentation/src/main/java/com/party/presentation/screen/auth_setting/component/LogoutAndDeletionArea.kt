package com.party.presentation.screen.auth_setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.RED

@Composable
fun LogoutAndDeletionArea(
    onLogout: () -> Unit,
    onUserDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextComponent(
            text = "로그아웃",
            fontSize = B2,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
            onClick = onLogout
        )
        WidthSpacer(widthDp = 16.dp)
        Text(text = "|")
        WidthSpacer(widthDp = 16.dp)
        TextComponent(
            text = "회원탈퇴",
            fontSize = B2,
            fontWeight = FontWeight.SemiBold,
            textColor = RED,
            onClick = onUserDelete
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogoutAndDeletionArea() {
    LogoutAndDeletionArea(
        onLogout = {},
        onUserDelete = {},
    )
}