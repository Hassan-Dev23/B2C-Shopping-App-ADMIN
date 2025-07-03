package com.example.mystoreadmin.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mystoreadmin.common.isValidProduct
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.presentation.viewModel.MyViewModel
import com.example.mystoreadmin.presentation.viewModel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenUI(paddingValues: PaddingValues, viewModel: MyViewModel = hiltViewModel()) {
    val addProductUiState by viewModel.addProductState.collectAsStateWithLifecycle()
    val categoriesListState by viewModel.getAllCategoriesState.collectAsStateWithLifecycle()
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<CategoryModel?>(null) }
    LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            productImageUri = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text("Product Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = { Text("Product Price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))





            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                // TextField - acts like a button
                OutlinedTextField(
                    value = selectedCategory?.name ?: "",
                    onValueChange = {}, // disable typing
                    readOnly = true, // no keyboard
                    label = { Text("Product Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                        .clickable { expanded = true } // make whole field clickable
                )

                // Dropdown menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (categoriesListState is UiState.Success<*>) {
                        (categoriesListState as UiState.Success<List<CategoryModel>>).data
                            .forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category.name) },
                                    onClick = {
                                        selectedCategory = category
                                        productCategory = category.name
                                        expanded = false
                                    }
                                )
                            }
                    } else if (categoriesListState is UiState.Loading) {
                        CircularProgressIndicator()
                    }

                }
            }


//            Box(modifier = Modifier.fillMaxWidth()) {
//                OutlinedTextField(
//                    value = selectedCategory?.name ?: "",
//                    onValueChange = { },
//                    label = { Text("Product Category") },
//                    modifier = Modifier.fillMaxWidth(),
//                    readOnly = true,
//                    trailingIcon = {
//                        Icon(
//                            Icons.Filled.ArrowDropDown,
//                            "contentDescription",
//                            Modifier.padding(end = 8.dp)
//                        )
//                    }
//                )
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier.width(300.dp) // Make dropdown expensive
//                ) {
//                    when (categoriesListState) {
//
//                        is UiState.Success<*> -> {
//                            val categories =
//                                (categoriesListState as UiState.Success<List<CategoryModel>>).data
//                            categories.forEach { category ->
//
//                                DropdownMenuItem(
//                                    onClick = {
//                                        selectedCategory = category
//                                        productCategory = category.name
//                                        expanded = false
//                                    },
//                                    text = { Text(text = category.name) }
//                                )
//                            }
//                        }
//                        else -> {}
//                    }
//                }
//                // Invisible button to make the whole field clickable
//
//                IconButton(
//                    onClick = { expanded = !expanded },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Icon(Icons.Default.Add,"", tint = Color.Unspecified)
//
//
//                }
//            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
                label = { Text("Product Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { imagePicker.launch("image/*") }) {
                    Text("Select Image")
                }
                Button(onClick = {
                    val product = Product(
                        CATEGORY = productCategory,
                        PRODUCT_NAME = productName,
                        PRODUCT_PRICE = productPrice.toDoubleOrNull() ?: 0.0,
                        PRODUCT_QUANTITY = productQuantity.toIntOrNull() ?: 0,
                        PRODUCT_DESCRIPTION = productDescription,

//
                    )
                    if (product.isValidProduct()) {
                        viewModel.addProduct(product)
                    }
                }) {
                    Text("Add Product")
                }
            }
            productImageUri?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = it).build()
                    ),
                    contentDescription = "Selected product image",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.getAllCategories()
            }) { Text("Reload")}

            when (addProductUiState) {


                UiState.Empty -> {}
                is UiState.Error -> Text("Error : " + (addProductUiState as UiState.Error).message)
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Success<*> -> {
                    val successResponse = (addProductUiState as UiState.Success<String>).data
                    var showDialog by remember { mutableStateOf(true) }
                    if (showDialog) {
                        Dialog(onDismissRequest = {
                            showDialog = false
                            productName = ""
                            productDescription = ""
                            productPrice = ""
                            productCategory = ""
                            productQuantity = ""
                            productImageUri = null
                        }) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    Arrangement.SpaceAround,
                                    Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = successResponse
                                    )
                                    Button(
                                        onClick = {
                                            showDialog = false
                                            productName = ""
                                            productDescription = ""
                                            productPrice = ""
                                            productCategory = ""
                                            productQuantity = ""
                                            productImageUri = null
                                        }, modifier = Modifier.padding(130.dp, 0.dp, 0.dp, 0.dp)
                                    ) {
                                        Text("Dismiss")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}