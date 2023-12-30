package com.example.test_in_kotlin.screens.Home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_in_kotlin.R
import com.example.test_in_kotlin.components.Buttons
import com.example.test_in_kotlin.components.TopB

@Composable
fun HomeScreen(toLogin:() ->Unit, toRegistration:() ->Unit, toRegistrations:() ->Unit, modifier: Modifier,) {

    Row{
        TopB(modifier = modifier.align(Alignment.Top))
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        Buttons(
            text = "Registreer uur",
            onClick = {
                toRegistration()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Buttons(
            text = "Registraties",
            onClick = {
                toRegistrations()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Buttons(
            text = "Log out",
            onClick = {
                toLogin()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))


    }
}

