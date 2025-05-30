package com.party.presentation.screen.join.nickname.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.utils.AnnotatedTextComponent
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.guam.design.B3
import com.party.guam.design.GRAY400
import com.party.guam.design.RED

@Composable
fun WarningArea(
    context: Context,
    userNickName: String,
    isValid: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 6.dp),
            text = setWarningText(userNickName, isValid),
            fontSize = B3,
            textColor = RED,
            align = Alignment.CenterStart,
        )
        AnnotatedTextComponent(
            modifier = Modifier.weight(0.2f),
            annotatedString = makeAnnotatedStringValid(
                text1 = userNickName.length.toString(),
                text2 = context.getString(R.string.join_nickname4),
                textColor = annotatedTextColor(userNickName, isValid)
            ),
            fontSize = B3,
            textColor = GRAY400,
            textAlign = Alignment.CenterEnd,
        )
    }
}