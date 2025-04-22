package com.party.presentation.screen.recruitment_detail.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY

@Composable
fun RecruitmentButton(
    isRecruitment: Boolean,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = MEDIUM_PADDING_SIZE),
        buttonText = if(isRecruitment) "지원완료" else "지원하기",
        containerColor = if(isRecruitment) GRAY300 else PRIMARY,
        borderColor = if(isRecruitment) GRAY300 else PRIMARY,
        textSize = B2,
        textWeight = FontWeight.Bold,
        onClick = { if(!isRecruitment) onClick() }
    )
}