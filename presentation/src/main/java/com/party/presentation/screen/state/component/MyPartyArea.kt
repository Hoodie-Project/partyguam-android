package com.party.presentation.screen.state.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.chip.BorderChip
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY

@Composable
fun MyPartyArea(
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("전체") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SelectCategoryArea(
            selectedCategory = selectedCategory,
            onClick = { selectedCategory = it }
        )
    }
}

@Composable
private fun SelectCategoryArea(
    categoryList: List<String> = listOf("전체", "진행중", "종료"),
    selectedCategory: String,
    onClick: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(categoryList) { index, category ->
            BorderChip(
                borderColor = if (selectedCategory == category) PRIMARY else GRAY200,
                fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal,
                text = category,
                textColor = if (selectedCategory == category) BLACK else GRAY500,
                contentColor = BLACK,
                onClick = { onClick(category) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPartyAreaPreview(
    modifier: Modifier = Modifier
) {
    MyPartyArea(
        modifier = modifier
    )
}