package com.party.presentation.screen.party_detail.tab.member.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B1
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
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
            color = WHITE,
            fontSize = B3,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun AuthorityAndPosition(
    authority: String,
    position: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(authority == PartyAuthorityType.MASTER.authority){
            TextComponent(
                text = "파티장",
                fontSize = B2,
                fontWeight = FontWeight.SemiBold,
                textColor = PRIMARY,
            )
        }
        WidthSpacer(widthDp = 6.dp)
        Image(
            painter = painterResource(id = R.drawable.vertical_rectangle),
            contentDescription = "",
            modifier = Modifier
                .width(1.dp)
                .height(8.dp)
                .background(GRAY200),
        )
        WidthSpacer(widthDp = 6.dp)
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

@Preview
@Composable
private fun AuthorityAndPositionPreview(modifier: Modifier = Modifier) {
    AuthorityAndPosition(
        authority = "master",
        position = "개발자 Android"
    )
}


@Preview(showBackground = true)
@Composable
fun PointMePreview() {
    PointMe()
}