package com.example.piwatch.presentation.screens.login_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.piwatch.presentation.components.PiWatchLogo
import com.example.piwatch.R
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.LoginButtonWithIcon
import com.example.piwatch.presentation.components.MyIconPasswordField
import com.example.piwatch.presentation.components.MyIconTextField
import com.example.piwatch.presentation.components.SmallMessage
import com.example.piwatch.presentation.components.ValidateError
import com.example.piwatch.ui.theme.PiWatchTheme
import com.example.piwatch.util.CONSTANTS
import com.example.piwatch.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navigateToSignUp: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: LoginViewModel
) {
    var state = viewModel.state.collectAsState().value
    var context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()){
        val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val result = account.getResult(ApiException::class.java)
            val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
            viewModel.onEvent(LoginEvent.LoginWithGoogle(credentials))
        }catch (it: ApiException){
            print(it)
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ){
                    PiWatchLogo()
                    HeadingTextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.welcome),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    verticalArrangement = Arrangement.Center
                ){
                    MyIconTextField(
                        label = stringResource(id = R.string.email),
                        Icons.Outlined.Email,
                        onTextChange = {
                            viewModel.onEvent(LoginEvent.EmailChanged(it))
                        },
                        value = state.email,
                        isError = state.emailError != null
                    )
                    if(state.emailError != null){
                        ValidateError(
                            state.emailError!!
                        )
                    }
                    MyIconPasswordField(
                        label = stringResource(id = R.string.password),
                        Icons.Outlined.Lock,
                        onTextChange = {
                            viewModel.onEvent(LoginEvent.PasswordChanged(it))
                        },
                        value = state.password,
                        isError = state.passwordError != null
                    )
                    if(state.passwordError != null){
                        ValidateError(
                            state.passwordError!!
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        SmallMessage(
                            text = stringResource(id = R.string.create_account),
                            {
                                navigateToSignUp()
                            }
                        )
                        SmallMessage(
                            text = stringResource(id = R.string.forgot_password),
                            {
                                navigateToResetPassword()
                            }
                        )
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    verticalArrangement = Arrangement.Top
                ){
                    LoginButton(
                        text = stringResource(R.string.login),
                        MaterialTheme.colorScheme.primary,
                        onClick = {
                            viewModel.onEvent(LoginEvent.Submit)
                        }
                    )
                    Spacer(modifier = Modifier.heightIn(20.dp))
                    LoginButtonWithIcon(
                        text = stringResource(id = R.string.google),
                        color = MaterialTheme.colorScheme.background,
                        icon = R.drawable.ic_google_logo,
                        onClick = {
                            val gso = GoogleSignInOptions.Builder(
                                GoogleSignInOptions.DEFAULT_SIGN_IN
                            ).requestEmail().requestIdToken(CONSTANTS.WEB_CLIENT_SEVER).build()

                            val googleSignInClient = GoogleSignIn.getClient(context,gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    ) {

                    }

                }
            }
        }
    LaunchedEffect(key1 = state){
        if(state.loginError != null){
            Toast.makeText(context, state.loginError, Toast.LENGTH_LONG).show()
        }
        if(state.isLoginSuccess){
            navigateToHomeScreen()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    PiWatchTheme {
        //LoginScreen({}, null)
    }
}