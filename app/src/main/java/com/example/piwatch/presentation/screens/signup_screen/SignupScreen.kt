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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.unit.dp
import com.example.piwatch.R
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.MyIconPasswordField
import com.example.piwatch.presentation.components.MyIconTextField
import com.example.piwatch.presentation.components.PiWatchLogo
import com.example.piwatch.presentation.components.SmallMessage
import com.example.piwatch.presentation.components.ValidateError
import com.example.piwatch.presentation.screens.signup_screen.SignupFormEvent
import com.example.piwatch.presentation.screens.signup_screen.SignupViewModel


@Composable
fun SignUpScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SignupViewModel
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    PiWatchLogo()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    HeadingTextComponent(
                        text = stringResource(id = R.string.create_account),
                        weight = FontWeight.ExtraBold
                    )
                    MyIconTextField(
                        label = stringResource(id = R.string.user_name),
                        Icons.Outlined.Person,
                        onTextChange = {
                            viewModel.onEvent(SignupFormEvent.UsernameChanged(it))
                        },
                        value = state.userName,
                        isError = state.userNameError != null
                    )
                    if (state.userNameError != null) {
                        ValidateError(
                            stringResource(id = state.userNameError)
                        )
                    }
                    MyIconTextField(
                        label = stringResource(id = R.string.email),
                        Icons.Outlined.Email,
                        onTextChange = {
                            viewModel.onEvent(SignupFormEvent.EmailChanged(it))
                        },
                        value = state.email,
                        isError = state.emailError != null
                    )
                    if (state.emailError != null) {
                        ValidateError(
                            stringResource(id = state.emailError)
                        )
                    }
                    MyIconPasswordField(
                        label = stringResource(id = R.string.password),
                        Icons.Outlined.Lock,
                        onTextChange = {
                            viewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                        },
                        value = state.password,
                        isError = state.passwordError != null
                    )
                    if (state.passwordError != null) {
                        ValidateError(
                            stringResource(id = state.passwordError)
                        )
                    }
                    MyIconPasswordField(
                        label = stringResource(id = R.string.password_confirm),
                        Icons.Outlined.Lock,
                        onTextChange = {
                            viewModel.onEvent(
                                SignupFormEvent.PasswordConfirmChanged(
                                    state.password,
                                    it
                                )
                            )
                        },
                        value = state.passwordConfrim,
                        isError = state.passwordConfrimError != null
                    )
                    if (state.passwordConfrimError != null) {
                        ValidateError(
                            stringResource(id = state.passwordConfrimError)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        SmallMessage(
                            text = stringResource(id = R.string.have_account),
                            { navigateToLogin() }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (!state.isSignUpSucess && state.errorToast != null) {
                        ValidateError(text = state.errorToast)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    LoginButton(
                        text = stringResource(R.string.singup),
                        MaterialTheme.colorScheme.primary
                    ) {
                        viewModel.onEvent(SignupFormEvent.Submit)
                    }
                    Spacer(modifier = Modifier.heightIn(20.dp))
                }
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
    LaunchedEffect(state.isSignUpSucess) {
        if(state.isSignUpSucess){
            navigateToHomeScreen()
        }
    }
}
