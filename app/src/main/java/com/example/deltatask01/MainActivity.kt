package com.example.deltatask01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deltatask01.ui.theme.DELTATASK01Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DELTATASK01Theme {
                val viewModel = viewModel<GameViewModel>()
                GameBoard(
                    viewModel = viewModel )


                }
            }
        }


    }



