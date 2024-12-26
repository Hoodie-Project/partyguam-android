package com.party.presentation.screen.party_detail.tab.member.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.ImageLoading
import com.party.common.component.icon.DrawableIcon
import com.party.presentation.enum.PartyAuthorityType

@Composable
fun PartyDetailUserProfileImageArea(
    imageUrl: String,
    authority: String
) {
    Box(
        modifier = Modifier
            .size(60.dp)
    ) {
        ImageLoading(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp),
            imageUrl = imageUrl,
        )
        if(authority == PartyAuthorityType.MASTER.authority){
            DrawableIcon(
                icon = painterResource(id = R.drawable.icon_master),
                contentDescription = "master",
                modifier = Modifier.size(24.dp).align(Alignment.BottomEnd),
                tintColor = Color.Unspecified
            )
        }
    }
}