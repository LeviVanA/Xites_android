package com.example.test_in_kotlin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_in_kotlin.R
import com.example.test_in_kotlin.screens.Login.LoginViewModel

@Composable
fun TopB(modifier: Modifier = Modifier) {

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .requiredWidth(width = 411.dp)
                .requiredHeight(height = 86.dp)
                .background(color = Color(67, 73, 111))
                .border(border = BorderStroke(1.dp, Color.Black))
                .padding(
                    horizontal = 4.dp,
                    vertical = 8.dp
                )
        )
        {
            logo(modifier = modifier)
        }
        Spacer(modifier = Modifier.padding(20.dp))
    }
}

@Composable
fun logo(modifier:Modifier){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "image description",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .width(81.dp)
            .height(86.dp)
    )
}
@Preview(widthDp = 411, heightDp = 86)
@Composable
private fun TopAppBarPreview() {
    TopB(Modifier)
}