package com.example.data.model

enum class UserRole {
    STUDENT,
    EMPLOYER,
    MENTOR,
    ADMIN
}

enum class JobType {
    FULL_TIME,
    PART_TIME,
    INTERNSHIP,
    REMOTE,
    ABROAD_GULF
}

enum class RoomType {
    SINGLE_ROOM,
    DOUBLE_ROOM,
    FLAT,
    BOYS_HOSTEL,
    GIRLS_HOSTEL
}

enum class ApplicationStatus {
    APPLIED,
    UNDER_REVIEW,
    SHORTLISTED,
    INTERVIEW,
    SELECTED,
    REJECTED
}

enum class MentorshipStatus {
    PENDING,
    ACCEPTED,
    COMPLETED,
    DECLINED
}

enum class SarlahiLocationHub {
    SARLAHI_MALANGWA,
    SARLAHI_LALBANDI,
    SARLAHI_HARIPUR,
    SARLAHI_BARAHATHAWA,
    KATHMANDU,
    NEPAL_WIDE,
    ABROAD
}
