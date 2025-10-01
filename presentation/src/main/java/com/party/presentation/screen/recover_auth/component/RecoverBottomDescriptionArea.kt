package com.party.presentation.screen.recover_auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.calculateLetterSpacing
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.fs
import com.party.guam.design.B2

@Composable
fun RecoverBottomDescriptionArea(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        RecoverBottomDescriptionAreaItem(
            text = "계정을 복구하면 기존 데이터와 설정이 그대로 유지됩니다."
        )
        HeightSpacer(heightDp = 12.dp)
        RecoverBottomDescriptionAreaItem(
            text = "30일이 경과하면 계정이 영구 삭제되며, 이후에는 복구가 불가능합니다."
        )
    }
}

@Composable
private fun RecoverBottomDescriptionAreaItem(
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        DrawableIcon(
            icon = painterResource(id = R.drawable.icon_info),
            contentDescription = "info",
            modifier = Modifier
                .padding(top = 1.dp)
        )
        WidthSpacer(widthDp = 6.dp)

        TextComponent(
            text = text,
            fontSize = B2,
            letterSpacing = calculateLetterSpacing(B2.sp, (-2.5f))
        )
    }
}