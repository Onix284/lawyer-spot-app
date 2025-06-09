package com.example.lawyerspot.Screens

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.lawyerspot.AllViewModels.AuthViewModel
import com.example.lawyerspot.Data.Model.UserRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel()
){

    //Values to pass in backend
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf("https://randomuser.me/api/portraits/men/75.jpg".toUri()) }

    //Observe Result
    val signUpResult by viewModel.signupResult.collectAsState()

    //Extras
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Client", "Lawyer")
    var isDialogOn by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    //Main Parent
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)){

        //Elements Columns
        Column(modifier = Modifier
            .padding(5.dp)
            .padding(top = 10.dp)
            .verticalScroll(scrollState)
        ){
            //Header
            Row{
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 30.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterStart))

                    AsyncImage(
                        model = profileImageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .size(70.dp)
                            .clip(RoundedCornerShape(90.dp))
                            .clickable(onClick = { isDialogOn = true }),
                    )
                }

            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Please signup before get started.", fontWeight = FontWeight.Light, fontSize = 17.sp)

            Spacer(modifier = Modifier.height(20.dp))

            //Name TextField
            Text(
                text = "Name",// or any style you prefer
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp
            )

            OutlinedTextField(
                value = name.value,
                onValueChange = {name.value = it },
                placeholder = {Text("Enter Your Name")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Email TextField
            Text(
                text = "Email",// or any style you prefer
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = {email.value = it },
                placeholder = {Text("Enter Your Email")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Password TextField
            Text(
                text = "Password",// or any style you prefer
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp,
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it },
                placeholder = {Text("Enter Your Password")},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )


            Spacer(modifier = Modifier.height(20.dp))

            //Select Role
            Row(modifier = Modifier.fillMaxWidth()) {

                Text(text = "Select Role", modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .weight(0.4f))


                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded},
                    modifier = Modifier.weight(0.6f)
                ) {
                    OutlinedTextField(
                        value = selectedRole,
                        onValueChange = {selectedRole = it},
                        readOnly = true,
                        label = {Text("Select Role")},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {expanded = false}
                    ){
                        roles.forEach { role ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedRole = role
                                    expanded = false
                                },
                                text = { Text(text = role) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            PhotoOptionDialog(
                show = isDialogOn,
                onDismiss = {isDialogOn = false},
                onConfirm = {isDialogOn = false
                    Toast.makeText(context, "Profile Photo Updated", Toast.LENGTH_SHORT).show()},
                onCancel = {isDialogOn = false
                    Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
                },
                onImageSelected = {
                    uri ->
                    profileImageUri = uri
                }
            )


            Spacer(modifier = Modifier.height(20.dp))

            //Signup Button
            FilledTonalButton(onClick = {
                val request = UserRequest(
                    name = name.value,
                    email = email.value,
                    password = password.value,
                    role = selectedRole,
                    imageURI = profileImageUri.toString()
                )
                viewModel.signUp(request)
                 },
                modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 90.dp)
                .height(50.dp)){
                Text("Signup", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth()){
                //Login Button
                Row(modifier = Modifier.align(alignment = Alignment.Center)){
                    Text(text = "Already have an account?", fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Login Now", fontWeight = FontWeight.Medium, color = Color.Blue)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun PhotoOptionDialog(
    show : Boolean,
    onDismiss : ()-> Unit,
    onConfirm : () -> Unit,
    onCancel : () -> Unit,
    onImageSelected : (Uri) -> Unit
){

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.RequestPermission().apply {
        }) {}

    var tempSelectedUri by remember { mutableStateOf<Uri?>(null) }
    var imageUri by remember { mutableStateOf("${context.filesDir}/temp.jpg".toUri()) }

    //Camera Launcher
    val CameraLauncher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.TakePicture()) {
        success ->
        if (success) {
            tempSelectedUri?.let {
                imageUri = it
            }
            Toast.makeText(context, "Image capture: Successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Image capture: Failed", Toast.LENGTH_SHORT).show()
        }
    }

    //Gallery Launcher
    val GalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()
    ){
        imgURI ->
            imgURI?.let {
                tempSelectedUri = it
            }
    }

    //Alert Dialog
    if(show){
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier.height(250.dp),
            title = {
                Text("Add Your Photo")
            },
            text = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)){

                    //Gallery Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(horizontal = 15.dp)
                            .clickable(onClick = {
                                GalleryLauncher.launch("image/*")
                            }),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Filter,
                                contentDescription = null,
                                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Gallery", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                        }
                    }

                    //Camera Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(horizontal = 15.dp)
                            .clickable(onClick = {
                               if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PERMISSION_GRANTED){
                                   permissionLauncher.launch(Manifest.permission.CAMERA)
                               }
                                else
                               {
                                   val uri= createImageUri(context)
                                   tempSelectedUri = uri
                                   uri?.let {
                                       CameraLauncher.launch(it)
                                   }
                               }
                            }),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraEnhance,
                                contentDescription = null,
                                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Camera", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                        }
                    }
                }
            },

            //Confirm Button
            confirmButton = {
                TextButton(
                    onClick = {
                        tempSelectedUri?.let {
                            onImageSelected(it)
                        }
                        onConfirm()
                    }
                ){
                    Text("Add", modifier = Modifier.padding(end = 5.dp), fontSize = 17.sp)
                }
            },

            //Cancel Button
            dismissButton = {
                TextButton(
                    onClick = onCancel
                ) {
                    Text("Cancel", fontSize = 17.sp)
                }
            },
        )
    }
}

//Create Image Uri on Camera Button Click
fun createImageUri(context: Context) : Uri? {

    val contentResolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
    }

    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}