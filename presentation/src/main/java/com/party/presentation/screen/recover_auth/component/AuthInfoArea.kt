package com.party.presentation.screen.recover_auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE

@Composable
fun AuthInfoArea(
    email: String,
    deletedAt: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
        colors = CardDefaults.cardColors(containerColor = WHITE),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            AuthInfoAreaItem(
                title = "이메일",
                content = email
            )
            HeightSpacer(heightDp = 16.dp)
            AuthInfoAreaItem(
                title = "탈퇴일",
                content = convertIsoToCustomDateFormat(deletedAt)
            )
        }
    }
}

@Composable
fun AuthInfoAreaItem(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextComponent(
            modifier = Modifier
                .width(50.dp)
                .fillMaxHeight(),
            text = title,
            fontSize = B2,
            fontWeight = FontWeight.SemiBold,
        )
        WidthSpacer(widthDp = 8.dp)
        TextComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            text = content,
            fontSize = B2,
        )
    }
}