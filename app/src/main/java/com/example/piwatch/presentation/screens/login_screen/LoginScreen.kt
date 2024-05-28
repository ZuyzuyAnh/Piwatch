package com.example.piwatch.presentation.screens.login_screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.piwatch.R
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.LoginButtonWithIcon
import com.example.piwatch.presentation.components.MyIconPasswordField
import com.example.piwatch.presentation.components.MyIconTextField
import com.example.piwatch.presentation.components.PiWatchLogo
import com.example.piwatch.presentation.components.SmallMessage
import com.example.piwatch.presentation.components.ValidateError
import com.example.piwatch.util.CONSTANTS
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginScreen(
    navigateToSignUp: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: LoginViewModel
) {
    var state = viewModel.state.collectAsState().value
    var context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            try {
                val result = account.getResult(ApiException::class.java)
                Log.d("credentials google", "${account.result}")
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                Log.d("credentials google", "$it")
            }
        }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ){
                    PiWatchLogo()
                    HeadingTextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.welcome),
                        textAlign = TextAlign.Center,
                        weight = FontWeight.ExtraBold
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            stringResource(id = state.emailError!!)
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
                            stringResource(id = state.passwordError!!)
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
                    Spacer(modifier = Modifier.height(10.dp))
                    if (!state.isLoginSuccess && state.loginError != null) {
                        ValidateError(text = state.loginError!!)
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            ).requestEmail().requestIdToken(CONSTANTS.OAUTH).build()

                            val googleSignInClient = GoogleSignIn.getClient(context,gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                    )
                }
            }
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        }
    LaunchedEffect(key1 = state){
        if(state.isLoginSuccess){
            navigateToHomeScreen()
        }
    }

}

