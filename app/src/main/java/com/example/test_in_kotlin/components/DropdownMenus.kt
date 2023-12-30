package com.example.test_in_kotlin.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.test_in_kotlin.data.transactions.Items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDMBox(
    exVarP: List<Items.ProjectItem>? = null,
    exVarD:List<Items.DienstItem>? = null,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    value: String,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .requiredWidth(width = 180.dp)
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (exVarP != null) {
                    exVarP.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.naam) },
                            onClick = {
                                onValueChange(item.naam)
                                expanded = false
                            }
                        )
                    }
                }
                if (exVarD != null) {
                    exVarD.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.naam) },
                            onClick = {
                                onValueChange(item.naam)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
