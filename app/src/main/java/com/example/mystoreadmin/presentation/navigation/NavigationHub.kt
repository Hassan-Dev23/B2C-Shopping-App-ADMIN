package com.example.mystoreadmin.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.mystoreadmin.presentation.ui.AddCategoryScreenUI
import com.example.mystoreadmin.presentation.ui.AddProductScreenUI

@Composable
fun NavigationHub() {
    val backStack = rememberNavBackStack(AddCategoryScreen)
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry(AddProductScreen) { key ->
                    AddProductScreenUI(innerPadding, snackBarHostState = snackBarHostState, backStack = backStack)
                }
                entry(AddCategoryScreen) { key ->
                    AddCategoryScreenUI(innerPadding , backStack = backStack)
                }
            }
        )
    }
}