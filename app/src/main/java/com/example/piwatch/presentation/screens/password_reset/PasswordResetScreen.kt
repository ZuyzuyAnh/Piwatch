package com.example.piwatch.presentation.screens.password_reset

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piwatch.R
import com.example.piwatch.presentation.components.ContentTextComponent
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.MyIconTextField
import com.example.piwatch.ui.theme.PiWatchTheme
import com.example.piwatch.util.Resource

@Composable
fun PasswordResetScreen(
    navigateToLogin: () -> Unit,
    viewModel: PassWordResetViewModel
) {
    val sendRequestFlow = viewModel.sendResetFlow.collectAsState()
    var context = LocalContext.current
    var email = viewModel.email
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeadingTextComponent(
                text = stringResource(R.string.send_password_reset),
                weight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.heightIn(16.dp))
            ContentTextComponent(
                text = stringResource(id = R.string.send_password_reset_detail),
                maxLines = 10
            )
            MyIconTextField(
                label = stringResource(id = R.string.email),
                Icons.Outlined.Email,
                onTextChange = {
                    viewModel.onEvent(PasswordResetEvent.EmailChanged(it))
                },
                value = email,
                isError = false
            )
            Spacer(modifier = Modifier.height(10.dp))
            LoginButton(
                text = stringResource(R.string.send_email),
                MaterialTheme.colorScheme.primary,
                onClick = {
                    viewModel.onEvent(PasswordResetEvent.Submit)
                    if(sendRequestFlow.value is Resource.Success){
                        Toast.makeText(context, "Email sended", Toast.LENGTH_LONG).show()
                    }else if(sendRequestFlow.value is Resource.Error){
                        Toast.makeText(context, sendRequestFlow.value?.message!!, Toast.LENGTH_LONG).show()
                    }
                    Log.d("MyTAG", "")
                }
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PassWordResetScrenPrv() {
    PiWatchTheme{
        //PasswordResetScreen()
    }
}