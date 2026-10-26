package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.ApplicationStatus
import com.example.data.model.JobType
import com.example.data.model.MentorshipStatus
import com.example.data.model.RoomType
import com.example.data.model.UserRole

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val fullName: String,
    val role: UserRole,
    val phone: String = "",
    val sarlahiOrigin: String = "Malangwa, Sarlahi",
    val currentCity: String = "Kathmandu",
    val education: String = "Bachelor in IT (TU)",
    val skills: String = "Android, Kotlin, Web Design, Accounting",
    val careerGoal: String = "Software Engineer & Community Mentor",
    val isVerified: Boolean = true,
    val bio: String = "Student from Sarlahi striving to build skills and contribute back home.",
    val studentCardId: String = "SGC-2026-SAR-0492"
)

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey
    val id: String,
    val employerId: String,
    val title: String,
    val companyName: String,
    val location: String,
    val salaryRange: String,
    val jobType: JobType,
    val category: String,
    val experienceLevel: String,
    val description: String,
    val requirements: String,
    val isVerifiedEmployer: Boolean = true,
    val deadline: String = "Nov 30, 2026",
    val postedDate: String = "Just now",
    val isAbroad: Boolean = false
)

@Entity(tableName = "job_applications")
data class JobApplicationEntity(
    @PrimaryKey
    val id: String,
    val jobId: String,
    val jobTitle: String,
    val companyName: String,
    val applicantId: String,
    val applicantName: String,
    val applicantEmail: String,
    val applicantPhone: String,
    val coverNote: String,
    val status: ApplicationStatus = ApplicationStatus.APPLIED,
    val appliedDate: String = "Today"
)

@Entity(tableName = "colleges")
data class CollegeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val location: String,
    val affiliation: String, // TU, KU, PoU, CTEVT
    val programs: String,
    val feeRange: String,
    val rating: Float = 4.5f,
    val hostelAvailable: Boolean = true,
    val scholarshipAvailable: Boolean = true,
    val phone: String = "+977-1-4245678",
    val website: String = "www.college.edu.np",
    val description: String = ""
)

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey
    val id: String,
    val ownerId: String,
    val ownerName: String,
    val ownerPhone: String,
    val title: String,
    val location: String,
    val rentPerMonth: Int,
    val roomType: RoomType,
    val facilities: String, // WiFi, 24/7 Water, Inverter, Balcony
    val distanceToCollege: String = "10 mins walk from TU/Colleges",
    val isVerified: Boolean = true,
    val latitude: Double = 27.7172,
    val longitude: Double = 85.3240,
    val description: String = ""
)

@Entity(tableName = "scholarships")
data class ScholarshipEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val provider: String,
    val amountOrCoverage: String,
    val deadline: String,
    val eligibility: String,
    val category: String,
    val applyLink: String,
    val isVerified: Boolean = true
)

@Entity(tableName = "mentors")
data class MentorEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val sarlahiOrigin: String,
    val currentDesignation: String,
    val companyOrField: String,
    val expertise: String,
    val yearsExperience: Int,
    val isVerified: Boolean = true,
    val bio: String,
    val sessionCount: Int = 12
)

@Entity(tableName = "mentorship_requests")
data class MentorshipRequestEntity(
    @PrimaryKey
    val id: String,
    val mentorId: String,
    val mentorName: String,
    val studentId: String,
    val studentName: String,
    val studentEmail: String,
    val topic: String,
    val message: String,
    val status: MentorshipStatus = MentorshipStatus.PENDING,
    val dateRequested: String = "Today"
)

@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey
    val id: String,
    val authorId: String,
    val authorName: String,
    val authorRole: UserRole,
    val title: String,
    val content: String,
    val tag: String, // Sarlahi News, Accommodation, Study Group, Career
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: String = "Today"
)

@Entity(tableName = "marketplace_items")
data class MarketplaceItemEntity(
    @PrimaryKey
    val id: String,
    val sellerId: String,
    val sellerName: String,
    val sellerPhone: String,
    val title: String,
    val price: Int,
    val category: String, // Books, Tech, Furniture, Cycle
    val condition: String, // Like New, Good, Used
    val location: String,
    val description: String,
    val createdAt: String = "Today"
)

@Entity(tableName = "sarlahi_resources")
data class SarlahiResourceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String, // Transport, Local Office, Emergency, Student Club, Spot
    val location: String,
    val contact: String,
    val details: String
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val category: String, // Job, Scholarship, Application, Mentorship, Sarlahi
    val timestamp: String = "Just now",
    val isRead: Boolean = false
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val itemType: String, // JOB, ROOM, SCHOLARSHIP, COLLEGE
    val itemId: String,
    val title: String,
    val subtitle: String
)
