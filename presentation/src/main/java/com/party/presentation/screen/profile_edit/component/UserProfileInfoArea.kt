package com.party.presentation.screen.profile_edit.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.icon.DrawableIconButton
import com.party.common.createMultipartBody
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T1
import com.party.presentation.screen.profile.UserProfileState
import okhttp3.MultipartBody

@Composable
fun UserProfileInfoArea(
    userProfileState: UserProfileState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING_SIZE),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserProfileImageArea(
            onSetImage = {}
        )

        // 유저 닉네임
        HeightSpacer(heightDp = 12.dp)
        UserNickNameArea(
            nickName = ""
        )

        // 성별, 나이
        HeightSpacer(heightDp = 12.dp)
        UserGenderAndAge()
    }
}

@Composable
private fun UserProfileImageArea(
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
        modifier = Modifier
            .size(120.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .size(120.dp),
            shape = CircleShape,
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
            icon = painterResource(id = R.drawable.icon_select_image),
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

@Composable
private fun UserNickNameArea(
    nickName: String,
) {
   TextComponent(
       text = nickName,
       fontSize = T1,
       fontWeight = FontWeight.SemiBold
   )
}

@Composable
private fun UserGenderAndAge(
    modifier: Modifier = Modifier
) {
    UserGenderAndAgeItem(
        title = "성별",
        gender = "남자",
        isVisible = false
    )

    HeightSpacer(heightDp = 12.dp)
    UserGenderAndAgeItem(
        title = "나이",
        gender = "24살",
        isVisible = true
    )
}

@Composable
private fun UserGenderAndAgeItem(
    title: String,
    gender: String,
    isVisible: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            TextComponent(
                text = title,
                fontSize = B1,
                fontWeight = FontWeight.SemiBold,
            )
            WidthSpacer(widthDp = 12.dp)
            TextComponent(
                text = gender,
                fontSize = B1
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                text = if (isVisible) "공개" else "비공개",
                fontSize = B3,
                fontWeight = FontWeight.SemiBold,
                textColor = if (isVisible) PRIMARY else GRAY400
            )
            WidthSpacer(widthDp = 2.dp)
            DrawableIcon(
                icon = if(isVisible) painterResource(id = R.drawable.icon_toggle_on) else painterResource(id = R.drawable.icon_toggle_off),
                contentDescription = "",
                tintColor = if (isVisible) PRIMARY else GRAY400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfileInfoAreaPreview() {
    UserProfileInfoArea(
        userProfileState = UserProfileState()
    )
}