package com.party.common.component.bottomsheet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.TextComponent
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun BottomSheetTitleArea(
    titleText: String,
    onSheetClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextComponent(
            modifier = Modifier.align(Alignment.Center),
            text = titleText,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
            align = Alignment.Center,
        )

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(24.dp)
                .clickable { onSheetClose() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetTitleAreaPreview() {
    BottomSheetTitleArea(
        titleText = "Title",
        onSheetClose = {}
    )
}
