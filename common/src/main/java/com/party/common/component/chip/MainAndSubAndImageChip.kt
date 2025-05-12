package com.party.common.component.chip

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LIGHT400

@Composable
fun MainAndSubAndImageChip(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    roundedCornerShape: Dp,
    main: String,
    sub: String,
    fontSize: TextUnit = B2,
    image: Painter,
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .height(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(roundedCornerShape),
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = main,
                    fontSize = fontSize,
                )
                WidthSpacer(widthDp = 6.dp)
                Image(
                    painter = image,
                    contentDescription = "",
                    modifier = Modifier
                        .width(1.dp)
                        .height(10.dp)
                )
                WidthSpacer(widthDp = 6.dp)
                Text(
                    text = sub,
                    fontSize = fontSize,
                )
            }
        }
    }
}

@Preview
@Composable
fun MainAndSubAndImageChipPreview(modifier: Modifier = Modifier) {
    MainAndSubAndImageChip(
        image = painterResource(id = R.drawable.vertical_rectangle_primary),
        containerColor = LIGHT400,
        contentColor = BLACK,
        roundedCornerShape = 999.dp,
        main = "개발자",
        sub = "Android",
    )
}

@Preview
@Composable
fun MainAndSubAndImageChipPreview2(modifier: Modifier = Modifier) {
    MainAndSubAndImageChip(
        image = painterResource(id = R.drawable.vertical_rectangle_primary),
        containerColor = GRAY100,
        contentColor = BLACK,
        roundedCornerShape = 999.dp,
        main = "개발자",
        sub = "백엔드",
    )
}