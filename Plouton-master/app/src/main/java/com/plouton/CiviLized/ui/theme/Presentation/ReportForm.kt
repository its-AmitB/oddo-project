package com.plouton.CiviLized.ui.theme.Presentation


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

data class Category(
    val  id: String,
    val name: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RoadsScreen(navController: NavController) {
    var description by remember { mutableStateOf("") }
var selectedCategory by remember { mutableStateOf(Category("","","")) }
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf< List<Uri?>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(3),
        onResult = { uri-> selectedImageUri = uri}
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( Color(0xFFE91E63).copy(alpha = 0.1f))
            .padding(start = 16.dp, end = 16.dp)
           , verticalArrangement = Arrangement.SpaceEvenly
    ) {






        Text(
            text = "Report Issue",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        var expanded by remember { mutableStateOf(false) }

        val categories = listOf(
            Category("roads", "Roads", "Road maintenance and infrastructure issues"),
            Category("lightning", "Lightning", "Street lighting and electrical issues"),
            Category("water_supply", "Water Supply", "Water distribution and quality issues"),
            Category("cleanliness", "Cleanliness", "Sanitation and waste management"),
            Category("public_safety", "Public Safety", "Safety and security concerns"),
            Category("obstruction", "Obstruction", "Blocked roads and pathways")
        )

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
            Text(
                text = "Category",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.name ?: "Select a category",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown arrow"
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6B46C1),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = category.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = category.description,
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            },
                            onClick = {
                               selectedCategory=category
                                   expanded = false
                            },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // Show selected category description
            selectedCategory?.let { category ->
                Text(
                    text = category.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }


      Column (modifier = Modifier.fillMaxWidth().wrapContentHeight(), verticalArrangement = Arrangement.Center) {
          Text(
              text = "Add images",
              fontSize = 16.sp,
              fontWeight = FontWeight.Medium,
              color = Color.Black
              , modifier = Modifier.padding(bottom = 5.dp)
          )

          Text(
              text = "You can upload 3 images to support your ticket",
              fontSize = 12.sp,
              color = Color.Gray,
              modifier = Modifier.padding(bottom = 10.dp)
          )

          AnimatedVisibility(visible = selectedImageUri.isNotEmpty()){

             Row(modifier = Modifier.fillMaxWidth(1f).horizontalScroll(rememberScrollState())
                  .height(120.dp)) {
                 selectedImageUri.forEach {it->
                     AsyncImage(model = it, contentDescription = null, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                 }
}
              }

          AnimatedVisibility(visible =selectedImageUri.isEmpty() ) {
              Box(
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(120.dp)
                      .border(
                          width = 2.dp,
                          color = Color.Gray.copy(alpha = 0.5f),
                          shape = RoundedCornerShape(8.dp)
                      )

                      .clip(RoundedCornerShape(8.dp))
                      .background( Color(0xFF042C96).copy(alpha = 0.1f)),
                  contentAlignment = Alignment.Center
              ) {
                  Icon(
                      imageVector = Icons.Default.Share,
                      contentDescription = "Add image",
                      tint = Color.Gray,
                      modifier = Modifier.size(48.dp).clickable{
launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly, maxItems = 3))
                      }
                  )
              }
          }


          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp),
              horizontalArrangement = Arrangement.End
          ) {

              Row(
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(top = 8.dp),
                  horizontalArrangement = Arrangement.End
              ) {
                  Row(
                      verticalAlignment = Alignment.CenterVertically
                  ) {
                      Icon(
                          imageVector = Icons.Default.Info,
                          contentDescription = "Info",
                          tint = Color(0xFF6B46C1),
                          modifier = Modifier.size(16.dp)
                      )
                      Spacer(modifier = Modifier.width(4.dp))
                      Text(
                          text = "Info",
                          color = Color(0xFF6B46C1),
                          fontSize = 12.sp
                      )
                  }
              }
          }
      }

        Column (modifier = Modifier.fillMaxWidth().wrapContentHeight(), verticalArrangement = Arrangement.Center) {

            Text(
                text = "Add Description",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
                , modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = "You can write description to support your ticket",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Please describe your issue...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6B46C1),
                    focusedLabelColor = Color(0xFF6B46C1)
                )
            )
        }


        Button(
            onClick = {

                if (description.isNotBlank() && selectedCategory != Category("","","")&&selectedImageUri.isNotEmpty()) {

                    Toast.makeText(context,"Report Submitted", Toast.LENGTH_SHORT).show()
navController.navigate("HomePage"){
    popUpTo("HomePage"){
        inclusive = true
    }
}
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B46C1)
            ),
            enabled = description.isNotBlank() &&  selectedCategory != Category("","","")&&selectedImageUri.isNotEmpty()
        ) {
            Text(
                text = "Submit",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

    }
}

