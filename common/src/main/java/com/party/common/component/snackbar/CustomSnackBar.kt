package com.party.common.component.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.guam.design.GRAY600

@Composable
fun CustomSnackBar(
    message: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 20.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = GRAY600,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .width(width = 20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_error),
                    contentDescription = "error",
                    modifier = Modifier.size(16.dp)
                )

                WidthSpacer(4.dp)

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    lineHeight = 16.8.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                )
            }
            Spacer(
                modifier = Modifier
                    .width(width = 20.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCustomSnackBar() {
    CustomSnackBar(
        message = "지원이 취소되었어요."
    )
}