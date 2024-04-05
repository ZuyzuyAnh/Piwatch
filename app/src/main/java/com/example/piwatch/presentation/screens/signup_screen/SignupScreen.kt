import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.piwatch.presentation.components.PiWatchLogo
import com.example.piwatch.R
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.MyIconPasswordField
import com.example.piwatch.presentation.components.MyIconTextField
import com.example.piwatch.presentation.components.SmallMessage
import com.example.piwatch.presentation.components.ValidateError
import com.example.piwatch.presentation.screens.signup_screen.SignupFormEvent
import com.example.piwatch.presentation.screens.signup_screen.SignupViewModel
import com.example.piwatch.ui.theme.PiWatchTheme
import com.example.piwatch.util.Resource


@Composable
fun SignUpScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SignupViewModel
) {
    var context = LocalContext.current
    var state = viewModel.state.collectAsState().value
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        if(state.isLoading){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
            ){
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
                        HeadingTextComponent(text = stringResource(id = R.string.create_account))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f),
                        verticalArrangement = Arrangement.SpaceAround
                    ){
                        MyIconTextField(
                            label = stringResource(id = R.string.user_name),
                            Icons.Outlined.Person,
                            onTextChange = {
                                viewModel.onEvent(SignupFormEvent.UsernameChanged(it))
                            },
                            value = state.userName,
                            isError = state.userNameError != null
                        )
                        if(state.userNameError != null){
                            ValidateError(
                                state.userNameError!!
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
                        if(state.emailError != null){
                            ValidateError(
                                state.emailError!!
                            )
                        }
                        MyIconPasswordField(
                            label = stringResource(id = R.string.password),
                            Icons.Outlined.Lock,
                            onTextChange = {
                                viewModel?.onEvent(SignupFormEvent.PasswordChanged(it))
                            },
                            value = state.password,
                            isError = state.passwordError != null
                        )
                        if(state.passwordError != null){
                            ValidateError(
                                state.passwordError!!
                            )
                        }
                        MyIconPasswordField(
                            label = stringResource(id = R.string.password_confirm),
                            Icons.Outlined.Lock,
                            onTextChange = {
                                viewModel.onEvent(SignupFormEvent.PasswordConfirmChanged(state.password,it))
                            },
                            value = state.passwordConfrim,
                            isError = state.passwordConfrimError != null
                        )
                        if(state.passwordConfrimError != null){
                            ValidateError(
                                state.passwordConfrimError!!
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
                                {navigateToLogin()}
                            )
                        }
                    }
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        verticalArrangement = Arrangement.Top
                    ){
                        LoginButton(
                            text = stringResource(R.string.singup),
                            MaterialTheme.colorScheme.primary,
                            { viewModel.onEvent(SignupFormEvent.Submit) }
                        )
                        Spacer(modifier = Modifier.heightIn(20.dp))
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = state){
        if(state.isSignUpSucess){
            navigateToHomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPrv(){
    PiWatchTheme {
        //SignUpScreen({}, null)
    }
}