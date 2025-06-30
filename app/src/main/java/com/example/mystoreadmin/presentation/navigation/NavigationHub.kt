package com.example.mystoreadmin.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.mystoreadmin.presentation.ui.AddCategoryScreenUI
import com.example.mystoreadmin.presentation.ui.AddProductScreenUI

@Composable
fun NavigationHub() {
    val backStack = rememberNavBackStack(AddProductScreen)
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {backStack.removeLastOrNull()},
            entryProvider = entryProvider {
                entry(AddProductScreen) { key ->
                    AddProductScreenUI(innerPadding)
                }
                entry(AddCategoryScreen){ key ->
                    AddCategoryScreenUI(innerPadding)
                }
            }
        )
    }
}