package com.gones.quizzapp.ui.quizz

import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizzScreen(
    viewModel: QuizzViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error
            )
        } else {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = Html.fromHtml(state.question, Html.FROM_HTML_MODE_LEGACY).toString(),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))

                for (response: Response in state.responses) {
                    Response(response) {
                        viewModel.checkAnwser(response)
                    }
                }
            }
        }
    }
}

@Composable
fun Response(
    response: Response,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = if (response.state == ResponseStatus.Init) MaterialTheme.colors.surface else if (response.state == ResponseStatus.Correct) MaterialTheme.colors.primary else MaterialTheme.colors.error),
        onClick = onClick
    ) {
        Text(
            text = Html.fromHtml(response.response, Html.FROM_HTML_MODE_LEGACY).toString(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
