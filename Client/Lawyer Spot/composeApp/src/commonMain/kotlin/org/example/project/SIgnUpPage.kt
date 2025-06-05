package org.example.project

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignUpScreen(){

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") }
    val roles = listOf("Client", "Lawyer")


    //Main Parent
    Box(modifier = Modifier.fillMaxSize().padding(20.dp)){

        //Elements Columns
        Column(modifier = Modifier.padding(5.dp)
            .padding(top = 30.dp)
        ){
            //Header
            Text("Sign Up", fontWeight = FontWeight.Bold, fontSize = 30.sp)

            Spacer(modifier = Modifier.height(15.dp))

            Text("Please signup before get started.", fontWeight = FontWeight.Light, fontSize = 17.sp)

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
                modifier = Modifier.fillMaxWidth()
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

            Spacer(modifier = Modifier.height(30.dp))

            //Profile Picture
            Box(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.fillMaxWidth()){

                    AsyncImage(
                        model = "https://randomuser.me/api/portraits/men/75.jpg",
                        contentDescription = null,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(90.dp))
                            .size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ElevatedButton(onClick = {},
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ){
                        Text("Upload Profile Photo")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text("Select Role")


            }

        }

    }
}