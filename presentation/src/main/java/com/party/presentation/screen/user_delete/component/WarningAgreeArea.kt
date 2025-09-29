package com.party.presentation.screen.user_delete.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY400

@Composable
fun WarningAgreeArea(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        onClick = { onCheckedChange(!isChecked)},
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = CardDefaults.cardColors(
            containerColor = GRAY100
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = DARK100,
                    uncheckedColor = GRAY400,
                ),
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )

            TextComponent(
                text = "안내 사항을 모두 확인하였으며, 이에 동의합니다",
                fontSize = B2,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .offset(x = (-6).dp),
                onClick = { onCheckedChange(!isChecked) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WarningAgreeAreaPreview() {
    WarningAgreeArea(
        isChecked = false,
        onCheckedChange = {}
    )
}