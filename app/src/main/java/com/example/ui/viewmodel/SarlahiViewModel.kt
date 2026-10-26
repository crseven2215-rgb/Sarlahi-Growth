package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.*
import com.example.data.model.ApplicationStatus
import com.example.data.model.JobType
import com.example.data.model.MentorshipStatus
import com.example.data.model.RoomType
import com.example.data.model.UserRole
import com.example.data.repository.SarlahiRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AppUiState(
    val selectedTab: Int = 0, // 0=Home, 1=Jobs, 2=Colleges, 3=Rooms, 4=Sarlahi Hub
    val searchQuery: String = "",
    val activeFilterCategory: String = "All",
    val selectedLocationSegment: String = "All", // "Sarlahi", "Kathmandu", "Abroad"
    val showAuthDialog: Boolean = false,
    val showNotificationsSheet: Boolean = false,
    val showStudentIdDialog: Boolean = false,
    val showPostJobDialog: Boolean = false,
    val showPostRoomDialog: Boolean = false,
    val showRequestMentorDialog: Boolean = false,
    val showApplyJobDialog: Boolean = false,
    val showAICareerAdvisor: Boolean = false,
    val showMarketplaceSheet: Boolean = false,
    val showPostMarketplaceDialog: Boolean = false,
    val showCreatePostDialog: Boolean = false,
    val showAdminSheet: Boolean = false,
    val showComparisonSheet: Boolean = false,
    val selectedJobForApply: JobEntity? = null,
    val selectedMentorForRequest: MentorEntity? = null,
    val collegeComparisonList: List<CollegeEntity> = emptyList(),
    val snackbarMessage: String? = null
)

class SarlahiViewModel(application: Application) : AndroidViewModel(application) {

    private val database = SarlahiDatabase.getDatabase(application, viewModelScope)
    val repository = SarlahiRepository(database.sarlahiDao(), viewModelScope)

    val currentUser = repository.currentUser.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SeedData.defaultUser
    )

    val allJobs = repository.allJobs.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allColleges = repository.allColleges.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allRooms = repository.allRooms.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allScholarships = repository.allScholarships.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allMentors = repository.allMentors.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allPosts = repository.allPosts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allMarketplace = repository.allMarketplaceItems.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allSarlahiResources = repository.allSarlahiResources.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allApplications = repository.allApplications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val myApplications = repository.myApplications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val notifications = repository.notifications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val bookmarks = repository.bookmarks.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val mentorshipRequests = repository.myMentorshipRequests.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val mentorRequestsForMe = repository.mentorRequestsForMe.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun selectTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setLocationSegment(location: String) {
        _uiState.update { it.copy(selectedLocationSegment = location) }
    }

    fun setCategoryFilter(category: String) {
        _uiState.update { it.copy(activeFilterCategory = category) }
    }

    fun openAuthDialog() {
        _uiState.update { it.copy(showAuthDialog = true) }
    }

    fun closeAuthDialog() {
        _uiState.update { it.copy(showAuthDialog = false) }
    }

    fun openNotifications() {
        _uiState.update { it.copy(showNotificationsSheet = true) }
    }

    fun closeNotifications() {
        _uiState.update { it.copy(showNotificationsSheet = false) }
    }

    fun openStudentId() {
        _uiState.update { it.copy(showStudentIdDialog = true) }
    }

    fun closeStudentId() {
        _uiState.update { it.copy(showStudentIdDialog = false) }
    }

    fun openApplyJob(job: JobEntity) {
        _uiState.update { it.copy(selectedJobForApply = job, showApplyJobDialog = true) }
    }

    fun closeApplyJob() {
        _uiState.update { it.copy(selectedJobForApply = null, showApplyJobDialog = false) }
    }

    fun openRequestMentor(mentor: MentorEntity) {
        _uiState.update { it.copy(selectedMentorForRequest = mentor, showRequestMentorDialog = true) }
    }

    fun closeRequestMentor() {
        _uiState.update { it.copy(selectedMentorForRequest = null, showRequestMentorDialog = false) }
    }

    fun openAICareer() {
        _uiState.update { it.copy(showAICareerAdvisor = true) }
    }

    fun closeAICareer() {
        _uiState.update { it.copy(showAICareerAdvisor = false) }
    }

    fun openMarketplace() {
        _uiState.update { it.copy(showMarketplaceSheet = true) }
    }

    fun closeMarketplace() {
        _uiState.update { it.copy(showMarketplaceSheet = false) }
    }

    fun openPostJob() {
        _uiState.update { it.copy(showPostJobDialog = true) }
    }

    fun closePostJob() {
        _uiState.update { it.copy(showPostJobDialog = false) }
    }

    fun openPostRoom() {
        _uiState.update { it.copy(showPostRoomDialog = true) }
    }

    fun closePostRoom() {
        _uiState.update { it.copy(showPostRoomDialog = false) }
    }

    fun openPostMarketplace() {
        _uiState.update { it.copy(showPostMarketplaceDialog = true) }
    }

    fun closePostMarketplace() {
        _uiState.update { it.copy(showPostMarketplaceDialog = false) }
    }

    fun openCreatePost() {
        _uiState.update { it.copy(showCreatePostDialog = true) }
    }

    fun closeCreatePost() {
        _uiState.update { it.copy(showCreatePostDialog = false) }
    }

    fun openAdmin() {
        _uiState.update { it.copy(showAdminSheet = true) }
    }

    fun closeAdmin() {
        _uiState.update { it.copy(showAdminSheet = false) }
    }

    fun toggleCollegeComparison(college: CollegeEntity) {
        _uiState.update { state ->
            val exists = state.collegeComparisonList.any { it.id == college.id }
            val newList = if (exists) {
                state.collegeComparisonList.filter { it.id != college.id }
            } else {
                if (state.collegeComparisonList.size >= 3) {
                    state.collegeComparisonList.drop(1) + college
                } else {
                    state.collegeComparisonList + college
                }
            }
            state.copy(collegeComparisonList = newList, showComparisonSheet = newList.isNotEmpty())
        }
    }

    fun closeComparison() {
        _uiState.update { it.copy(showComparisonSheet = false) }
    }

    fun clearComparison() {
        _uiState.update { it.copy(collegeComparisonList = emptyList(), showComparisonSheet = false) }
    }

    fun switchRole(role: UserRole) {
        viewModelScope.launch {
            repository.switchRole(role)
            showSnackbar("Switched to ${role.name} role view")
        }
    }

    fun login(email: String, role: UserRole) {
        viewModelScope.launch {
            repository.login(email, role)
            closeAuthDialog()
            showSnackbar("Welcome back to Sarlahi Growth Connect!")
        }
    }

    fun loginWithGoogle(role: UserRole) {
        viewModelScope.launch {
            val user = repository.loginWithGoogle(role)
            closeAuthDialog()
            showSnackbar("Signed in as ${user.fullName} (${user.role.name})")
        }
    }

    fun register(fullName: String, email: String, role: UserRole, origin: String, city: String, phone: String) {
        viewModelScope.launch {
            val user = repository.registerUser(fullName, email, role, origin, city, phone)
            closeAuthDialog()
            showSnackbar("Account registered! Welcome, ${user.fullName}.")
        }
    }

    fun submitJobApplication(job: JobEntity, coverNote: String) {
        viewModelScope.launch {
            repository.applyForJob(job, coverNote)
            closeApplyJob()
            showSnackbar("Application submitted successfully for ${job.title}!")
        }
    }

    fun submitMentorshipRequest(mentor: MentorEntity, topic: String, message: String) {
        viewModelScope.launch {
            repository.requestMentorship(mentor, topic, message)
            closeRequestMentor()
            showSnackbar("Mentorship request sent to ${mentor.name}!")
        }
    }

    fun submitJobListing(
        title: String,
        company: String,
        location: String,
        salary: String,
        type: JobType,
        category: String,
        requirements: String,
        description: String
    ) {
        viewModelScope.launch {
            val user = currentUser.value ?: SeedData.defaultUser
            val job = JobEntity(
                id = "job_${System.currentTimeMillis()}",
                employerId = user.id,
                title = title.trim(),
                companyName = company.ifBlank { user.fullName },
                location = location.trim(),
                salaryRange = salary.ifBlank { "Negotiable" },
                jobType = type,
                category = category,
                experienceLevel = "All Levels",
                description = description.trim(),
                requirements = requirements.trim(),
                isVerifiedEmployer = true,
                postedDate = "Just now",
                isAbroad = type == JobType.ABROAD_GULF
            )
            repository.postJob(job)
            closePostJob()
            showSnackbar("Job '${job.title}' posted successfully!")
        }
    }

    fun submitRoomListing(
        title: String,
        location: String,
        rent: Int,
        roomType: RoomType,
        facilities: String,
        phone: String,
        description: String
    ) {
        viewModelScope.launch {
            val user = currentUser.value ?: SeedData.defaultUser
            val room = RoomEntity(
                id = "room_${System.currentTimeMillis()}",
                ownerId = user.id,
                ownerName = user.fullName,
                ownerPhone = phone.ifBlank { user.phone },
                title = title.trim(),
                location = location.trim(),
                rentPerMonth = rent,
                roomType = roomType,
                facilities = facilities.trim(),
                distanceToCollege = "Nearby transportation & campus",
                isVerified = true,
                description = description.trim()
            )
            repository.postRoom(room)
            closePostRoom()
            showSnackbar("Room listing posted in ${room.location}!")
        }
    }

    fun submitCommunityPost(title: String, content: String, tag: String) {
        viewModelScope.launch {
            repository.createCommunityPost(title, content, tag)
            closeCreatePost()
            showSnackbar("Your post was shared with the Sarlahi community!")
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            repository.likePost(postId)
        }
    }

    fun submitMarketplaceItem(
        title: String,
        price: Int,
        category: String,
        condition: String,
        phone: String,
        location: String,
        description: String
    ) {
        viewModelScope.launch {
            val user = currentUser.value ?: SeedData.defaultUser
            val item = MarketplaceItemEntity(
                id = "item_${System.currentTimeMillis()}",
                sellerId = user.id,
                sellerName = user.fullName,
                sellerPhone = phone.ifBlank { user.phone },
                title = title.trim(),
                price = price,
                category = category,
                condition = condition,
                location = location.trim(),
                description = description.trim(),
                createdAt = "Just now"
            )
            repository.createMarketplaceItem(item)
            closePostMarketplace()
            showSnackbar("Marketplace item posted!")
        }
    }

    fun toggleBookmark(type: String, id: String, title: String, subtitle: String) {
        viewModelScope.launch {
            repository.toggleBookmark(type, id, title, subtitle)
            showSnackbar("Bookmarks updated")
        }
    }

    fun updateApplicationStatus(appId: String, status: ApplicationStatus) {
        viewModelScope.launch {
            repository.updateApplicationStatus(appId, status)
            showSnackbar("Applicant status updated to ${status.name}")
        }
    }

    fun updateMentorshipStatus(reqId: String, status: MentorshipStatus) {
        viewModelScope.launch {
            repository.updateMentorshipStatus(reqId, status)
            showSnackbar("Mentorship request marked ${status.name}")
        }
    }

    fun markNotificationRead(id: String) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsRead()
            showSnackbar("All notifications marked as read")
        }
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
