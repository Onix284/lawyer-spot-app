package com.example.lawyerspot.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ClientHomeScreen(){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "User Home Screen",
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}