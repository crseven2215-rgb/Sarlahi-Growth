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
import com.example.data.local.CollegeEntity
import com.example.data.local.ScholarshipEntity
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.SkyTertiary

@Composable
fun CollegesScreen(
    colleges: List<CollegeEntity>,
    scholarships: List<ScholarshipEntity>,
    comparisonList: List<CollegeEntity>,
    onToggleCompare: (CollegeEntity) -> Unit,
    onOpenComparison: () -> Unit,
    onToggleBookmark: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(0) } // 0=Colleges, 1=Scholarships, 2=Learning Center
    var searchQuery by remember { mutableStateOf("") }
    var selectedAffiliation by remember { mutableStateOf("All") }

    val affiliations = listOf("All", "Tribhuvan University (TU)", "Kathmandu University", "CTEVT")

    val filteredColleges = colleges.filter { col ->
        val matchesSearch = searchQuery.isBlank() ||
                col.name.contains(searchQuery, ignoreCase = true) ||
                col.programs.contains(searchQuery, ignoreCase = true) ||
                col.location.contains(searchQuery, ignoreCase = true)

        val matchesAffiliation = selectedAffiliation == "All" || col.affiliation.contains(selectedAffiliation, ignoreCase = true)
        matchesSearch && matchesAffiliation
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Tab row: Colleges | Scholarships | Learning Center
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = ForestGreenPrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedSection == 0,
                onClick = { selectedSection = 0 },
                text = { Text("Colleges & Campuses", fontWeight = if (selectedSection == 0) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
            )
            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = { Text("Scholarships", fontWeight = if (selectedSection == 1) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
            )
            Tab(
                selected = selectedSection == 2,
                onClick = { selectedSection = 2 },
                text = { Text("Learning Center", fontWeight = if (selectedSection == 2) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (selectedSection) {
                0 -> {
                    // Colleges Section
                    item {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search colleges, engineering, BBA, BCA, +2...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Affiliation chips
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(affiliations) { aff ->
                                val isSelected = selectedAffiliation == aff
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedAffiliation = aff },
                                    label = { Text(aff, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    // Comparison bar if any selected
                    if (comparisonList.isNotEmpty()) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onOpenComparison() }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CompareArrows, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${comparisonList.size} colleges selected to compare",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Text(
                                        text = "View Side-by-Side ➔",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    items(filteredColleges) { col ->
                        val isCompared = comparisonList.any { it.id == col.id }
                        CollegeItemCard(
                            college = col,
                            isCompared = isCompared,
                            onToggleCompare = { onToggleCompare(col) },
                            onBookmark = { onToggleBookmark("COLLEGE", col.id, col.name, col.location) }
                        )
                    }
                }

                1 -> {
                    // Scholarships Section
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = ForestGreenPrimary.copy(alpha = 0.12f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.School, contentDescription = null, tint = ForestGreenPrimary)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Sarlahi Student Financial Aid Desk",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = ForestGreenPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Government, bilateral, and municipal scholarships available for Sarlahi students studying in Kathmandu or home campuses. Deadlines are verified.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    items(scholarships) { sch ->
                        ScholarshipCard(
                            scholarship = sch,
                            onBookmark = { onToggleBookmark("SCHOLARSHIP", sch.id, sch.title, sch.amountOrCoverage) }
                        )
                    }
                }

                2 -> {
                    // Learning Center Section
                    item {
                        Text(
                            text = "Free Career & Academic Skills Hub",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Empowering Sarlahi youth with modern skills for high-paying careers.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(learningModules) { mod ->
                        LearningModuleCard(module = mod)
                    }
                }
            }
        }
    }
}

data class LearningModule(
    val title: String,
    val category: String,
    val duration: String,
    val description: String,
    val keySkills: List<String>
)

val learningModules = listOf(
    LearningModule(
        title = "Lok Sewa Section Officer & NaSu Masterclass",
        category = "Civil Service Exam",
        duration = "8 Modules • 14 Hours",
        description = "Written notes, current affairs analysis from Gorkhapatra, GK tricks, and past 10-year question breakdown by passed Sarlahi officers.",
        keySkills = listOf("Constitution of Nepal", "GK & IQ", "Administrative Writing")
    ),
    LearningModule(
        title = "Professional CV & Interview Prep for Kathmandu Tech",
        category = "Career Skills",
        duration = "4 Modules • 6 Hours",
        description = "Learn how to format ATS-friendly resumes, highlight Sarlahi background as resilience, and answer behavioral interview questions with confidence.",
        keySkills = listOf("Resume Design", "Mock Interview", "Salary Negotiation")
    ),
    LearningModule(
        title = "Modern Kotlin & Android Development from Scratch",
        category = "Technology",
        duration = "12 Modules • 25 Hours",
        description = "Build real mobile apps using Kotlin and Jetpack Compose. Mentored by senior software architects from Sarlahi.",
        keySkills = listOf("Kotlin", "Jetpack Compose", "Room Database")
    ),
    LearningModule(
        title = "Financial Literacy & Banking Career Readiness",
        category = "Banking & Finance",
        duration = "6 Modules • 10 Hours",
        description = "Master MS Excel for banking, understand Nepal Rastra Bank directives, and prepare for assistant relationship manager entrance exams.",
        keySkills = listOf("Advanced Excel", "Financial Statements", "Microfinance")
    )
)

@Composable
fun LearningModuleCard(module: LearningModule, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = module.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(text = "⏱️ ${module.duration}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = module.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = module.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                module.keySkills.forEach { skill ->
                    Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                        Text(text = skill, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /* Open learning */ },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Start Free Course", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CollegeItemCard(
    college: CollegeEntity,
    isCompared: Boolean,
    onToggleCompare: () -> Unit,
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
                    Text(text = college.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = "📍 ${college.location}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onBookmark, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(6.dp), color = SkyTertiary.copy(alpha = 0.12f)) {
                    Text(
                        text = college.affiliation,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SkyTertiary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(shape = RoundedCornerShape(6.dp), color = AmberSecondary.copy(alpha = 0.12f)) {
                    Text(
                        text = "⭐ ${college.rating} / 5.0",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmberSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Fee Range: ${college.feeRange}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(text = "Programs: ${college.programs}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onToggleCompare,
                    shape = RoundedCornerShape(8.dp),
                    colors = if (isCompared) ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer) else ButtonDefaults.outlinedButtonColors()
                ) {
                    Icon(
                        imageVector = if (isCompared) Icons.Default.Check else Icons.Default.CompareArrows,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isCompared) "In Comparison" else "Compare", fontSize = 11.sp)
                }

                Button(
                    onClick = { /* Contact */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Admissions Info", fontSize = 11.sp)
                }
            }
        }
    }
}
