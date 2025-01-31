package com.party.presentation.screen.party_detail.tab.member.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyAdmin
import com.party.domain.model.party.PartyMember
import com.party.domain.model.party.PartyUser
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.Position
import com.party.domain.model.party.User
import com.party.domain.model.party.toPartyMember
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition

@Composable
fun PartyDetailUsersListArea(
    partyUsers: PartyUsers,
    authority: PartyAuthority,
    onReports: (Int) -> Unit,
) {
    val partyAdminList = partyUsers.partyAdmin
    val partyUserList = partyUsers.partyUser

    val partyMemberList: List<PartyMember> = partyAdminList.map { admin ->
        admin.toPartyMember()
    } + partyUserList.map { user ->
        user.toPartyMember()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
    ) {
        itemsIndexed(
            items = partyMemberList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            PartyDetailUsersListItem(
                partyMember = item,
                authority = authority,
                onReports = onReports
            )
        }
    }
}

@Composable
fun PartyDetailUsersListItem(
    partyMember: PartyMember,
    authority: PartyAuthority,
    onReports: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 5.dp),
            colors = CardDefaults.cardColors(
                containerColor = WHITE,
            ),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PartyDetailUserProfileImageArea(
                        imageUrl = partyMember.image,
                        authority = partyMember.authority,
                    )
                    WidthSpacer(widthDp = 12.dp)
                    PartyDetailUserInfoArea(
                        authority = partyMember.authority,
                        position = "${partyMember.main} ${partyMember.sub}",
                        nickName = partyMember.nickName,
                        userId = partyMember.userId,
                        authorityUserId = authority.id
                    )
                }

                // 나 인 경우는 신고하기 아이콘 노출X
                if(partyMember.userId != authority.id){
                    DrawableIconButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp, end = 10.dp),
                        icon = painterResource(id = R.drawable.icon_emergency),
                        contentDescription = "emergency",
                        onClick = { onReports(partyMember.userId)}
                    )
                }

            }
        }
        HeightSpacer(heightDp = 12.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailUsersListAreaPreview() {
    PartyDetailUsersListArea(
        partyUsers = PartyUsers(
            partyAdmin = listOf(
                PartyAdmin(
                    id = 0,
                    authority = "master",
                    position = Position(
                        id = 5047,
                        main = "개발자",
                        sub = "안드로이드"
                    ),
                    user = User(
                        //id = 1961,
                        nickname = "닉네임입니다",
                        image = null,
                        userCareers = listOf()
                    )
                )
            ),
            partyUser = listOf(
                PartyUser(
                    id = 0,
                    authority = "member",
                    position = Position(
                        id = 1727,
                        main = "기획자",
                        sub = "PM"
                    ),
                    user = User(
                        //id = 4098,
                        nickname = "나의 닉네임은",
                        image = null,
                        userCareers = listOf()
                    )
                )
            )
        ),
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onReports = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailUsersListItemPreview() {
    PartyDetailUsersListItem(
        partyMember = PartyMember(
            authority = "master",
            main = "개발자",
            sub = "안드로이드 3년",
            nickName = "안드로이드 개발자",
            image = "",
            userId = 1
        ),
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onReports = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailUsersListItemPreview2() {
    PartyDetailUsersListItem(
        partyMember = PartyMember(
            authority = "member",
            main = "개발자",
            sub = "안드로이드 3년",
            nickName = "홍길동",
            image = "",
            userId = 1
        ),
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onReports = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailUsersListItemPreview3() {
    PartyDetailUsersListItem(
        partyMember = PartyMember(
            authority = "deputy",
            main = "개발자",
            sub = "안드로이드 3년",
            nickName = "홍길동",
            image = "",
            userId = 1
        ),
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onReports = {}
    )
}