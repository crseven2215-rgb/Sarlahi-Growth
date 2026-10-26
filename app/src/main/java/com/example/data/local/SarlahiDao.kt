package com.example.data.local

import androidx.room.*
import com.example.data.model.ApplicationStatus
import com.example.data.model.MentorshipStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface SarlahiDao {

    // Users
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    // Jobs
    @Query("SELECT * FROM jobs ORDER BY id DESC")
    fun getAllJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE employerId = :employerId ORDER BY id DESC")
    fun getJobsByEmployer(employerId: String): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE id = :id LIMIT 1")
    suspend fun getJobById(id: String): JobEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Delete
    suspend fun deleteJob(job: JobEntity)

    // Applications
    @Query("SELECT * FROM job_applications ORDER BY id DESC")
    fun getAllApplications(): Flow<List<JobApplicationEntity>>

    @Query("SELECT * FROM job_applications WHERE applicantId = :applicantId ORDER BY id DESC")
    fun getApplicationsByApplicant(applicantId: String): Flow<List<JobApplicationEntity>>

    @Query("SELECT * FROM job_applications WHERE jobId = :jobId ORDER BY id DESC")
    fun getApplicationsByJob(jobId: String): Flow<List<JobApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(application: JobApplicationEntity)

    @Query("UPDATE job_applications SET status = :status WHERE id = :applicationId")
    suspend fun updateApplicationStatus(applicationId: String, status: ApplicationStatus)

    // Colleges
    @Query("SELECT * FROM colleges ORDER BY rating DESC")
    fun getAllColleges(): Flow<List<CollegeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColleges(colleges: List<CollegeEntity>)

    // Rooms
    @Query("SELECT * FROM rooms ORDER BY id DESC")
    fun getAllRooms(): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<RoomEntity>)

    @Delete
    suspend fun deleteRoom(room: RoomEntity)

    // Scholarships
    @Query("SELECT * FROM scholarships ORDER BY id DESC")
    fun getAllScholarships(): Flow<List<ScholarshipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScholarships(scholarships: List<ScholarshipEntity>)

    // Mentors
    @Query("SELECT * FROM mentors ORDER BY sessionCount DESC")
    fun getAllMentors(): Flow<List<MentorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMentors(mentors: List<MentorEntity>)

    // Mentorship Requests
    @Query("SELECT * FROM mentorship_requests WHERE mentorId = :mentorId ORDER BY id DESC")
    fun getRequestsForMentor(mentorId: String): Flow<List<MentorshipRequestEntity>>

    @Query("SELECT * FROM mentorship_requests WHERE studentId = :studentId ORDER BY id DESC")
    fun getRequestsByStudent(studentId: String): Flow<List<MentorshipRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMentorshipRequest(request: MentorshipRequestEntity)

    @Query("UPDATE mentorship_requests SET status = :status WHERE id = :requestId")
    suspend fun updateMentorshipStatus(requestId: String, status: MentorshipStatus)

    // Community Posts
    @Query("SELECT * FROM community_posts ORDER BY id DESC")
    fun getAllPosts(): Flow<List<CommunityPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPostEntity)

    @Query("UPDATE community_posts SET likesCount = likesCount + 1 WHERE id = :postId")
    suspend fun incrementPostLike(postId: String)

    // Marketplace
    @Query("SELECT * FROM marketplace_items ORDER BY id DESC")
    fun getAllMarketplaceItems(): Flow<List<MarketplaceItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketplaceItem(item: MarketplaceItemEntity)

    // Sarlahi Resources
    @Query("SELECT * FROM sarlahi_resources ORDER BY category, title")
    fun getAllSarlahiResources(): Flow<List<SarlahiResourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSarlahiResources(resources: List<SarlahiResourceEntity>)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getNotificationsForUser(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markNotificationAsRead(notificationId: String)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsRead()

    // Bookmarks
    @Query("SELECT * FROM bookmarks WHERE userId = :userId")
    fun getBookmarks(userId: String): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE userId = :userId AND itemType = :itemType AND itemId = :itemId")
    suspend fun removeBookmark(userId: String, itemType: String, itemId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE userId = :userId AND itemType = :itemType AND itemId = :itemId)")
    suspend fun isBookmarked(userId: String, itemType: String, itemId: String): Boolean
}
