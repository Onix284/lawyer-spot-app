package com.example.lawyerspot

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(){

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") }
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
                        model = "https://randomuser.me/api/portraits/men/75.jpg",
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
                fontSize = 20.sp
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it },
                placeholder = {Text("Enter Your Password")},
                modifier = Modifier.fillMaxWidth()
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
                        onValueChange = {},
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
                }
            )


            Spacer(modifier = Modifier.height(20.dp))

            //Signup Button
            FilledTonalButton(onClick = {}, modifier = Modifier
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
    onCancel : () -> Unit
){

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<android.net.Uri?>(null) }



    //Camera Launcher
    val CameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        result ->
        if(result) {
            Toast.makeText(context, "Photo Captured!", Toast.LENGTH_SHORT).show()
            // You can update your UI to show the new photo using imageUri
        } else {
            Toast.makeText(context, "Failed to capture photo", Toast.LENGTH_SHORT).show()
        }

    }

    // Function to create temp file Uri
    fun createImageUri(): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.cacheDir  // or getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            imageFile
        )
    }


    //Gallery Launcher
    val GalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imgURI ->

    }


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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(horizontal = 15.dp)
                            .clickable(onClick = {
                                imageUri?.let {uri ->
                                    CameraLauncher.launch(uri)
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


            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(

                        )
                    }
                ){
                    Text("Add", modifier = Modifier.padding(end = 5.dp), fontSize = 17.sp)
                }
            },

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

