package org.utl.rvpark_movil.login.ui

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoginScreen(){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {

        Login(Modifier.align(Alignment.Center))
    }
}

@Composable
fun Login(modifier: Modifier) {
    Column(modifier = Modifier){

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            text = "RVPARK movil")

        Image(
            modifier =Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.star_on),
            contentDescription = "Logo")

        OutlinedTextField(
            modifier= Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text(text = "Email")},
            value = "Email"
        )
        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            modifier= Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text(text = "Password")},
            value = "password"
        )
        Spacer(modifier = Modifier.padding(20.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
            contentAlignment = Alignment.Center) {

            Button(
                modifier = Modifier,
                onClick = {}) {
                Text(text = "Log in")
            }
        }

        Box(modifier =Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
            contentAlignment = Alignment.Center){

            Spacer(modifier = Modifier.padding(16.dp))

            TextButton(
                onClick = {}
            ) {
                Text(
                    modifier = Modifier,
                    text = "Register")
            }
        }
    }
}