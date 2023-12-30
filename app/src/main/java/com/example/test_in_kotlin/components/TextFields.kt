package com.example.test_in_kotlin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.test_in_kotlin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    label: String,
    onValueChange: (String) -> Unit,
    value: String,
    trailingIconContentDescription: String = "Icon",
    onTrailingIconClick: () -> Unit,
    width: Dp = 275.dp,
    password : Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                lineHeight = 1.33.em,
                style = TextStyle(fontSize = 12.sp)
            )
        },
        trailingIcon = {
            if(trailingIcon != null) {
                trailingIcon
            }
            else{
                CancelIcon(trailingIconContentDescription,onTrailingIconClick)
            } },
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = if (password) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier
            .requiredWidth(width = width)
            .requiredHeight(height = 100.dp)
            .padding(0.dp,15.dp)
            .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
    )


}
@Composable
fun CancelIcon(trailingIconContentDescription:String, onTrailingIconClick: () -> Unit){

    IconButton(
        onClick = { onTrailingIconClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.requiredSize(size = 48.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clip(shape = RoundedCornerShape(100.dp))
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_24px),
                        contentDescription = trailingIconContentDescription,
                        tint = Color(0xff48454e)
                    )
                }
            }
        }
    }
}