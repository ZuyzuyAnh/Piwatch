import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.presentation.Navigation.AppRoute
import com.example.piwatch.presentation.screens.auth_state.AuthViewModel
import com.example.piwatch.presentation.screens.home_screen.HomeScreen
import com.example.piwatch.presentation.screens.home_screen.HomeViewModel
import com.example.piwatch.presentation.screens.library_screen.LibraryViewModel
import com.example.piwatch.presentation.screens.library_screen.libraryScreen
import com.example.piwatch.presentation.screens.login_screen.LoginScreen
import com.example.piwatch.presentation.screens.login_screen.LoginViewModel
import com.example.piwatch.presentation.screens.movie_detail.MovieDetailScreen
import com.example.piwatch.presentation.screens.movie_detail.MovieDetailViewModel
import com.example.piwatch.presentation.screens.password_reset.PassWordResetViewModel
import com.example.piwatch.presentation.screens.password_reset.PasswordResetScreen
import com.example.piwatch.presentation.screens.search_screen.SearchScreenViewModel
import com.example.piwatch.presentation.screens.search_screen.searchScreen
import com.example.piwatch.presentation.screens.signup_screen.SignupViewModel

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    navController: NavHostController,
) {
    Log.d("AppNavHostreInit", "")
    val authState by viewModel.authState.collectAsState()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val libraryViewModel = hiltViewModel<LibraryViewModel>()
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val passwordResetViewModel = hiltViewModel<PassWordResetViewModel>()
    val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>()
    val signUpViewModel = hiltViewModel<SignupViewModel>()
    NavHost(
        navController = navController,
        startDestination = if(authState is AuthEvent.LogedIn) AppRoute.HOME.route else AppRoute.LOGIN.route
    ){
        composable(
            route = AppRoute.LOGIN.route
        ){
            LoginScreen(
                navigateToSignUp = {
                    navController.navigate(AppRoute.SIGNUP.route)
                },
                navigateToResetPassword = {
                    navController.navigate(AppRoute.RESET_PASSWORD.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(AppRoute.HOME.route)
                },
                viewModel = loginViewModel
            )
        }
        composable(
            route = AppRoute.SIGNUP.route
        ){
            SignUpScreen(
                navigateToLogin = {
                    navController.navigate(AppRoute.LOGIN.route)
                },
                viewModel = signUpViewModel,
                navigateToHomeScreen = {navController.navigate(AppRoute.HOME.route)}
            )
        }
        composable(
            route = AppRoute.RESET_PASSWORD.route
        ){
            PasswordResetScreen(
                navigateToLogin = {
                    navController.navigate(AppRoute.LOGIN.route)
                },
                viewModel = passwordResetViewModel
            )
        }
        composable(
            route = AppRoute.HOME.route
        ){
            HomeScreen(
                navigateToMovieDetail = {movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")},
                navigateToSearchScreen = {
                    navController.navigate("${AppRoute.SEARCH.route}")
                },
                navigateToLibraryScreen = {
                    navController.navigate("${AppRoute.LIBRARY.route}")
                },
                viewModel = homeViewModel
            )
        }
        composable(
            route = AppRoute.SEARCH.route
        ){
            searchScreen(
                navigateToMovieDetail = {movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")},
                navigateToHomeScreen = {
                    navController.navigate("${AppRoute.HOME.route}")
                },
                navigateToLibraryScreen = {
                    navController.navigate("${AppRoute.LIBRARY.route}")
                },
                viewModel = searchScreenViewModel
            )
        }
        composable(
            route = AppRoute.LIBRARY.route
        ){
            libraryScreen(
                navigateToSearchScreen = {
                    navController.navigate("${AppRoute.SEARCH.route}")
                },
                navigateToHomeScreen = {
                    navController.navigate("${AppRoute.HOME.route}")
                },
                viewModel = libraryViewModel
            )
        }
        composable(
            route = AppRoute.MOVIE_DETAIL.route + "/{movie_id}",
            arguments = listOf(navArgument("movie_id"){ type = NavType.IntType })
        ){
            val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailScreen(
               viewModel = movieDetailViewModel,
               navigateToMovieDetail = {movieId ->
                   navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")},
           )
        }
    }
}