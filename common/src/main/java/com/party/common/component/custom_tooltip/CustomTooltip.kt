package com.party.common.component.custom_tooltip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

class TriangleEdge: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path()
        trianglePath.apply {
            moveTo(x = size.width / 2, y = 0f)
            lineTo(x = size.width, y = size.height)
            lineTo(x = 0f, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}


@Composable
fun CustomTooltip(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp)
                .background(color = GRAY500, shape = TriangleEdge()),
        )

        Card(
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            border = BorderStroke(1.dp, GRAY500),
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(30.dp)
                    .background(
                        color = GRAY500,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "4:3 비율",
                    textAlign = TextAlign.Center,
                    color = PRIMARY,
                    modifier = Modifier
                        .padding(start = 12.dp),
                    fontSize = B2
                )
                Text(
                    text = "이 가장 예뻐요",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = B2
                )
                WidthSpacer(widthDp = 4.dp)
                DrawableIconButton(
                    modifier = Modifier
                        .size(12.dp),
                    icon = painterResource(id = R.drawable.icon_close2),
                    contentDescription = "close",
                    iconColor = WHITE,
                    onClick = { onClick() }
                )
                WidthSpacer(widthDp = 12.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyCreateCustomShapePreview() {
    CustomTooltip(
        onClick = {}
    )
}