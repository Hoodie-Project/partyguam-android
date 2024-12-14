package com.party.presentation.screen.party_user_manage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.PRIMARY

@Composable
fun PartyUserListArea(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = listOf("", ""),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PartyUserListItem()
        }
    }
}

@Composable
fun PartyUserListItem(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(122.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfileArea()
        WidthSpacer(12.dp)
        PartyUserListItemContentArea(
            modifier = Modifier
                .weight(1f)
        )
        MoreButton()
    }
}

@Composable
private fun PartyUserListItemContentArea(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        PartyAuthorityAndPosition()
        HeightSpacer(4.dp)
        UserContent()
        HeightSpacer(4.dp)
        UserJoinCreateDt()
    }
}

@Composable
private fun PartyAuthorityAndPosition(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "파티장",
            color = PRIMARY,
            fontWeight = FontWeight.SemiBold,
            fontSize = B3
        )
        WidthSpacer(6.dp)
        Text(
            text = "|",
            color = GRAY400
        )
        WidthSpacer(6.dp)
        Text(
            text = "개발자",
            fontSize = B3
        )
        WidthSpacer(6.dp)
        Text(
            text = "안드로이드",
            fontSize = B3
        )
    }
}

@Composable
private fun UserContent(
    modifier: Modifier = Modifier
) {
    TextComponent(
        text = "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요",
        fontSize = B2,
        maxLines = 2,
    )
}

@Composable
private fun UserJoinCreateDt(
    modifier: Modifier = Modifier
) {
    TextComponent(
        text = "2024.12.12",
        fontSize = B3,
        textColor = GRAY600
    )
}

@Composable
private fun UserProfileArea(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(60.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = GRAY300
            )
        ) {

        }

        // 파티장이면
        DrawableIconButton(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.BottomEnd),
            icon = painterResource(id = R.drawable.icon_flag),
            contentDescription = "Select Image",
            onClick = {},
            iconColor = Color.Unspecified
        )
    }
}

@Composable
private fun MoreButton(
    modifier: Modifier = Modifier
) {
    DrawableIconButton(
        icon = painterResource(id = R.drawable.icon_vertical_more),
        contentDescription = "More",
        iconSize = 24.dp,
        iconColor = GRAY400,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyUserListAreaPreview() {
    PartyUserListArea()
}

@Preview(showBackground = true)
@Composable
private fun PartyUserListItemPreview() {
    PartyUserListItem()
}