package com.example.mystoreadmin.presentation.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import coil.compose.rememberAsyncImagePainter
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.presentation.navigation.AddProductScreen
import com.example.mystoreadmin.presentation.viewModel.MyViewModel
import com.example.mystoreadmin.presentation.viewModel.UiState
import java.nio.file.WatchEvent

@Composable
fun AddCategoryScreenUI(innerPadding: PaddingValues, viewModel: MyViewModel = hiltViewModel(), backStack: NavBackStack) {
    val state by viewModel.addCategoryState.collectAsStateWithLifecycle()
    val addCategoryPhotoState by viewModel.addCategoryPhotoState.collectAsStateWithLifecycle()
    var categoryName by remember { mutableStateOf("") }
    var categoryImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            categoryImageUri = it
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick ={
            backStack.removeLastOrNull()
            backStack.add(AddProductScreen)
        } ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")


        }

        // Show the selected image at the top if it's available
        categoryImageUri?.let { uri ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Form section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { imagePicker.launch("image/*") }) {
                    Text("Upload Image")
                }

                Button(onClick = {
                    if (categoryImageUri != null) {
                        viewModel.addCategoryPhoto(categoryImageUri!!)
                    }
                }) {
                    Text("Add Category")
                }

            }

        }
    }

    var showErrorDialog1 by remember { mutableStateOf(false) }
    val context = LocalContext.current
    when (addCategoryPhotoState) {
        UiState.Empty -> {}
        is UiState.Error -> {
            val error = (state as UiState.Error).message

            if (showErrorDialog1) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog1 = false },
                    title = { Text("Error") },
                    text = {

                        Text(error)
                    },
                    confirmButton = {
                        TextButton(onClick = { showErrorDialog1 = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }

        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // dim background
                    .pointerInput(Unit) { /* Consume all touches */ }
                    .zIndex(1f), // ensures it's on top
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is UiState.Success<*> -> {

            val categoryImageUrl = (addCategoryPhotoState as UiState.Success).data
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Image Uploaded!", Toast.LENGTH_SHORT).show()
                if (categoryName.isNotEmpty() && categoryImageUrl.isNotEmpty()) {
                    viewModel.addCategory(
                        CategoryModel(
                            name = categoryName,
                            imageUrl = categoryImageUrl
                        )
                    )
                }

            }
        }
    }
    var showErrorDialog by remember { mutableStateOf(false) }
    when (state) {
        UiState.Empty -> {}
        is UiState.Error -> {


            LaunchedEffect(Unit) {
                showErrorDialog = true
            }
            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error") },
                    text = {
                        val error = (state as UiState.Error).message
                        Text(error)
                    },
                    confirmButton = {
                        TextButton(onClick = { showErrorDialog = false }) {
                            Text("OK")
                        }
                    }
                )
            }

        }

        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // dim background
                    .pointerInput(Unit) { /* Consume all touches */ }
                    .zIndex(1f), // ensures it's on top
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is UiState.Success<*> -> {
            val successResponse = (state as UiState.Success<String>).data
            LaunchedEffect(Unit) {
                Toast.makeText(context, successResponse, Toast.LENGTH_SHORT).show()
                categoryName = ""
                categoryImageUri = null
            }

        }


    }


}