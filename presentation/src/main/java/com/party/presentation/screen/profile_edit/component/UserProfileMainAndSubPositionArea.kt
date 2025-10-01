package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.utils.fs
import com.party.guam.design.B1
import com.party.guam.design.GRAY100
import com.party.guam.design.LIGHT400
import com.party.guam.design.T3
import com.party.domain.model.user.profile.UserCareer
import com.party.guam.design.B2

@Composable
fun UserProfileMainAndSubPositionArea(
    userCareers: List<UserCareer>
) {
    if(userCareers.isNotEmpty()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            UserProfileMainAndSubPositionAreaItem(
                title = "주포지션",
                userCareer = userCareers[0],
                containerColor = LIGHT400,
            )
            if(userCareers.size > 1){
                HeightSpacer(heightDp = 12.dp)
                UserProfileMainAndSubPositionAreaItem(
                    title = "부포지션",
                    userCareer = userCareers[1],
                    containerColor = GRAY100,
                )
            }
        }
    }
}

@Composable
private fun UserProfileMainAndSubPositionAreaItem(
    title: String,
    userCareer: UserCareer,
    containerColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        TextComponent(
            text = title,
            fontSize = B1,
            fontWeight = FontWeight.SemiBold,
        )
        WidthSpacer(widthDp = 24.dp)

        val yearsText = if(userCareer.years == 0) "신입" else "${userCareer.years}년"
        Chip(
            text = yearsText,
            containerColor = containerColor,
            fontSize = fs(T3),
            fontWeight = FontWeight.Normal,
        )

        val main = userCareer.position.main
        val sub = userCareer.position.sub

        WidthSpacer(widthDp = 8.dp)
        Chip(
            text = "$main | $sub",
            containerColor = containerColor,
            fontSize = fs(T3),
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfileMainAndSubPositionAreaPreview(){
    UserProfileMainAndSubPositionArea(
        userCareers = emptyList()
    )
}