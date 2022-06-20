package com.gones.quizzapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gones.quizzapp.ui.quizz.QuizzScreen
import com.gones.quizzapp.ui.quizz.QuizzViewModel
import com.gones.quizzapp.ui.theme.QuizzAppTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: QuizzViewModel = getViewModel()

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.state.isLoading
            }
        }

        setContent {
            QuizzAppTheme {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Score : ${viewModel.score}",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(16.dp)
                                .clickable {
                                    viewModel.openDialog = true
                                }
                        ) {
                            Text(
                                text = viewModel.userName,
                                fontSize = 18.sp,
                                textAlign = TextAlign.End,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }

                    QuizzScreen(
                        viewModel = viewModel
                    )
                }

                if (viewModel.openDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            viewModel.openDialog = false
                        },
                        title = {
                            Text(text="Choisisser un pseudo")
                        },
                        text = {
                            Column() {
                                TextField(
                                    value = viewModel.userName,
                                    onValueChange = {
                                        viewModel.setUsername(it)
                                    }
                                )
                            }
                        },
                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { viewModel.openDialog = false }
                                ) {
                                    Text("Valider")
                                }
                            }
                        },
                        properties = DialogProperties(
                            dismissOnClickOutside = false
                        )
                    )
                }

            }
        }
    }
}