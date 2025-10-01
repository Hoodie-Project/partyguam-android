package com.party.common.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK

@Composable
fun OrderByCreateDtChip(
    text: String,
    orderByDesc: Boolean,
    onChangeSelected: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .noRippleClickable {
                onChangeSelected(!orderByDesc)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = text,
            color = BLACK,
            fontSize = fs(B2)
        )
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = if(orderByDesc) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
            contentDescription = "Arrow",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextAndArrowIconChip() {
    OrderByCreateDtChip(
        text = stringResource(id = R.string.filter1),
        orderByDesc = false,
        onChangeSelected = {}
    )
}