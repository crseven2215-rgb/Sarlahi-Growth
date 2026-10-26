package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserEntity
import com.example.data.model.ApplicationStatus
import com.example.data.model.UserRole
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.SkyTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SarlahiTopBar(
    user: UserEntity?,
    unreadNotificationsCount: Int,
    onOpenNotifications: () -> Unit,
    onOpenStudentId: () -> Unit,
    onOpenRoleSwitcher: () -> Unit,
    onOpenAuth: () -> Unit,
    onOpenAdmin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Logo & App Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onOpenStudentId() }
                        .padding(vertical = 4.dp)
                        .testTag("app_logo_row")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(ForestGreenPrimary, Color(0xFF1B8A5A))
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Sarlahi Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SARLAHI GROWTH",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AmberSecondary.copy(alpha = 0.15f),
                                contentColor = AmberSecondary
                            ) {
                                Text(
                                    text = "CONNECT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "Study • Work • Live • Grow",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Action icons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Admin badge if available or toggle
                    IconButton(
                        onClick = onOpenAdmin,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("admin_panel_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AdminPanelSettings,
                            contentDescription = "Admin Panel",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Role switch chip
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onOpenRoleSwitcher() }
                            .testTag("role_switcher_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = when (user?.role) {
                                    UserRole.EMPLOYER -> Icons.Default.Business
                                    UserRole.MENTOR -> Icons.Default.Psychology
                                    UserRole.ADMIN -> Icons.Default.Shield
                                    else -> Icons.Default.Person
                                },
                                contentDescription = "Current Role",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = user?.role?.name?.take(3) ?: "STU",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    // Notification with unread badge
                    IconButton(
                        onClick = onOpenNotifications,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("notifications_btn")
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadNotificationsCount > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    ) {
                                        Text("$unreadNotificationsCount")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Student ID or Avatar button
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable { onOpenStudentId() }
                            .testTag("student_id_btn")
                    ) {
                        Text(
                            text = (user?.fullName?.firstOrNull() ?: 'S').uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SarlahiNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = modifier.fillMaxWidth()
    ) {
        val items = listOf(
            Triple("Home", Icons.Filled.Home, Icons.Outlined.Home),
            Triple("Jobs", Icons.Filled.Work, Icons.Outlined.WorkOutline),
            Triple("Colleges", Icons.Filled.School, Icons.Outlined.School),
            Triple("Rooms", Icons.Filled.HolidayVillage, Icons.Outlined.HolidayVillage),
            Triple("Sarlahi", Icons.Filled.Explore, Icons.Outlined.Explore)
        )

        items.forEachIndexed { index, (label, selectedIcon, unselectedIcon) ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == index) selectedIcon else unselectedIcon,
                        contentDescription = label
                    )
                },
                label = { Text(label, fontSize = 11.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.testTag("nav_tab_$index")
            )
        }
    }
}

@Composable
fun LocationSegmentBar(
    selectedSegment: String,
    onSelectSegment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val segments = listOf("All", "Sarlahi", "Kathmandu", "Abroad")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        segments.forEach { segment ->
            val isSelected = selectedSegment.equals(segment, ignoreCase = true)
            FilterChip(
                selected = isSelected,
                onClick = { onSelectSegment(segment) },
                label = {
                    Text(
                        text = when (segment) {
                            "Sarlahi" -> "📍 Sarlahi District"
                            "Kathmandu" -> "🏛️ Kathmandu"
                            "Abroad" -> "✈️ Abroad / Gulf"
                            else -> "🌐 All Locations"
                        },
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.testTag("location_chip_$segment")
            )
        }
    }
}

@Composable
fun ApplicationStatusBadge(status: ApplicationStatus, modifier: Modifier = Modifier) {
    val (bgColor, textColor, label) = when (status) {
        ApplicationStatus.APPLIED -> Triple(Color(0xFFE2E8F0), Color(0xFF334155), "Applied")
        ApplicationStatus.UNDER_REVIEW -> Triple(Color(0xFFFEF3C7), Color(0xFF92400E), "Under Review")
        ApplicationStatus.SHORTLISTED -> Triple(Color(0xFFE0E7FF), Color(0xFF3730A3), "Shortlisted")
        ApplicationStatus.INTERVIEW -> Triple(Color(0xFFCCFBF1), Color(0xFF115E59), "Interview")
        ApplicationStatus.SELECTED -> Triple(Color(0xFFDCFCE7), Color(0xFF166534), "Selected ✓")
        ApplicationStatus.REJECTED -> Triple(Color(0xFFFEE2E2), Color(0xFF991B1B), "Closed")
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun VerifiedBadge(text: String = "Verified", modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = ForestGreenPrimary.copy(alpha = 0.12f),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Verified",
                tint = ForestGreenPrimary,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = ForestGreenPrimary
            )
        }
    }
}
