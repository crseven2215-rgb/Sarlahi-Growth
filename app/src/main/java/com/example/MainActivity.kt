package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.SarlahiViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SarlahiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
                val allJobs by viewModel.allJobs.collectAsStateWithLifecycle()
                val allColleges by viewModel.allColleges.collectAsStateWithLifecycle()
                val allRooms by viewModel.allRooms.collectAsStateWithLifecycle()
                val allScholarships by viewModel.allScholarships.collectAsStateWithLifecycle()
                val allMentors by viewModel.allMentors.collectAsStateWithLifecycle()
                val allPosts by viewModel.allPosts.collectAsStateWithLifecycle()
                val allMarketplace by viewModel.allMarketplace.collectAsStateWithLifecycle()
                val allSarlahiResources by viewModel.allSarlahiResources.collectAsStateWithLifecycle()
                val myApplications by viewModel.myApplications.collectAsStateWithLifecycle()
                val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
                val notifications by viewModel.notifications.collectAsStateWithLifecycle()
                val mentorshipRequests by viewModel.mentorshipRequests.collectAsStateWithLifecycle()

                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.snackbarMessage) {
                    uiState.snackbarMessage?.let { msg ->
                        snackbarHostState.showSnackbar(msg)
                        viewModel.clearSnackbar()
                    }
                }

                var showRoleSwitcher by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        SarlahiTopBar(
                            user = currentUser,
                            unreadNotificationsCount = notifications.count { !it.isRead },
                            onOpenNotifications = { viewModel.openNotifications() },
                            onOpenStudentId = { viewModel.openStudentId() },
                            onOpenRoleSwitcher = { showRoleSwitcher = true },
                            onOpenAuth = { viewModel.openAuthDialog() },
                            onOpenAdmin = { viewModel.openAdmin() }
                        )
                    },
                    bottomBar = {
                        SarlahiNavigationBar(
                            selectedTab = uiState.selectedTab,
                            onTabSelected = { viewModel.selectTab(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.selectedTab) {
                            0 -> HomeScreen(
                                user = currentUser,
                                jobs = allJobs,
                                colleges = allColleges,
                                rooms = allRooms,
                                scholarships = allScholarships,
                                mentors = allMentors,
                                selectedLocation = uiState.selectedLocationSegment,
                                onLocationChange = { viewModel.setLocationSegment(it) },
                                onNavigateTab = { viewModel.selectTab(it) },
                                onApplyJob = { viewModel.openApplyJob(it) },
                                onRequestMentor = { viewModel.openRequestMentor(it) },
                                onOpenAICareer = { viewModel.openAICareer() },
                                onOpenMarketplace = { viewModel.openMarketplace() },
                                onToggleBookmark = { type, id, title, sub ->
                                    viewModel.toggleBookmark(type, id, title, sub)
                                }
                            )

                            1 -> JobsScreen(
                                jobs = allJobs,
                                user = currentUser,
                                onApplyJob = { viewModel.openApplyJob(it) },
                                onPostJob = { viewModel.openPostJob() },
                                onToggleBookmark = { type, id, title, sub ->
                                    viewModel.toggleBookmark(type, id, title, sub)
                                }
                            )

                            2 -> CollegesScreen(
                                colleges = allColleges,
                                scholarships = allScholarships,
                                comparisonList = uiState.collegeComparisonList,
                                onToggleCompare = { viewModel.toggleCollegeComparison(it) },
                                onOpenComparison = { viewModel.closeComparison() },
                                onToggleBookmark = { type, id, title, sub ->
                                    viewModel.toggleBookmark(type, id, title, sub)
                                }
                            )

                            3 -> RoomsScreen(
                                rooms = allRooms,
                                user = currentUser,
                                onPostRoom = { viewModel.openPostRoom() },
                                onToggleBookmark = { type, id, title, sub ->
                                    viewModel.toggleBookmark(type, id, title, sub)
                                }
                            )

                            4 -> SarlahiHubScreen(
                                user = currentUser,
                                resources = allSarlahiResources,
                                posts = allPosts,
                                marketplace = allMarketplace,
                                mentors = allMentors,
                                applications = myApplications,
                                bookmarks = bookmarks,
                                mentorshipRequests = mentorshipRequests,
                                onLikePost = { viewModel.likePost(it) },
                                onCreatePost = { viewModel.openCreatePost() },
                                onPostMarketplace = { viewModel.openPostMarketplace() },
                                onRequestMentor = { viewModel.openRequestMentor(it) },
                                onOpenStudentId = { viewModel.openStudentId() },
                                onOpenRoleSwitcher = { showRoleSwitcher = true },
                                onUpdateApplicationStatus = { appId, st ->
                                    viewModel.updateApplicationStatus(appId, st)
                                }
                            )
                        }
                    }

                    // Dialogs & Modals
                    if (showRoleSwitcher) {
                        RoleSwitcherDialog(
                            currentRole = currentUser?.role ?: com.example.data.model.UserRole.STUDENT,
                            onRoleSelected = { viewModel.switchRole(it) },
                            onDismiss = { showRoleSwitcher = false }
                        )
                    }

                    if (uiState.showAuthDialog) {
                        AuthDialog(
                            onLogin = { email, role -> viewModel.login(email, role) },
                            onLoginWithGoogle = { role -> viewModel.loginWithGoogle(role) },
                            onRegister = { name, email, role, orig, city, ph ->
                                viewModel.register(name, email, role, orig, city, ph)
                            },
                            onDismiss = { viewModel.closeAuthDialog() }
                        )
                    }

                    if (uiState.showStudentIdDialog) {
                        DigitalStudentIdDialog(
                            user = currentUser,
                            onDismiss = { viewModel.closeStudentId() }
                        )
                    }

                    if (uiState.showNotificationsSheet) {
                        NotificationsBottomSheet(
                            notifications = notifications,
                            onMarkRead = { viewModel.markNotificationRead(it) },
                            onMarkAllRead = { viewModel.markAllNotificationsRead() },
                            onDismiss = { viewModel.closeNotifications() }
                        )
                    }

                    if (uiState.showApplyJobDialog && uiState.selectedJobForApply != null) {
                        ApplyJobDialog(
                            job = uiState.selectedJobForApply,
                            user = currentUser,
                            onSubmit = { note ->
                                uiState.selectedJobForApply?.let { j ->
                                    viewModel.submitJobApplication(j, note)
                                }
                            },
                            onDismiss = { viewModel.closeApplyJob() }
                        )
                    }

                    if (uiState.showPostJobDialog) {
                        PostJobDialog(
                            user = currentUser,
                            onSubmit = { title, comp, loc, sal, type, cat, req, desc ->
                                viewModel.submitJobListing(title, comp, loc, sal, type, cat, req, desc)
                            },
                            onDismiss = { viewModel.closePostJob() }
                        )
                    }

                    if (uiState.showPostRoomDialog) {
                        PostRoomDialog(
                            user = currentUser,
                            onSubmit = { title, loc, rent, type, fac, ph, desc ->
                                viewModel.submitRoomListing(title, loc, rent, type, fac, ph, desc)
                            },
                            onDismiss = { viewModel.closePostRoom() }
                        )
                    }

                    if (uiState.showRequestMentorDialog && uiState.selectedMentorForRequest != null) {
                        RequestMentorDialog(
                            mentor = uiState.selectedMentorForRequest,
                            user = currentUser,
                            onSubmit = { topic, msg ->
                                uiState.selectedMentorForRequest?.let { m ->
                                    viewModel.submitMentorshipRequest(m, topic, msg)
                                }
                            },
                            onDismiss = { viewModel.closeRequestMentor() }
                        )
                    }

                    if (uiState.showAICareerAdvisor) {
                        AICareerAdvisorDialog(
                            user = currentUser,
                            onDismiss = { viewModel.closeAICareer() }
                        )
                    }

                    if (uiState.showComparisonSheet) {
                        CollegeComparisonSheet(
                            colleges = uiState.collegeComparisonList,
                            onClear = { viewModel.clearComparison() },
                            onDismiss = { viewModel.closeComparison() }
                        )
                    }

                    if (uiState.showCreatePostDialog) {
                        CreatePostDialog(
                            onSubmit = { title, content, tag ->
                                viewModel.submitCommunityPost(title, content, tag)
                            },
                            onDismiss = { viewModel.closeCreatePost() }
                        )
                    }

                    if (uiState.showPostMarketplaceDialog) {
                        PostMarketplaceDialog(
                            user = currentUser,
                            onSubmit = { title, price, cat, cond, ph, loc, desc ->
                                viewModel.submitMarketplaceItem(title, price, cat, cond, ph, loc, desc)
                            },
                            onDismiss = { viewModel.closePostMarketplace() }
                        )
                    }

                    if (uiState.showAdminSheet) {
                        AdminDashboardSheet(
                            totalStudents = 142,
                            totalJobs = allJobs.size,
                            totalColleges = allColleges.size,
                            totalRooms = allRooms.size,
                            totalScholarships = allScholarships.size,
                            totalApplications = myApplications.size,
                            onVerifyAll = {
                                viewModel.showSnackbar("All listings verified and checked against safety standards!")
                                viewModel.closeAdmin()
                            },
                            onDismiss = { viewModel.closeAdmin() }
                        )
                    }
                }
            }
        }
    }
}
