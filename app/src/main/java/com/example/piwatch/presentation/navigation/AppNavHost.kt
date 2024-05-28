import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.piwatch.presentation.navigation.AppRoute
import com.example.piwatch.presentation.screens.home_screen.HomeScreen
import com.example.piwatch.presentation.screens.home_screen.HomeViewModel
import com.example.piwatch.presentation.screens.library_screen.LibraryScreen
import com.example.piwatch.presentation.screens.library_screen.LibraryViewModel
import com.example.piwatch.presentation.screens.login_screen.LoginScreen
import com.example.piwatch.presentation.screens.login_screen.LoginViewModel
import com.example.piwatch.presentation.screens.movie_detail.MovieDetailScreen
import com.example.piwatch.presentation.screens.movie_detail.MovieDetailViewModel
import com.example.piwatch.presentation.screens.movie_with_genre.MovieWithGenreViewModel
import com.example.piwatch.presentation.screens.movie_with_genre.movieWithGenre
import com.example.piwatch.presentation.screens.password_reset.PassWordResetViewModel
import com.example.piwatch.presentation.screens.password_reset.PasswordResetScreen
import com.example.piwatch.presentation.screens.playlist_screen.PlayList
import com.example.piwatch.presentation.screens.playlist_screen.PlaylistViewModel
import com.example.piwatch.presentation.screens.search_screen.SearchScreenViewModel
import com.example.piwatch.presentation.screens.search_screen.searchScreen
import com.example.piwatch.presentation.screens.signup_screen.SignupViewModel
import com.example.piwatch.presentation.screens.welcome_screen.WelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(
            route = AppRoute.LOGIN.route
        ){
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navigateToSignUp = {
                    navController.navigate(AppRoute.SIGNUP.route)
                },
                navigateToResetPassword = {
                    navController.navigate(AppRoute.RESET_PASSWORD.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(AppRoute.HOME.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                viewModel = loginViewModel
            )
        }
        composable(
            route = AppRoute.SIGNUP.route
        ){
            val signUpViewModel = hiltViewModel<SignupViewModel>()
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
            val passwordResetViewModel = hiltViewModel<PassWordResetViewModel>()
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
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                navigateToMovieDetail = {movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")},
                navigateToSearchScreen = {
                    navController.navigate(AppRoute.SEARCH.route)
                },
                navigateToLibraryScreen = {
                    navController.navigate(AppRoute.LIBRARY.route)
                },
                viewModel = homeViewModel
            )
        }
        composable(
            route = AppRoute.SEARCH.route
        ){
            val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>()
            searchScreen(
                navigateToMovieDetail = {movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")},
                navigateToHomeScreen = {
                    navController.navigate(AppRoute.HOME.route)
                },
                navigateToLibraryScreen = {
                    navController.navigate(AppRoute.LIBRARY.route)
                },
                viewModel = searchScreenViewModel
            )
        }
        composable(
            route = AppRoute.LIBRARY.route
        ){
            val libraryViewModel = hiltViewModel<LibraryViewModel>()
            LibraryScreen(
                navigateToSearchScreen = {
                    navController.navigate(AppRoute.SEARCH.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(AppRoute.HOME.route)
                },
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")
                },
                navigateToPlaylist = { playlistId, userId ->
                    navController.navigate("${AppRoute.PLAYLIST.route}/$userId/${playlistId}")
                },
                navigateToMovieWithGenre = { genreId ->
                    navController.navigate("${AppRoute.MOVIE_WITH_GENRE.route}/$genreId")
                },
                navigateToLogin = {
                    Log.d("navigate To Login", startDestination)
                    navController.navigate(AppRoute.LOGIN.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                navigateToMovieWithGenre = { genreId ->
                    navController.navigate("${AppRoute.MOVIE_WITH_GENRE.route}/$genreId")
                },
                navigateToSearchScreen = {
                    navController.navigate(AppRoute.SEARCH.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(AppRoute.HOME.route)
                },
                navigateToLibraryScreen = {
                    navController.navigate("${AppRoute.LIBRARY.route}")
                },
            )
        }
        composable(
            route = AppRoute.PLAYLIST.route + "/{user_id}" + "/{playlist_id}",
            arguments = listOf(navArgument("user_id") { type = NavType.StringType },
                navArgument("playlist_id") { type = NavType.IntType }
            )
        ) {
            val playlistViewModel = hiltViewModel<PlaylistViewModel>()
            PlayList(
                navigateToLibrary = { navController.navigate(AppRoute.LIBRARY.route) },
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")
                },
                viewModel = playlistViewModel
            )
        }
        composable(
            route = AppRoute.MOVIE_WITH_GENRE.route + "/{genre_id}",
            arguments = listOf(navArgument("genre_id") {
                type = NavType.IntType
            })
        ) {
            val movieWithGenreViewModel = hiltViewModel<MovieWithGenreViewModel>()
            movieWithGenre(
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${AppRoute.MOVIE_DETAIL.route}/$movieId")
                },
                viewModel = movieWithGenreViewModel
            )
        }
        composable(
            route = AppRoute.WELCOME.route
        ) {
            WelcomeScreen(
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(AppRoute.LOGIN.route)
                }
            )
        }
    }
}