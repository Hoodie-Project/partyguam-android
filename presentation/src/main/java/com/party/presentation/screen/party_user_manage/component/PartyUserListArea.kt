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
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY600
import com.party.domain.model.party.PartyMemberInfo
import com.party.domain.model.party.PartyMemberPosition
import com.party.domain.model.party.PartyUserInfo
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.enum.getPartyAuthorityColor
import com.party.presentation.enum.getPartyRole
import com.party.presentation.screen.party_user_manage.PartyUserState

@Composable
fun PartyUserListArea(
    partyUserState: PartyUserState,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = partyUserState.partyUserList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PartyUserListItem(
                partyMemberInfo = item,
                onClick = onClick
            )
        }
    }
}

@Composable
fun PartyUserListItem(
    partyMemberInfo: PartyMemberInfo,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(122.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfileArea(
            authority = partyMemberInfo.authority,
        )
        WidthSpacer(12.dp)
        PartyUserListItemContentArea(
            modifier = Modifier
                .weight(1f),
            partyMemberInfo = partyMemberInfo
        )
        MoreButton(
            onClick = onClick
        )
    }
}

@Composable
private fun PartyUserListItemContentArea(
    modifier: Modifier,
    partyMemberInfo: PartyMemberInfo
) {
    Column(
        modifier = modifier
    ) {
        PartyAuthorityAndPosition(
            authority = partyMemberInfo.authority,
            main = partyMemberInfo.position.main,
            sub = partyMemberInfo.position.sub
        )
        HeightSpacer(4.dp)
        UserContent()
        HeightSpacer(4.dp)
        UserJoinCreateDt(
            createdAt = partyMemberInfo.createdAt
        )
    }
}

@Composable
private fun PartyAuthorityAndPosition(
    authority: String,
    main: String,
    sub: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = getPartyRole(PartyAuthorityType.fromAuthority(authority)),
            color = getPartyAuthorityColor(PartyAuthorityType.fromAuthority(authority)),
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
            text = main,
            fontSize = B3
        )
        WidthSpacer(6.dp)
        Text(
            text = sub,
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
    createdAt: String
) {
    TextComponent(
        text = convertIsoToCustomDateFormat(createdAt),
        fontSize = B3,
        textColor = GRAY600
    )
}

@Composable
private fun UserProfileArea(
    authority: String,
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
        if(authority == PartyAuthorityType.MASTER.authority){
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
}

@Composable
private fun MoreButton(
    onClick: () -> Unit
) {
    DrawableIconButton(
        icon = painterResource(id = R.drawable.icon_vertical_more),
        contentDescription = "More",
        iconSize = 24.dp,
        iconColor = Color.Unspecified,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyUserListAreaPreview() {
    PartyUserListArea(
        partyUserState = PartyUserState(
            partyUserList = listOf(
                PartyMemberInfo(
                    createdAt = "2024-06-05T15:30:45.123Z",
                    updatedAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "master",
                    user = PartyUserInfo(
                        id = 4865,
                        nickname = "닉네임입니다.",
                        image = null
                    ),
                    position = PartyMemberPosition(
                        main = "개발자",
                        sub = "안드로이드"
                    )
                ),
                PartyMemberInfo(
                    createdAt = "2024-06-05T15:30:45.123Z",
                    updatedAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "member",
                    user = PartyUserInfo(
                        id = 4865,
                        nickname = "닉네임입니다.",
                        image = null
                    ),
                    position = PartyMemberPosition(
                        main = "개발자",
                        sub = "안드로이드"
                    )
                ),
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyUserListItemPreview() {
    PartyUserListItem(
        partyMemberInfo = PartyMemberInfo(
            createdAt = "2024-06-05T15:30:45.123Z",
            updatedAt = "2024-06-05T15:30:45.123Z",
            status = "Joseh",
            authority = "master",
            user = PartyUserInfo(
                id = 4865,
                nickname = "닉네임입니다.",
                image = null
            ),
            position = PartyMemberPosition(
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onClick = {}
    )
}