package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.JobEntity
import com.example.data.local.UserEntity
import com.example.data.model.JobType
import com.example.data.model.UserRole
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary

@Composable
fun JobsScreen(
    jobs: List<JobEntity>,
    user: UserEntity?,
    onApplyJob: (JobEntity) -> Unit,
    onPostJob: () -> Unit,
    onToggleBookmark: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedType by remember { mutableStateOf<JobType?>(null) }
    var selectedLocation by remember { mutableStateOf("All") }

    val categories = listOf("All", "Technology & IT", "Banking & Finance", "Education & Teaching", "Design & Creative", "Foreign Employment")
    val locations = listOf("All", "Kathmandu", "Lalbandi, Sarlahi", "Malangwa, Sarlahi", "Dubai, UAE")

    val filteredJobs = jobs.filter { job ->
        val matchesSearch = searchQuery.isBlank() ||
                job.title.contains(searchQuery, ignoreCase = true) ||
                job.companyName.contains(searchQuery, ignoreCase = true) ||
                job.requirements.contains(searchQuery, ignoreCase = true)

        val matchesCategory = selectedCategory == "All" || job.category.equals(selectedCategory, ignoreCase = true)
        val matchesType = selectedType == null || job.jobType == selectedType
        val matchesLocation = selectedLocation == "All" || job.location.contains(selectedLocation, ignoreCase = true)

        matchesSearch && matchesCategory && matchesType && matchesLocation
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
                    placeholder = { Text("Search jobs, skills, companies...") },
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("jobs_search_field")
                )
            }

            // Categories horizontal filter
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            // Job Type and Location sub-filters
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Job type chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedType == null,
                                onClick = { selectedType = null },
                                label = { Text("All Types", fontSize = 10.sp) }
                            )
                        }
                        items(JobType.values()) { type ->
                            FilterChip(
                                selected = selectedType == type,
                                onClick = { selectedType = if (selectedType == type) null else type },
                                label = { Text(type.name.replace("_", " ").lowercase().capitalize(), fontSize = 10.sp) }
                            )
                        }
                    }
                }
            }

            // Gulf & Foreign Employment Safety Banner (Specialized feature for Sarlahi Youth)
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = AmberSecondary.copy(alpha = 0.12f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = AmberSecondary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Safe Foreign Employment (DoFE Verified) ✈️",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AmberSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "All foreign jobs listed on Sarlahi Growth Connect have verified Ministry LT approval. Remember: Never pay cash without government receipt.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Results count
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Available Opportunities (${filteredJobs.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    if (user?.role == UserRole.EMPLOYER) {
                        Text(
                            text = "Employer View",
                            fontSize = 11.sp,
                            color = ForestGreenPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Empty state if needed
            if (filteredJobs.isEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Outlined.WorkOff, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No jobs match your filter criteria.", fontWeight = FontWeight.SemiBold)
                            Text("Try broadening your location or search terms.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            } else {
                items(filteredJobs) { job ->
                    JobCard(
                        job = job,
                        onApply = { onApplyJob(job) },
                        onBookmark = { onToggleBookmark("JOB", job.id, job.title, job.companyName) }
                    )
                }
            }
        }

        // Floating Action Button to post job
        FloatingActionButton(
            onClick = onPostJob,
            containerColor = ForestGreenPrimary,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
                .testTag("post_job_fab")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Job")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Post Job", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}
