package com.example.test_in_kotlin.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_in_kotlin.screens.Registration.RegistrationViewModel
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePick(modifier: Modifier,
             onValueChange: (String) -> Unit,
             value: String,
             registrationViewModel: RegistrationViewModel= viewModel(factory = RegistrationViewModel.Factory)
             ) {
    val mContext = LocalContext.current
    val RegistrationUIState = registrationViewModel.uiState.collectAsState()

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") } // Add this line to create a text variable

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
            mDate.value = selectedDate
            text = selectedDate
        }, mYear, mMonth, mDay
    )
    val updatedUiState = RegistrationUIState.value.copy(date = text)
    registrationViewModel.updateUiState(updatedUiState)
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text="dd/mm/yyyy"
            )
                      },
        label = {
            Text(
                text = "Date",
                lineHeight = 3.em,
                style = TextStyle(fontSize = 30.sp)
            )
        },
        trailingIcon = {
            DateIcon(mDatePickerDialog)
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .requiredWidth(width = 175.dp)
            .requiredHeight(height = 75.dp)
            .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
    )
}

@Composable
fun DateIcon(datePickerDialog: DatePickerDialog){
    IconButton(
        onClick = {datePickerDialog.show()}
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
                        Icons.Outlined.DateRange,
                        contentDescription = "DateIcon",
                    )
                }
            }
        }
    }

}
