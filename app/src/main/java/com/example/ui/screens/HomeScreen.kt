package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.*
import com.example.data.model.UserRole
import com.example.ui.components.LocationSegmentBar
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.SkyTertiary

@Composable
fun HomeScreen(
    user: UserEntity?,
    jobs: List<JobEntity>,
    colleges: List<CollegeEntity>,
    rooms: List<RoomEntity>,
    scholarships: List<ScholarshipEntity>,
    mentors: List<MentorEntity>,
    selectedLocation: String,
    onLocationChange: (String) -> Unit,
    onNavigateTab: (Int) -> Unit,
    onApplyJob: (JobEntity) -> Unit,
    onRequestMentor: (MentorEntity) -> Unit,
    onOpenAICareer: () -> Unit,
    onOpenMarketplace: () -> Unit,
    onToggleBookmark: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Banner with official Sarlahi graphic
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .height(200.dp)
            ) {
                // Banner Image
                Image(
                    painter = painterResource(id = R.drawable.sarlahi_hero_banner),
                    contentDescription = "Sarlahi Students Collaboration",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.75f)
                                )
                            )
                        )
                )

                // Text overlay
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AmberSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "NAMASTE SARLAHI YOUTH! 🇳🇵",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Text(
                        text = "Study • Work • Live • Grow",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Connecting Malangwa, Lalbandi, Haripur & Barahathawa to Kathmandu and global pathways.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Location Path Journey Card (Sarlahi -> KTM -> Abroad)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Route, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Your Sarlahi Journey Tracker",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JourneyStep("Sarlahi", "Home Town", Icons.Default.Home, true)
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(14.dp))
                        JourneyStep("Kathmandu", "Colleges & Jobs", Icons.Default.LocationCity, true)
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(14.dp))
                        JourneyStep("Abroad/Career", "Next Horizon", Icons.Default.Public, false)
                    }
                }
            }
        }

        // Location Segment Filter
        item {
            LocationSegmentBar(
                selectedSegment = selectedLocation,
                onSelectSegment = onLocationChange
            )
        }

        // Quick Service Categories Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Explore Opportunities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryTile("Jobs & Work", Icons.Default.Work, ForestGreenPrimary, Modifier.weight(1f)) { onNavigateTab(1) }
                    CategoryTile("Colleges", Icons.Default.School, SkyTertiary, Modifier.weight(1f)) { onNavigateTab(2) }
                    CategoryTile("Rooms & Hostels", Icons.Default.HolidayVillage, AmberSecondary, Modifier.weight(1f)) { onNavigateTab(3) }
                    CategoryTile("Sarlahi Hub", Icons.Default.Explore, Color(0xFF8B5CF6), Modifier.weight(1f)) { onNavigateTab(4) }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryTile("Scholarships", Icons.Default.CardGiftcard, Color(0xFFEC4899), Modifier.weight(1f)) { onNavigateTab(2) }
                    CategoryTile("Mentors", Icons.Default.Psychology, Color(0xFF10B981), Modifier.weight(1f)) { onNavigateTab(4) }
                    CategoryTile("Marketplace", Icons.Default.Storefront, Color(0xFFF59E0B), Modifier.weight(1f)) { onOpenMarketplace() }
                    CategoryTile("AI Advisor", Icons.Default.AutoAwesome, Color(0xFF6366F1), Modifier.weight(1f)) { onOpenAICareer() }
                }
            }
        }

        // Latest Jobs Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Latest Jobs & Internships",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { onNavigateTab(1) }) {
                        Text("View all (${jobs.size})", fontSize = 12.sp, color = ForestGreenPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                jobs.take(3).forEach { job ->
                    JobCard(
                        job = job,
                        onApply = { onApplyJob(job) },
                        onBookmark = { onToggleBookmark("JOB", job.id, job.title, job.companyName) },
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }

        // Student Hostels & Rooms Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Affordable Rooms & Hostels",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { onNavigateTab(3) }) {
                        Text("Explore rooms (${rooms.size})", fontSize = 12.sp, color = ForestGreenPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(rooms.take(4)) { room ->
                        RoomCompactCard(room = room, onBookmark = {
                            onToggleBookmark("ROOM", room.id, room.title, room.location)
                        })
                    }
                }
            }
        }

        // Scholarships with Deadline countdown
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Active Scholarships",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { onNavigateTab(2) }) {
                        Text("All grants", fontSize = 12.sp, color = ForestGreenPrimary)
                    }
                }

                scholarships.take(2).forEach { sch ->
                    ScholarshipCard(scholarship = sch, onBookmark = {
                        onToggleBookmark("SCHOLARSHIP", sch.id, sch.title, sch.amountOrCoverage)
                    }, modifier = Modifier.padding(bottom = 10.dp))
                }
            }
        }

        // Featured Mentors
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Connect with Sarlahi Alumni Mentors",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { onNavigateTab(4) }) {
                        Text("See all mentors", fontSize = 12.sp, color = ForestGreenPrimary)
                    }
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(mentors.take(3)) { mentor ->
                        MentorCompactCard(mentor = mentor, onRequest = { onRequestMentor(mentor) })
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneyStep(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (active) ForestGreenPrimary else MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(text = subtitle, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun CategoryTile(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f))
            ) {
                Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun JobCard(
    job: JobEntity,
    onApply: () -> Unit,
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
                    Text(
                        text = job.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = job.companyName,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (job.isVerifiedEmployer) {
                        VerifiedBadge()
                    }
                    IconButton(onClick = onBookmark, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Bookmark", tint = MaterialTheme.colorScheme.outline)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "📍 ${job.location}",
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AmberSecondary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "💰 ${job.salaryRange}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = job.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Deadline: ${job.deadline}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.outline
                )

                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Apply Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RoomCompactCard(room: RoomEntity, onBookmark: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        modifier = modifier
            .width(220.dp)
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ForestGreenPrimary.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = room.roomType.name.replace("_", " "),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                IconButton(onClick = onBookmark, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = room.title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "📍 ${room.location}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "NPR ${room.rentPerMonth} / month",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AmberSecondary
            )

            Text(
                text = room.distanceToCollege,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ScholarshipCard(scholarship: ScholarshipEntity, onBookmark: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(14.dp),
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
                    Text(text = scholarship.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = scholarship.provider, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onBookmark, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(shape = RoundedCornerShape(8.dp), color = AmberSecondary.copy(alpha = 0.12f)) {
                Text(
                    text = "Grant: ${scholarship.amountOrCoverage}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmberSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Eligibility: ${scholarship.eligibility}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Deadline: ${scholarship.deadline}", fontSize = 11.sp, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.SemiBold)
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ForestGreenPrimary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Verified Govt / Bilateral",
                        fontSize = 10.sp,
                        color = ForestGreenPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MentorCompactCard(mentor: MentorEntity, onRequest: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        modifier = modifier.width(220.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = mentor.name.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = mentor.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                    Text(text = "From ${mentor.sarlahiOrigin}", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = mentor.currentDesignation, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Text(text = mentor.expertise, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = onRequest,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Text("Request Guidance", fontSize = 11.sp)
            }
        }
    }
}
