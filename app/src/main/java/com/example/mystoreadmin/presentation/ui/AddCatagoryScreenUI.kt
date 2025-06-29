package com.example.mystoreadmin.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mystoreadmin.presentation.viewModel.MyViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.presentation.viewModel.UiState

@Composable
fun AddCategoryScreenUI(innerPadding: PaddingValues,viewModel: MyViewModel = hiltViewModel()) {
    val state by viewModel.addCategoryState.collectAsStateWithLifecycle()
    var categoryName by remember { mutableStateOf("") }


    Column(Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text("Category Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            if (categoryName.isNotEmpty()) {
                viewModel.addCategory(CategoryModel(categoryName))
            }
            categoryName = ""
        }) {
            Text("Add Category")

        }
    }

    when(state){
        is UiState.Error -> {
            val error = state as UiState.Error
            Text(error.message)
        }
        UiState.Loading -> {
            CircularProgressIndicator()
        }
        is UiState.Success<*> -> {
            val successResponse = (state as UiState.Success<String>).data
            Dialog(onDismissRequest = {  }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = successResponse,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        UiState.Empty ->{}
    }
}