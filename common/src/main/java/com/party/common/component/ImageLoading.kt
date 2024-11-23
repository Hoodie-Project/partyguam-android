package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.party.common.R
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE

@Composable
fun ImageLoading(
    modifier: Modifier,
    imageUrl: String? = null,
    borderColor: Color = GRAY100,
    borderWidth: Dp = 1.dp,
    roundedCornerShape: Dp = LARGE_CORNER_SIZE,
) {
    Card(
        modifier = modifier,
        border = BorderStroke(borderWidth, borderColor),
        shape = RoundedCornerShape(roundedCornerShape),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NetworkImageLoad(
                modifier = Modifier
                    .fillMaxSize(),
                url = imageUrl,
            )
        }
    }
}

@Composable
fun NetworkImageLoad(
    modifier: Modifier = Modifier,
    url: String? = null,
) {
    if (LocalInspectionMode.current) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = ""
        )
    } else {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = "image",
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .fillMaxSize(),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                )
            },
            error = { ImageNotLoading() }
        )
    }
}

@Composable
fun ImageNotLoading(){
    Image(
        modifier = Modifier
            .size(60.dp),
        painter = painterResource(id = R.drawable.default_image),
        contentDescription = "이미지 로딩 실패!",
        contentScale = ContentScale.FillBounds
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNetworkImageLoad() {
    NetworkImageLoad(
        url = "https://images.uns123123plash.com/photo-1632210004114-3b3b3b3b3b3b"
    )
}