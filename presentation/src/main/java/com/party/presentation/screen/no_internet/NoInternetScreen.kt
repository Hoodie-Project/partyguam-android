package com.party.presentation.screen.no_internet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.B2
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.common.utils.calculateLetterSpacing

@Composable
fun NoInternetScreenRoute() {
    NoInternetScreen()
}

@Composable
private fun NoInternetScreen(){
    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(
                text = "인터넷 연결이 불안정해요",
                fontSize = T2,
                fontWeight = FontWeight.SemiBold,
                textColor = BLACK
            )
            HeightSpacer(12.dp)
            TextComponent(
                text = "인터넷 연결 확인 후 다시 시도해 주세요",
                fontSize = T3,
                fontWeight = FontWeight.Normal,
                textColor = BLACK
            )
            HeightSpacer(20.dp)

            Card(
                modifier = Modifier
                    .width(86.dp)
                    .height(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PRIMARY
                ),
                shape = RoundedCornerShape(99.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    TextComponent(
                        text = "다시 시도",
                        fontSize = B2,
                        fontWeight = FontWeight.SemiBold,
                        textColor = Color(0xFF111111),
                        letterSpacing = calculateLetterSpacing(B2, (-2.5f))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    NoInternetScreen()
}