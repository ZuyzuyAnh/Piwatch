package com.example.piwatch.presentation.screens.welcome_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.piwatch.util.OnBoardingPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val pages = listOf(
            OnBoardingPage.First,
            OnBoardingPage.Second
        )

        val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
            HorizontalPagerIndicator(
                pagerState = pagerState, pageCount = pages.size, modifier = Modifier.weight(1f),
            )
            FinishButton(modifier = Modifier.weight(1f), pagerState = pagerState) {
                viewModel.saveOnBoardingState(true)
                navigateToLogin()
            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(
            text = stringResource(id = onBoardingPage.title),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 1
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PagerScreenPrvFirst(
) {
    PagerScreen(onBoardingPage = OnBoardingPage.Second)
}