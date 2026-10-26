package com.example.data.local

import com.example.data.model.ApplicationStatus
import com.example.data.model.JobType
import com.example.data.model.RoomType
import com.example.data.model.UserRole

object SeedData {

    val defaultUser = UserEntity(
        id = "student_sarlahi_01",
        email = "aayush.kushwaha@sarlahi.edu.np",
        fullName = "Aayush Kushwaha",
        role = UserRole.STUDENT,
        phone = "+977-9807654321",
        sarlahiOrigin = "Malangwa-02, Sarlahi",
        currentCity = "Kathmandu (Kirtipur)",
        education = "BCA (Bachelor in Computer Applications) - 3rd Year",
        skills = "Kotlin, Android Compose, Python, UI Design, Lok Sewa GK",
        careerGoal = "Software Engineer & Community Mentor for Sarlahi Youth",
        isVerified = true,
        bio = "Proud Sarlahi youth currently studying in Kathmandu. Passionate about technology, digital literacy, and helping newcomers from our home district.",
        studentCardId = "SGC-2026-SAR-0842"
    )

    val seedJobs = listOf(
        JobEntity(
            id = "job_01",
            employerId = "emp_01",
            title = "Junior Android & Mobile Developer",
            companyName = "Everest Tech Innovations",
            location = "Kathmandu (Putalisadak) / Hybrid",
            salaryRange = "NPR 35,000 - 55,000 / month",
            jobType = JobType.FULL_TIME,
            category = "Technology & IT",
            experienceLevel = "Entry Level / Freshers Welcome",
            description = "Exciting opportunity for fresh computer science graduates from Madhesh and Terai districts. Work on modern Kotlin, Jetpack Compose, and cloud-integrated mobile platforms.",
            requirements = "Basic knowledge of Kotlin or Java, Android fundamentals, passion to learn modern declarative UI. Mentorship provided by senior engineers.",
            isVerifiedEmployer = true,
            deadline = "Nov 15, 2026",
            postedDate = "2 days ago"
        ),
        JobEntity(
            id = "job_02",
            employerId = "emp_02",
            title = "Branch Relationship Assistant",
            companyName = "Sarlahi Sahakari & Finance",
            location = "Lalbandi, Sarlahi",
            salaryRange = "NPR 25,000 - 38,000 / month",
            jobType = JobType.FULL_TIME,
            category = "Banking & Finance",
            experienceLevel = "0 - 1 year",
            description = "Manage customer onboarding, microfinance loan verification, and digital banking promotions across Lalbandi and Haripur municipalities.",
            requirements = "+2 or Bachelor in Management / BBA / BBS, fluent in Maithili, Bhojpuri and Nepali, basic MS Excel skills.",
            isVerifiedEmployer = true,
            deadline = "Nov 25, 2026",
            postedDate = "1 day ago"
        ),
        JobEntity(
            id = "job_03",
            employerId = "emp_03",
            title = "UI/UX & Graphics Design Intern",
            companyName = "Himalayan Digital Studio",
            location = "Kathmandu (Baneshwor)",
            salaryRange = "NPR 15,000 - 20,000 stipend",
            jobType = JobType.INTERNSHIP,
            category = "Design & Creative",
            experienceLevel = "Students / Intern",
            description = "3-month paid internship with potential full-time placement. Create mobile UI mockups, social graphics, and brand assets for Nepali educational clients.",
            requirements = "Figma proficiency, strong aesthetic sense, willingness to learn design systems. Portfolio or sample works required.",
            isVerifiedEmployer = true,
            deadline = "Dec 05, 2026",
            postedDate = "Today"
        ),
        JobEntity(
            id = "job_04",
            employerId = "emp_04",
            title = "Secondary Level Science & Math Teacher",
            companyName = "Sarlahi Model Secondary School",
            location = "Malangwa, Sarlahi",
            salaryRange = "NPR 32,000 - 45,000 / month",
            jobType = JobType.FULL_TIME,
            category = "Education & Teaching",
            experienceLevel = "1+ years",
            description = "Inspire the next generation of Sarlahi scholars in Physics and Mathematics for Grades 9 and 10.",
            requirements = "B.Sc. in Physics, Chemistry or Mathematics. Teaching license preferred. Enthusiastic and student-friendly attitude.",
            isVerifiedEmployer = true,
            deadline = "Dec 10, 2026",
            postedDate = "3 days ago"
        ),
        JobEntity(
            id = "job_05",
            employerId = "emp_05",
            title = "Junior IT Support Technician (Gulf - Dubai)",
            companyName = "Global Apex Manpower (Govt Lic. 1420)",
            location = "Dubai, UAE (Govt Verified Work Permit)",
            salaryRange = "AED 3,200 - 4,000 (NPR 115,000 - 145,000)",
            jobType = JobType.ABROAD_GULF,
            category = "Foreign Employment",
            experienceLevel = "1 - 2 years",
            description = "Official government pre-approved recruitment quota. Free accommodation, medical insurance, and return airfare provided. Zero illegal commission policy.",
            requirements = "Diploma or Bachelor in IT/Computer Hardware, valid passport, English communication, clean police clearance certificate.",
            isVerifiedEmployer = true,
            deadline = "Nov 30, 2026",
            postedDate = "4 days ago",
            isAbroad = true
        )
    )

    val seedColleges = listOf(
        CollegeEntity(
            id = "col_01",
            name = "Pulchowk Campus (IOE, Tribhuvan University)",
            location = "Lalitpur / Kathmandu Valley",
            affiliation = "Tribhuvan University (TU)",
            programs = "B.E. Computer, Civil, Electrical, Electronics, Architecture",
            feeRange = "NPR 3.5 Lakhs - 5.5 Lakhs (Full 4 Years)",
            rating = 4.9f,
            hostelAvailable = true,
            scholarshipAvailable = true,
            phone = "+977-1-5521234",
            website = "www.ioe.edu.np",
            description = "Nepal's premier engineering institution. High quota of merit and government scholarships for students from Terai and provincial districts."
        ),
        CollegeEntity(
            id = "col_02",
            name = "Malangwa Multiple Campus",
            location = "Malangwa-03, Sarlahi",
            affiliation = "Tribhuvan University (TU)",
            programs = "BA, BBS, B.Ed, M.Ed (Nepali, English, Health)",
            feeRange = "NPR 15,000 - 30,000 / year (Government Affiliated)",
            rating = 4.4f,
            hostelAvailable = false,
            scholarshipAvailable = true,
            phone = "+977-46-520111",
            website = "www.malangwacampus.edu.np",
            description = "The central community higher education hub of Sarlahi district. Affordable government subsidized education close to home."
        ),
        CollegeEntity(
            id = "col_03",
            name = "Shanker Dev Campus",
            location = "Putalisadak, Kathmandu",
            affiliation = "Tribhuvan University (TU)",
            programs = "BBA, BBS, BBM, MBS, MBA",
            feeRange = "NPR 45,000 (BBS) to 3.8 Lakhs (BBA)",
            rating = 4.8f,
            hostelAvailable = true,
            scholarshipAvailable = true,
            phone = "+977-1-4226490",
            website = "www.shankerdevcampus.edu.np",
            description = "Nepal's leading business and management college located in the heart of Kathmandu, with hundreds of Sarlahi alumni in leading corporate sectors."
        ),
        CollegeEntity(
            id = "col_04",
            name = "Janajyoti Multiple Campus",
            location = "Lalbandi, Sarlahi",
            affiliation = "Tribhuvan University (TU)",
            programs = "+2 Science/Mgmt, BBS, B.Ed, BCA",
            feeRange = "NPR 25,000 - 65,000 / year",
            rating = 4.6f,
            hostelAvailable = true,
            scholarshipAvailable = true,
            phone = "+977-46-501234",
            website = "www.janajyotilalbandi.edu.np",
            description = "Northern Sarlahi's top educational destination along the East-West Highway with modern computer labs and dedicated student faculty."
        ),
        CollegeEntity(
            id = "col_05",
            name = "Kathmandu University (KU)",
            location = "Dhulikhel / Hattiban",
            affiliation = "Kathmandu University",
            programs = "B.Sc. Computer Science, AI, Biotechnology, Pharmacy",
            feeRange = "NPR 8 Lakhs - 11 Lakhs",
            rating = 4.9f,
            hostelAvailable = true,
            scholarshipAvailable = true,
            phone = "+977-11-415100",
            website = "www.ku.edu.np",
            description = "Autonomous, world-class research university with extensive need-based financial aid for underprivileged provincial talents."
        )
    )

    val seedRooms = listOf(
        RoomEntity(
            id = "room_01",
            ownerId = "owner_01",
            ownerName = "Ram Chandra Sharma (Alumnus Host)",
            ownerPhone = "+977-9841239870",
            title = "Sunny Student Room near TU Gate",
            location = "Naya Bazaar, Kirtipur, Kathmandu",
            rentPerMonth = 5500,
            roomType = RoomType.SINGLE_ROOM,
            facilities = "High-speed WiFi, 24/7 Water, Rooftop Terrace, Bike Parking, Inverter Backup",
            distanceToCollege = "7 mins walk to TU Central Library",
            isVerified = true,
            description = "Quiet and peaceful study atmosphere. 3 other students from Sarlahi and Dhanusha stay in the same building."
        ),
        RoomEntity(
            id = "room_02",
            ownerId = "owner_02",
            ownerName = "Sunita Karki (Hostel Warden)",
            ownerPhone = "+977-9851098765",
            title = "Girls Deluxe Hostel - Putalisadak Hub",
            location = "Putalisadak, Kathmandu (Near Shankar Dev & Bagbazar)",
            rentPerMonth = 9500,
            roomType = RoomType.GIRLS_HOSTEL,
            facilities = "3 Meals + Snacks, CCTV Security, Study Hall, Geyser Hot Water, Laundry Service",
            distanceToCollege = "2 mins walk to Shankar Dev Campus",
            isVerified = true,
            description = "Strict security and homely hygienic food tailored for student budget. Discount available for Sarlahi students upon SGC card presentation."
        ),
        RoomEntity(
            id = "room_03",
            ownerId = "owner_03",
            ownerName = "Bikash Mahato",
            ownerPhone = "+977-9812345678",
            title = "Shared 2-Bed Room for Madhesh Students",
            location = "Tinkune / Minbhawan, Kathmandu",
            rentPerMonth = 4200,
            roomType = RoomType.DOUBLE_ROOM,
            facilities = "WiFi, Shared Kitchen, 24hr Borewell Water, Close to Bus Stop",
            distanceToCollege = "15 mins bus ride to New Baneshwor colleges",
            isVerified = true,
            description = "Looking for a friendly roommate from Sarlahi/Janakpur. Rent is NPR 4,200 each per month."
        ),
        RoomEntity(
            id = "room_04",
            ownerId = "owner_04",
            ownerName = "Hari Narayan Yadav",
            ownerPhone = "+977-9803322114",
            title = "Full 1BHK Flat near Lalbandi Market",
            location = "Lalbandi-02, Sarlahi",
            rentPerMonth = 7000,
            roomType = RoomType.FLAT,
            facilities = "Attached Bathroom, Separate Kitchen, Balcony, Ground Water, Safe Neighborhood",
            distanceToCollege = "5 mins walk to Janajyoti Campus",
            isVerified = true,
            description = "Ideal for college lecturers, remote tech workers, or 2-3 college friends studying in Lalbandi."
        )
    )

    val seedScholarships = listOf(
        ScholarshipEntity(
            id = "sch_01",
            title = "Sarlahi Municipal Youth Meritorious Scholarship",
            provider = "Sarlahi District Coordination Committee & Municipalities",
            amountOrCoverage = "NPR 60,000 / year + Tuition waiver",
            deadline = "Dec 15, 2026",
            eligibility = "Permanent resident of Sarlahi (Citizenship verification), minimum GPA 3.0 in SEE/+2, enrolled in STEM or Medicine.",
            category = "Government & Local",
            applyLink = "https://sarlahi.gov.np/scholarship",
            isVerified = true
        ),
        ScholarshipEntity(
            id = "sch_02",
            title = "Golden Jubilee Scholarship Scheme (Nepal)",
            provider = "Embassy of India, Kathmandu",
            amountOrCoverage = "NPR 4,000 - 6,000 / month throughout degree",
            deadline = "Dec 31, 2026",
            eligibility = "Nepalese students studying MBBS, BDS, BE, B.Sc, BA, BBA, BBS in recognized institutions with high SEE/+2 marks.",
            category = "International / Bilateral",
            applyLink = "https://www.indembkathmandu.gov.np",
            isVerified = true
        ),
        ScholarshipEntity(
            id = "sch_03",
            title = "Prime Minister Higher Education Reform Grant",
            provider = "University Grants Commission (UGC Nepal)",
            amountOrCoverage = "Up to NPR 100,000 grant per student",
            deadline = "Jan 20, 2027",
            eligibility = "Underprivileged, Dalit, Janajati and Madhesi students pursuing Bachelor / Masters research in community colleges.",
            category = "National Merit & Need",
            applyLink = "https://www.ugcnepal.edu.np",
            isVerified = true
        ),
        ScholarshipEntity(
            id = "sch_04",
            title = "Australia Awards Scholarships for Nepalese Leaders",
            provider = "Department of Foreign Affairs & Trade (DFAT)",
            amountOrCoverage = "Full Tuition, Airfare, Living Allowance, Health Cover in Australia",
            deadline = "April 30, 2027",
            eligibility = "Nepalese citizens with 2+ years professional experience committed to returning and contributing to Nepal.",
            category = "Study Abroad",
            applyLink = "https://www.australiaawardsnepal.org",
            isVerified = true
        )
    )

    val seedMentors = listOf(
        MentorEntity(
            id = "men_01",
            name = "Er. Sudip Chaudhary",
            sarlahiOrigin = "Malangwa-04, Sarlahi",
            currentDesignation = "Senior Cloud & Android Architect",
            companyOrField = "Global FinTech (Ex-Leapfrog)",
            expertise = "Android, Kotlin, Cloud Systems, Tech Career Roadmap",
            yearsExperience = 8,
            isVerified = true,
            bio = "Born and raised in Malangwa. Moved to Kathmandu for engineering and now mentors hundreds of provincial students into international software careers.",
            sessionCount = 34
        ),
        MentorEntity(
            id = "men_02",
            name = "Dr. Anita Shrestha",
            sarlahiOrigin = "Lalbandi-01, Sarlahi",
            currentDesignation = "Resident Physician & Health Advocate",
            companyOrField = "Tribhuvan University Teaching Hospital (TUTH)",
            expertise = "MBBS Prep, CEE Guidance, Medical Career in Nepal",
            yearsExperience = 6,
            isVerified = true,
            bio = "Passionate about guiding prospective medical students from Sarlahi through the Common Entrance Examination (CEE) and medical school journey.",
            sessionCount = 28
        ),
        MentorEntity(
            id = "men_03",
            name = "Pradeep Yadav, Section Officer",
            sarlahiOrigin = "Haripur-03, Sarlahi",
            currentDesignation = "Civil Servant (Lok Sewa Passed)",
            companyOrField = "Ministry of Federal Affairs & General Admin",
            expertise = "Lok Sewa Preparation, Public Administration, GK & Writing",
            yearsExperience = 5,
            isVerified = true,
            bio = "Cleared Nepal Lok Sewa in first attempt. Helping youth from rural Sarlahi prepare structured notes and conquer civil service examinations.",
            sessionCount = 42
        ),
        MentorEntity(
            id = "men_04",
            name = "Sunita Sah, CA",
            sarlahiOrigin = "Barahathawa-05, Sarlahi",
            currentDesignation = "Senior Audit & Finance Consultant",
            companyOrField = "KPMG Associated Firm Nepal",
            expertise = "Chartered Accountancy, Banking Career, Financial Literacy",
            yearsExperience = 7,
            isVerified = true,
            bio = "First female CA from her municipality. Available for mentorship on ICAN/ICAI path and corporate finance.",
            sessionCount = 19
        )
    )

    val seedSarlahiResources = listOf(
        SarlahiResourceEntity(
            id = "res_01",
            title = "Kathmandu to Sarlahi Direct Bus Counter (Gongabu)",
            category = "Transport & Travel",
            location = "Gongabu New Bus Park, Counter 24",
            contact = "+977-9844012345 / 01-4356789",
            details = "Daily AC Deluxe & Night coaches departing at 6:30 PM & 7:30 PM directly to Lalbandi, Haripur, Barahathawa, and Malangwa."
        ),
        SarlahiResourceEntity(
            id = "res_02",
            title = "District Administration Office (DAO) Sarlahi",
            category = "Local Government & Passport",
            location = "Malangwa (District HQ)",
            contact = "+977-46-520199 / daosarlahi@moha.gov.np",
            details = "Citizenship issuing, national identity card (NID), e-passport biometric appointment processing."
        ),
        SarlahiResourceEntity(
            id = "res_03",
            title = "Sarlahi Student Welfare Association (SSWA)",
            category = "Student Club & Network",
            location = "Kirtipur / Bagbazar Hub, Kathmandu",
            contact = "+977-9801239999",
            details = "Peer support network assisting incoming Sarlahi students with college admissions, room hunting, and emergency medical funds."
        ),
        SarlahiResourceEntity(
            id = "res_04",
            title = "District Hospital Malangwa & Blood Bank",
            category = "Emergency Services",
            location = "Malangwa-02, Sarlahi",
            contact = "+977-46-520102 / 102 (Ambulance)",
            details = "24/7 Emergency trauma, maternity ward, Red Cross blood bank, snake bite anti-venom center."
        ),
        SarlahiResourceEntity(
            id = "res_05",
            title = "Nunthar Shiva Temple & Bagmati River Confluence",
            category = "Culture & Local Places",
            location = "Bagmati / Sarlahi border",
            contact = "Open daily 5:00 AM - 8:00 PM",
            details = "Scenic spiritual landmark where Bagmati river emerges from the Mahabharat range into the Terai plains. Major youth gathering spot."
        )
    )

    val seedPosts = listOf(
        CommunityPostEntity(
            id = "post_01",
            authorId = "student_sarlahi_01",
            authorName = "Aayush Kushwaha",
            authorRole = UserRole.STUDENT,
            title = "Tips for fresh Sarlahi students moving to Kathmandu this semester!",
            content = "Namaste sathi haru! When renting rooms around Kirtipur or Putalisadak, always verify water availability and inverter backup before paying advance. Also, join the Sarlahi Growth Connect group for shared TU book notes!",
            tag = "Student Advice",
            likesCount = 18,
            commentsCount = 4,
            createdAt = "2 hours ago"
        ),
        CommunityPostEntity(
            id = "post_02",
            authorId = "men_03",
            authorName = "Pradeep Yadav",
            authorRole = UserRole.MENTOR,
            title = "Free Lok Sewa (Section Officer/Nayab Subba) Orientation Session",
            content = "This Saturday at 4 PM, I will host a live mentorship webinar on Zoom for Sarlahi youth on syllabus analysis, current affairs note-making, and answer presentation techniques.",
            tag = "Lok Sewa Guidance",
            likesCount = 42,
            commentsCount = 11,
            createdAt = "Yesterday"
        ),
        CommunityPostEntity(
            id = "post_03",
            authorId = "student_02",
            authorName = "Pooja Thakur",
            authorRole = UserRole.STUDENT,
            title = "Looking for study partner for TU BCA Entrance / CEE",
            content = "Studying at Bagbazar library daily from 10 AM to 3 PM. Anyone from Lalbandi or Malangwa preparing together?",
            tag = "Study Group",
            likesCount = 9,
            commentsCount = 3,
            createdAt = "Yesterday"
        )
    )

    val seedMarketplace = listOf(
        MarketplaceItemEntity(
            id = "item_01",
            sellerId = "student_sarlahi_01",
            sellerName = "Aayush Kushwaha",
            sellerPhone = "+977-9807654321",
            title = "TU Computer Science & Engineering Complete Notes + Books",
            price = 850,
            category = "Books",
            condition = "Very Good (Highlighted)",
            location = "Kirtipur / Putalisadak",
            description = "Complete handwritten notes, previous 10 years question banks with solutions.",
            createdAt = "Today"
        ),
        MarketplaceItemEntity(
            id = "item_02",
            sellerId = "student_03",
            sellerName = "Rohan Sah",
            sellerPhone = "+977-9812998877",
            title = "Lenovo ThinkPad Core i5 (8GB RAM / 256GB SSD)",
            price = 26000,
            category = "Laptops & Tech",
            condition = "Good working condition",
            location = "Bagbazar, Kathmandu",
            description = "Great for coding, MS Office, and online classes. 3 hours battery backup. Charger included.",
            createdAt = "2 days ago"
        ),
        MarketplaceItemEntity(
            id = "item_03",
            sellerId = "student_04",
            sellerName = "Suresh Chaudhary",
            sellerPhone = "+977-9844112233",
            title = "Hercules Commuter Bicycle with Basket",
            price = 4500,
            category = "Bicycles",
            condition = "Fair",
            location = "Lalbandi Market, Sarlahi",
            description = "Perfect for college commute from Haripur to Lalbandi. New tires installed last month.",
            createdAt = "3 days ago"
        )
    )

    val seedNotifications = listOf(
        NotificationEntity(
            id = "notif_01",
            userId = "student_sarlahi_01",
            title = "New Job Match: Junior Mobile Developer",
            message = "Everest Tech posted a vacancy matching your Kotlin & Android skills in Kathmandu.",
            category = "Job",
            timestamp = "10 mins ago",
            isRead = false
        ),
        NotificationEntity(
            id = "notif_02",
            userId = "student_sarlahi_01",
            title = "Scholarship Deadline Alert",
            message = "Sarlahi Municipal Youth Scholarship closes in 20 days. Complete your application now.",
            category = "Scholarship",
            timestamp = "2 hours ago",
            isRead = false
        ),
        NotificationEntity(
            id = "notif_03",
            userId = "student_sarlahi_01",
            title = "Mentorship Request Confirmed",
            message = "Er. Sudip Chaudhary accepted your career roadmap guidance request.",
            category = "Mentorship",
            timestamp = "1 day ago",
            isRead = true
        )
    )

    val seedApplications = listOf(
        JobApplicationEntity(
            id = "app_01",
            jobId = "job_01",
            jobTitle = "Junior Android & Mobile Developer",
            companyName = "Everest Tech Innovations",
            applicantId = "student_sarlahi_01",
            applicantName = "Aayush Kushwaha",
            applicantEmail = "aayush.kushwaha@sarlahi.edu.np",
            applicantPhone = "+977-9807654321",
            coverNote = "Passionate student from Sarlahi with solid foundation in Android Jetpack Compose and Kotlin coroutines. Excited to build impactful products.",
            status = ApplicationStatus.SHORTLISTED,
            appliedDate = "Yesterday"
        )
    )
}
