package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.RoomEntity
import com.example.data.local.UserEntity
import com.example.data.model.RoomType
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary

@Composable
fun RoomsScreen(
    rooms: List<RoomEntity>,
    user: UserEntity?,
    onPostRoom: () -> Unit,
    onToggleBookmark: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<RoomType?>(null) }
    var selectedArea by remember { mutableStateOf("All") }

    val areas = listOf("All", "Kirtipur", "Putalisadak", "Tinkune", "Lalbandi")

    val filteredRooms = rooms.filter { room ->
        val matchesSearch = searchQuery.isBlank() ||
                room.title.contains(searchQuery, ignoreCase = true) ||
                room.location.contains(searchQuery, ignoreCase = true) ||
                room.facilities.contains(searchQuery, ignoreCase = true)

        val matchesType = selectedType == null || room.roomType == selectedType
        val matchesArea = selectedArea == "All" || room.location.contains(selectedArea, ignoreCase = true)

        matchesSearch && matchesType && matchesArea
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search rooms near TU, Putalisadak, hostels...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Area chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(areas) { area ->
                        val isSelected = selectedArea == area
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedArea = area },
                            label = { Text(if (area == "All") "All Locations" else "📍 $area", fontSize = 11.sp) }
                        )
                    }
                }
            }

            // Room Type chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    item {
                        FilterChip(
                            selected = selectedType == null,
                            onClick = { selectedType = null },
                            label = { Text("All Room Types", fontSize = 10.sp) }
                        )
                    }
                    items(RoomType.values()) { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = if (selectedType == type) null else type },
                            label = { Text(type.name.replace("_", " ").lowercase().capitalize(), fontSize = 10.sp) }
                        )
                    }
                }
            }

            // Student Housing Safety Notice
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ForestGreenPrimary.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.HomeWork, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Sarlahi Student Safe Housing Network",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenPrimary
                            )
                            Text(
                                text = "Hosts listed here are verified alumni or community members committed to fair student rent without extortion.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Header count
            item {
                Text(
                    text = "Available Rooms & Hostels (${filteredRooms.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            items(filteredRooms) { room ->
                RoomDetailCard(
                    room = room,
                    onCallOwner = {
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${room.ownerPhone}"))
                        runCatching { context.startActivity(dialIntent) }
                    },
                    onBookmark = {
                        onToggleBookmark("ROOM", room.id, room.title, room.location)
                    }
                )
            }
        }

        // Post Room FAB
        FloatingActionButton(
            onClick = onPostRoom,
            containerColor = ForestGreenPrimary,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
                .testTag("post_room_fab")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Room")
                Spacer(modifier = Modifier.width(6.dp))
                Text("List Room", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun RoomDetailCard(
    room: RoomEntity,
    onCallOwner: () -> Unit,
    onBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = ForestGreenPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = room.roomType.name.replace("_", " "),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        if (room.isVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            VerifiedBadge("Inspected")
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = room.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = "📍 ${room.location}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }

                IconButton(onClick = onBookmark, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "NPR ${room.rentPerMonth} / month",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AmberSecondary
                )
                Text(
                    text = "🚶 ${room.distanceToCollege}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)) {
                Text(
                    text = "Facilities: ${room.facilities}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            if (room.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = room.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = "Landlord / Host:", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                    Text(text = room.ownerName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onCallOwner,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Owner (${room.ownerPhone.takeLast(4)})", fontSize = 11.sp)
                }
            }
        }
    }
}
