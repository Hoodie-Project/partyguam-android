package com.party.presentation.screen.party_edit.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.createMultipartBody
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import okhttp3.MultipartBody

@Composable
fun PartyImageArea(
    modifier: Modifier,
    onSetImage: (MultipartBody.Part) -> Unit
) {
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            uri = selectedUri
            selectedUri?.let {
                // Uri -> MultipartBody.Part 변환 후 상위로 전달
                val multipartBody = createMultipartBody(context, selectedUri)
                onSetImage(multipartBody)
            }
        }
    )

    Box(
        modifier = modifier
            .width(220.dp)
            .height(170.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(150.dp),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = GRAY300
            )
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                model = uri,
                contentDescription = "Party Image",
            )
        }

        DrawableIconButton(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd),
            icon = painterResource(id = R.drawable.select_image),
            contentDescription = "Select Image",
            onClick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            iconColor = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyImageAreaPreview() {
    PartyImageArea(
        modifier = Modifier,
        onSetImage = { }
    )
}