package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.ApplicationStatus
import com.example.data.model.MentorshipStatus
import com.example.data.model.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class SarlahiRepository(
    private val dao: SarlahiDao,
    private val scope: CoroutineScope
) {
    private val _currentUserId = MutableStateFlow(SeedData.defaultUser.id)
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

    val currentUser: Flow<UserEntity?> = _currentUserId.flatMapLatest { id ->
        dao.getUserById(id)
    }

    val allJobs: Flow<List<JobEntity>> = dao.getAllJobs()
    val allColleges: Flow<List<CollegeEntity>> = dao.getAllColleges()
    val allRooms: Flow<List<RoomEntity>> = dao.getAllRooms()
    val allScholarships: Flow<List<ScholarshipEntity>> = dao.getAllScholarships()
    val allMentors: Flow<List<MentorEntity>> = dao.getAllMentors()
    val allPosts: Flow<List<CommunityPostEntity>> = dao.getAllPosts()
    val allMarketplaceItems: Flow<List<MarketplaceItemEntity>> = dao.getAllMarketplaceItems()
    val allSarlahiResources: Flow<List<SarlahiResourceEntity>> = dao.getAllSarlahiResources()
    val allApplications: Flow<List<JobApplicationEntity>> = dao.getAllApplications()

    val myApplications: Flow<List<JobApplicationEntity>> = _currentUserId.flatMapLatest { id ->
        dao.getApplicationsByApplicant(id)
    }

    val myMentorshipRequests: Flow<List<MentorshipRequestEntity>> = _currentUserId.flatMapLatest { id ->
        dao.getRequestsByStudent(id)
    }

    val mentorRequestsForMe: Flow<List<MentorshipRequestEntity>> = _currentUserId.flatMapLatest { id ->
        dao.getRequestsForMentor(id)
    }

    val notifications: Flow<List<NotificationEntity>> = dao.getNotificationsForUser()

    val bookmarks: Flow<List<BookmarkEntity>> = _currentUserId.flatMapLatest { id ->
        dao.getBookmarks(id)
    }

    init {
        // Guarantee initial data is ready immediately
        scope.launch(Dispatchers.IO) {
            ensureSeeded()
        }
    }

    private suspend fun ensureSeeded() {
        val user = dao.getUserById(SeedData.defaultUser.id).firstOrNull()
        if (user == null) {
            dao.insertUser(SeedData.defaultUser)
            dao.insertJobs(SeedData.seedJobs)
            SeedData.seedApplications.forEach { dao.insertApplication(it) }
            dao.insertColleges(SeedData.seedColleges)
            dao.insertRooms(SeedData.seedRooms)
            dao.insertScholarships(SeedData.seedScholarships)
            dao.insertMentors(SeedData.seedMentors)
            SeedData.seedPosts.forEach { dao.insertPost(it) }
            SeedData.seedMarketplace.forEach { dao.insertMarketplaceItem(it) }
            dao.insertSarlahiResources(SeedData.seedSarlahiResources)
            SeedData.seedNotifications.forEach { dao.insertNotification(it) }
        }
    }

    suspend fun login(email: String, role: UserRole): Boolean {
        val cleanEmail = email.trim().lowercase()
        val user = UserEntity(
            id = "user_${UUID.randomUUID().toString().take(8)}",
            email = cleanEmail,
            fullName = cleanEmail.substringBefore("@").replace(".", " ").replaceFirstChar { it.uppercase() },
            role = role,
            sarlahiOrigin = "Lalbandi, Sarlahi",
            currentCity = "Kathmandu",
            isVerified = true
        )
        dao.insertUser(user)
        _currentUserId.value = user.id
        return true
    }

    suspend fun loginWithGoogle(role: UserRole): UserEntity {
        val googleUser = UserEntity(
            id = "user_google_${role.name.lowercase()}",
            email = "student.sarlahi@gmail.com",
            fullName = if (role == UserRole.EMPLOYER) "Sarlahi Tech Ventures"
                       else if (role == UserRole.MENTOR) "Er. Sudip Chaudhary"
                       else "Aayush Kushwaha",
            role = role,
            phone = "+977-9807654321",
            sarlahiOrigin = "Malangwa, Sarlahi",
            currentCity = "Kathmandu (Kirtipur)",
            isVerified = true,
            studentCardId = "SGC-2026-SAR-0842"
        )
        dao.insertUser(googleUser)
        _currentUserId.value = googleUser.id
        return googleUser
    }

    suspend fun registerUser(
        fullName: String,
        email: String,
        role: UserRole,
        origin: String,
        currentCity: String,
        phone: String
    ): UserEntity {
        val newUser = UserEntity(
            id = "user_${UUID.randomUUID().toString().take(8)}",
            email = email.trim(),
            fullName = fullName.trim(),
            role = role,
            phone = phone.trim(),
            sarlahiOrigin = origin.ifBlank { "Malangwa, Sarlahi" },
            currentCity = currentCity.ifBlank { "Kathmandu" },
            isVerified = true,
            studentCardId = "SGC-2026-SAR-${(1000..9999).random()}"
        )
        dao.insertUser(newUser)
        _currentUserId.value = newUser.id
        return newUser
    }

    suspend fun switchRole(newRole: UserRole) {
        val current = dao.getUserById(_currentUserId.value).firstOrNull() ?: SeedData.defaultUser
        val updated = current.copy(role = newRole)
        dao.updateUser(updated)
    }

    suspend fun updateUser(user: UserEntity) {
        dao.updateUser(user)
    }

    suspend fun applyForJob(job: JobEntity, coverNote: String) {
        val user = dao.getUserById(_currentUserId.value).firstOrNull() ?: SeedData.defaultUser
        val app = JobApplicationEntity(
            id = "app_${UUID.randomUUID().toString().take(8)}",
            jobId = job.id,
            jobTitle = job.title,
            companyName = job.companyName,
            applicantId = user.id,
            applicantName = user.fullName,
            applicantEmail = user.email,
            applicantPhone = user.phone,
            coverNote = coverNote,
            status = ApplicationStatus.APPLIED,
            appliedDate = "Today"
        )
        dao.insertApplication(app)

        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(8)}",
                userId = user.id,
                title = "Application Submitted!",
                message = "You applied for ${job.title} at ${job.companyName}. Track status in your Dashboard.",
                category = "Application",
                timestamp = "Just now"
            )
        )
    }

    suspend fun postJob(job: JobEntity) {
        dao.insertJob(job)
        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(8)}",
                userId = _currentUserId.value,
                title = "Job Listing Published",
                message = "Your listing '${job.title}' is now live for all Sarlahi candidates.",
                category = "Job",
                timestamp = "Just now"
            )
        )
    }

    suspend fun postRoom(room: RoomEntity) {
        dao.insertRoom(room)
        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(8)}",
                userId = _currentUserId.value,
                title = "Room Listed Successfully",
                message = "Your accommodation '${room.title}' in ${room.location} has been posted.",
                category = "Room",
                timestamp = "Just now"
            )
        )
    }

    suspend fun requestMentorship(mentor: MentorEntity, topic: String, message: String) {
        val user = dao.getUserById(_currentUserId.value).firstOrNull() ?: SeedData.defaultUser
        val req = MentorshipRequestEntity(
            id = "req_${UUID.randomUUID().toString().take(8)}",
            mentorId = mentor.id,
            mentorName = mentor.name,
            studentId = user.id,
            studentName = user.fullName,
            studentEmail = user.email,
            topic = topic,
            message = message,
            status = MentorshipStatus.PENDING,
            dateRequested = "Today"
        )
        dao.insertMentorshipRequest(req)
        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(8)}",
                userId = user.id,
                title = "Mentorship Request Sent",
                message = "Your request on '$topic' has been sent to ${mentor.name}.",
                category = "Mentorship",
                timestamp = "Just now"
            )
        )
    }

    suspend fun updateMentorshipStatus(requestId: String, status: MentorshipStatus) {
        dao.updateMentorshipStatus(requestId, status)
    }

    suspend fun updateApplicationStatus(applicationId: String, status: ApplicationStatus) {
        dao.updateApplicationStatus(applicationId, status)
    }

    suspend fun createCommunityPost(title: String, content: String, tag: String) {
        val user = dao.getUserById(_currentUserId.value).firstOrNull() ?: SeedData.defaultUser
        val post = CommunityPostEntity(
            id = "post_${UUID.randomUUID().toString().take(8)}",
            authorId = user.id,
            authorName = user.fullName,
            authorRole = user.role,
            title = title,
            content = content,
            tag = tag,
            likesCount = 0,
            commentsCount = 0,
            createdAt = "Just now"
        )
        dao.insertPost(post)
    }

    suspend fun likePost(postId: String) {
        dao.incrementPostLike(postId)
    }

    suspend fun createMarketplaceItem(item: MarketplaceItemEntity) {
        dao.insertMarketplaceItem(item)
    }

    suspend fun toggleBookmark(itemType: String, itemId: String, title: String, subtitle: String) {
        val isBm = dao.isBookmarked(_currentUserId.value, itemType, itemId)
        if (isBm) {
            dao.removeBookmark(_currentUserId.value, itemType, itemId)
        } else {
            dao.insertBookmark(
                BookmarkEntity(
                    id = "${_currentUserId.value}_${itemType}_$itemId",
                    userId = _currentUserId.value,
                    itemType = itemType,
                    itemId = itemId,
                    title = title,
                    subtitle = subtitle
                )
            )
        }
    }

    suspend fun isItemBookmarked(itemType: String, itemId: String): Boolean {
        return dao.isBookmarked(_currentUserId.value, itemType, itemId)
    }

    suspend fun markNotificationAsRead(id: String) {
        dao.markNotificationAsRead(id)
    }

    suspend fun markAllNotificationsRead() {
        dao.markAllNotificationsRead()
    }
}
