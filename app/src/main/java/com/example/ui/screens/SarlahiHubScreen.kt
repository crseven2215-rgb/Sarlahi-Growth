package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.*
import com.example.data.model.ApplicationStatus
import com.example.data.model.UserRole
import com.example.ui.components.ApplicationStatusBadge
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary

@Composable
fun SarlahiHubScreen(
    user: UserEntity?,
    resources: List<SarlahiResourceEntity>,
    posts: List<CommunityPostEntity>,
    marketplace: List<MarketplaceItemEntity>,
    mentors: List<MentorEntity>,
    applications: List<JobApplicationEntity>,
    bookmarks: List<BookmarkEntity>,
    mentorshipRequests: List<MentorshipRequestEntity>,
    onLikePost: (String) -> Unit,
    onCreatePost: () -> Unit,
    onPostMarketplace: () -> Unit,
    onRequestMentor: (MentorEntity) -> Unit,
    onOpenStudentId: () -> Unit,
    onOpenRoleSwitcher: () -> Unit,
    onUpdateApplicationStatus: (String, ApplicationStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf(0) } // 0=Dashboard, 1=Directory, 2=Community, 3=Marketplace, 4=Mentorship

    val subTabs = listOf(
        Pair("My Dashboard", Icons.Default.Dashboard),
        Pair("District Hub", Icons.Default.Place),
        Pair("Community", Icons.Default.Forum),
        Pair("Marketplace", Icons.Default.Store),
        Pair("Mentors", Icons.Default.Psychology)
    )

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = ForestGreenPrimary,
            edgePadding = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            subTabs.forEachIndexed { index, (label, icon) ->
                Tab(
                    selected = activeSubTab == index,
                    onClick = { activeSubTab = index },
                    icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    text = { Text(label, fontSize = 11.sp, fontWeight = if (activeSubTab == index) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (activeSubTab) {
                    0 -> {
                        // My Dashboard
                        item {
                            // Personalized Greeting Banner
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column {
                                            Text(
                                                text = "Namaste, ${user?.fullName ?: "Sarlahi Student"} 👋",
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = ForestGreenPrimary
                                            )
                                            Text(
                                                text = "Role: ${user?.role?.name ?: "STUDENT"} • Origin: ${user?.sarlahiOrigin ?: "Sarlahi"}",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        Button(
                                            onClick = onOpenStudentId,
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                            modifier = Modifier.height(34.dp)
                                        ) {
                                            Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("My ID Card", fontSize = 11.sp)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Your digital profile is 100% verified. Employers and landlords in Kathmandu prioritize candidates with Sarlahi Growth Connect ID.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Application Tracking Section (CRITICAL CUJ)
                        item {
                            Text(
                                text = "Application Tracker (${applications.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (applications.isEmpty()) {
                            item {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("No active job applications yet.", fontWeight = FontWeight.SemiBold)
                                        Text("Explore the Jobs tab and apply with one tap!", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        } else {
                            items(applications) { app ->
                                Card(
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.Top,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(text = app.jobTitle, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                Text(text = app.companyName, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                            }
                                            ApplicationStatusBadge(status = app.status)
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Application lifecycle step indicator
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = "Applied", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreenPrimary)
                                            Text(text = "➔", fontSize = 10.sp)
                                            Text(
                                                text = "Under Review",
                                                fontSize = 10.sp,
                                                fontWeight = if (app.status >= ApplicationStatus.UNDER_REVIEW) FontWeight.Bold else FontWeight.Normal,
                                                color = if (app.status >= ApplicationStatus.UNDER_REVIEW) ForestGreenPrimary else Color.Gray
                                            )
                                            Text(text = "➔", fontSize = 10.sp)
                                            Text(
                                                text = "Shortlisted",
                                                fontSize = 10.sp,
                                                fontWeight = if (app.status >= ApplicationStatus.SHORTLISTED) FontWeight.Bold else FontWeight.Normal,
                                                color = if (app.status >= ApplicationStatus.SHORTLISTED) ForestGreenPrimary else Color.Gray
                                            )
                                            Text(text = "➔", fontSize = 10.sp)
                                            Text(
                                                text = "Interview",
                                                fontSize = 10.sp,
                                                fontWeight = if (app.status >= ApplicationStatus.INTERVIEW) FontWeight.Bold else FontWeight.Normal,
                                                color = if (app.status >= ApplicationStatus.INTERVIEW) ForestGreenPrimary else Color.Gray
                                            )
                                        }

                                        // Employer status changer (if employer role)
                                        if (user?.role == UserRole.EMPLOYER || user?.role == UserRole.ADMIN) {
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Text("Update Status:", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                listOf(ApplicationStatus.UNDER_REVIEW, ApplicationStatus.SHORTLISTED, ApplicationStatus.INTERVIEW, ApplicationStatus.SELECTED).forEach { st ->
                                                    FilterChip(
                                                        selected = app.status == st,
                                                        onClick = { onUpdateApplicationStatus(app.id, st) },
                                                        label = { Text(st.name.take(6), fontSize = 9.sp) }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Bookmarks Section
                        item {
                            Text(
                                text = "Saved Opportunities (${bookmarks.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (bookmarks.isEmpty()) {
                            item {
                                Text("No saved items yet. Tap the bookmark icon on jobs, rooms, or scholarships to pin them here.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        } else {
                            items(bookmarks) { bm ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Column {
                                            Surface(shape = RoundedCornerShape(4.dp), color = ForestGreenPrimary.copy(alpha = 0.12f)) {
                                                Text(text = bm.itemType, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ForestGreenPrimary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = bm.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(text = bm.subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = ForestGreenPrimary)
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // Sarlahi District Directory
                        item {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = ForestGreenPrimary.copy(alpha = 0.12f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.LocationCity, contentDescription = null, tint = ForestGreenPrimary)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Sarlahi District Essential Services Directory", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Direct bus counters to Kathmandu, District Administration (citizenship/passport), hospitals, student clubs, and cultural places.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        items(resources) { res ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = res.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Surface(shape = RoundedCornerShape(6.dp), color = ForestGreenPrimary.copy(alpha = 0.12f)) {
                                            Text(text = res.category, fontSize = 10.sp, color = ForestGreenPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "📍 ${res.location}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = "📞 ${res.contact}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = res.details, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }

                    2 -> {
                        // Community Discussions
                        items(posts) { post ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                            ) {
                                                Text(text = post.authorName.take(1), fontWeight = FontWeight.Bold, color = ForestGreenPrimary)
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text(text = post.authorName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                                Text(text = "${post.authorRole.name} • ${post.createdAt}", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                            }
                                        }

                                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                                            Text(text = post.tag, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = post.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = post.content, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { onLikePost(post.id) }
                                        ) {
                                            Icon(Icons.Default.ThumbUp, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "${post.likesCount} Helpful", fontSize = 11.sp, color = ForestGreenPrimary)
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "${post.commentsCount} Comments", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // Student Marketplace
                        item {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = AmberSecondary.copy(alpha = 0.12f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Storefront, contentDescription = null, tint = AmberSecondary)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Safe Peer-to-Peer Student Marketplace. Buy & sell books, laptops, and room furniture between Sarlahi students.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        items(marketplace) { item ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(text = "NPR ${item.price}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = AmberSecondary)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Condition: ${item.condition} • Category: ${item.category}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                    Text(text = "📍 ${item.location} • Seller: ${item.sellerName}", fontSize = 11.sp, color = ForestGreenPrimary)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = item.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Contact: ${item.sellerPhone}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }

                    4 -> {
                        // Mentorship Network
                        items(mentors) { mentor ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .size(44.dp)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                            ) {
                                                Text(text = mentor.name.take(2).uppercase(), fontWeight = FontWeight.Bold, color = ForestGreenPrimary)
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(text = mentor.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                Text(text = mentor.currentDesignation, fontSize = 12.sp, color = ForestGreenPrimary)
                                                Text(text = "From ${mentor.sarlahiOrigin}", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                            }
                                        }

                                        VerifiedBadge("Mentor")
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Expertise: ${mentor.expertise}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = mentor.bio, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { onRequestMentor(mentor) },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Book 1-on-1 Guidance Session", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Contextual FAB for Community and Marketplace
            if (activeSubTab == 2) {
                FloatingActionButton(
                    onClick = onCreatePost,
                    containerColor = ForestGreenPrimary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 80.dp)
                ) {
                    Icon(Icons.Default.AddComment, contentDescription = "Create Post")
                }
            } else if (activeSubTab == 3) {
                FloatingActionButton(
                    onClick = onPostMarketplace,
                    containerColor = AmberSecondary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 80.dp)
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Post Item")
                }
            }
        }
    }
}
