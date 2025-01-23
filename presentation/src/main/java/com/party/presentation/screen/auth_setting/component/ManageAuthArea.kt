package com.party.presentation.screen.auth_setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun ManageAuthArea(
    modifier: Modifier,
    onLinkKakao: () -> Unit,
    onLinkGoogle: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "소셜 로그인 관리",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
        )
        HeightSpacer(heightDp = 20.dp)

        AuthType(
            icon = painterResource(id = R.drawable.kakao),
            authText = "카카오톡 계정",
            onClick = onLinkKakao
        )
        HeightSpacer(heightDp = 12.dp)
        AuthType(
            icon = painterResource(id = R.drawable.google),
            authText = "구글 계정",
            onClick = onLinkGoogle
        )
    }
}

@Composable
private fun AuthType(
    icon: Painter,
    authText: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                DrawableIcon(
                    icon = icon,
                    contentDescription = "",
                    tintColor = Color.Unspecified
                )
                WidthSpacer(widthDp = 8.dp)
                TextComponent(
                    text = authText,
                    fontSize = T3,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            LinkAuthCard(
                onClick = onClick,
                text = "연결하기",
                containerColor = PRIMARY,
                borderColor = PRIMARY
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageAuthAreaPreview() {
    ManageAuthArea(
        modifier = Modifier,
        onLinkKakao = {},
        onLinkGoogle = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun AuthTypePreview() {
    AuthType(
        icon = painterResource(id = R.drawable.google),
        authText = "카카오톡 계정",
        onClick = {}
    )
}