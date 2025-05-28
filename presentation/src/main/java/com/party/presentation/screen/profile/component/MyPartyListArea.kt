package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.component.PartyListItem3
import com.party.guam.design.B1
import com.party.guam.design.PRIMARY
import com.party.domain.model.user.party.PartyUser
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.profile.UserProfileState

@Composable
fun MyPartyListArea(
    userProfileState: UserProfileState,
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MyPartyListTitleArea(
            count = userProfileState.myPartyList.total
        )
        HeightSpacer(heightDp = 20.dp)
        MyPartyList(
            partyUsers = userProfileState.myPartyList.partyUsers,
            onClick = onClick
        )
    }
}

@Composable
private fun MyPartyListTitleArea(
    count: Int,
) {
    val text = buildAnnotatedString {
        append("참여 파티 목록  ")
        withStyle(
            SpanStyle(
                color = PRIMARY
            )
        ){
            append("$count")
        }
        append("건")
    }
    Text(
        text = text,
        fontSize = B1,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun MyPartyList(
    partyUsers: List<PartyUser>,
    onClick: (Int) -> Unit,
) {
    if(partyUsers.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(282.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = partyUsers,
                key = { index, _ ->
                    index
                }
            ){ _, item ->
                PartyListItem3(
                    imageUrl = item.party.image,
                    status = StatusType.fromType(item.party.status).toDisplayText(),
                    type = item.party.partyType.type,
                    title = item.party.title,
                    main = item.position.main,
                    sub = item.position.sub,
                    onClick = { onClick(item.party.id) }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MyPartyListAreaPreview() {
    MyPartyListArea(
        userProfileState = UserProfileState(),
        onClick = {}
    )
}