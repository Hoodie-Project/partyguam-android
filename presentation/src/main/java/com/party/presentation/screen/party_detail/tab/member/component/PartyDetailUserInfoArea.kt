package com.party.presentation.screen.party_detail.tab.member.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.presentation.enum.PartyAuthorityType

@Composable
fun PartyDetailUserInfoArea(
    authority: String,
    position: String,
    nickName: String,
    userId: Int,
    authorityUserId: Int,
) {
    Column(
        modifier = Modifier
            .height(50.dp)
    ) {
        AuthorityAndPosition(
            authority = authority,
            position = position,
        )
        HeightSpacer(heightDp = 4.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if(userId == authorityUserId){
                PointMe()
                WidthSpacer(widthDp = 4.dp)
            }
            TextComponent(
                text = nickName,
                fontSize = B1
            )
        }
    }
}

@Composable
private fun PointMe() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(20.dp)
            .background(BLACK),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "나",
            color = WHITE
        )
    }
}

@Composable
private fun AuthorityAndPosition(
    authority: String,
    position: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(authority == PartyAuthorityType.MASTER.authority){
            TextComponent(
                text = "파티장 | ",
                fontSize = B2,
                fontWeight = FontWeight.SemiBold,
                textColor = PRIMARY,
            )
        }
        if(authority == PartyAuthorityType.DEPUTY.authority){
            TextComponent(
                text = "부파티장 | ",
                fontSize = B2,
                fontWeight = FontWeight.SemiBold,
                textColor = PRIMARY,
            )
        }
        TextComponent(
            text = position,
            fontSize = B2,
            fontWeight = FontWeight.SemiBold,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PointMePreview() {
    PointMe()
}