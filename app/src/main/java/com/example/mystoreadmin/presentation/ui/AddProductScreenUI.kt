package com.example.mystoreadmin.presentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mystoreadmin.common.isValidProduct
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.presentation.viewModel.MyViewModel
import com.example.mystoreadmin.presentation.viewModel.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenUI(
    paddingValues: PaddingValues,
    viewModel: MyViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val addProductUiState by viewModel.addProductState.collectAsStateWithLifecycle()
    val categoriesListState by viewModel.getAllCategoriesState.collectAsStateWithLifecycle()
    val addProductPhotosState by viewModel.addProductPhotosState.collectAsStateWithLifecycle()
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<CategoryModel?>(null) }
    val scope = rememberCoroutineScope()
    var isAvailable by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }


    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            productImageUris = uris
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

            if (productImageUris.isNotEmpty()) {

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    items(productImageUris) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current).data(uri).build()
                            ),
                            contentDescription = "Selected product image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))


            }


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
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))

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


            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
                label = { Text("Product Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Launch Product Now.",
                    modifier = Modifier.weight(1f)
                )

                Checkbox(
                    checked = isAvailable,
                    onCheckedChange = { isAvailable = it }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { imagePicker.launch("image/*") }) {
                    Text("Select Images")
                }
                Button(onClick = {

                    viewModel.addProductPhotos(productImageUris)

                }) {
                    Text("Add Product")
                }
            }

        }
        val context = LocalContext.current

        when(addProductPhotosState){
            UiState.Empty -> {

            }
            is UiState.Error -> {}
            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .pointerInput(Unit) {
                            // Consume all touches
                            awaitPointerEventScope {
                                while (true) {
                                    awaitPointerEvent()
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

                    is UiState.Success<*> -> {
                val productPhotosUrls = (addProductPhotosState as UiState.Success).data
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Images Uploaded!", Toast.LENGTH_SHORT).show()
                    val product = Product(
                        category = productCategory,
                        name = productName,
                        price = productPrice.toDoubleOrNull() ?: 0.0,
                        stockQuantity = productQuantity.toIntOrNull() ?: 0,
                        description = productDescription,
                        isAvailable = isAvailable,
                        brand = brand,
                        imageUrls = productPhotosUrls
                    )
                    if (product.isValidProduct()) {
                        viewModel.addProduct(product)
                    }
                }

            }
        }



        var showErrorDialog by remember { mutableStateOf(false) }

        when (addProductUiState) {
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
                            val text = (addProductUiState as UiState.Error).message
                            Text(text)
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
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success<*> -> {
                val successResponse = (addProductUiState as UiState.Success<String>).data
                LaunchedEffect(Unit){
                    scope.launch {
                        snackBarHostState.showSnackbar(successResponse)
                        productName = ""
                        productDescription = ""
                        productPrice = ""
                        productCategory = ""
                        brand = ""
                        selectedCategory = null
                        isAvailable = false
                        productQuantity = ""
                        productImageUris = emptyList()
                        viewModel.resetUiStates()
                    }
                }

//                var showDialog by remember { mutableStateOf(true) }
//
//                if (showDialog) {
//                    Dialog(onDismissRequest = {
//                        showDialog = false
//                        productName = ""
//                        productDescription = ""
//                        productPrice = ""
//                        productCategory = ""
//                        productQuantity = ""
//                        productImageUris = emptyList()
//
//                    }) {
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .padding(16.dp),
//                            shape = RoundedCornerShape(16.dp),
//                        ) {
//                            Column(
//                                modifier = Modifier.fillMaxSize(),
//                                Arrangement.SpaceAround,
//                                Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    text = successResponse
//                                )
//                                Button(
//                                    onClick = {
//                                        showDialog = false
//                                        productName = ""
//                                        productDescription = ""
//                                        productPrice = ""
//                                        productCategory = ""
//                                        productQuantity = ""
//                                        productImageUris = emptyList()
//                                    }, modifier = Modifier.padding(130.dp, 0.dp, 0.dp, 0.dp)
//                                ) {
//                                    Text("Dismiss")
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }

    }

}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomSnackBar(snackBarText: String){
    val snackBarHost = remember{ SnackbarHostState() }
val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHost)
        }
        , modifier = Modifier.wrapContentSize()
    ) {
        LaunchedEffect(Unit){
            scope.launch { snackBarHost.showSnackbar(snackBarText) }
        }
    }
}