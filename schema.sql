CREATE DATABASE IF NOT EXISTS smart_campus
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_campus;

CREATE TABLE IF NOT EXISTS users (
  user_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(80) NOT NULL,
  password VARCHAR(255) NOT NULL COMMENT 'BCrypt password hash',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id),
  UNIQUE KEY uq_users_username (username)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS students (
  student_id INT NOT NULL,
  student_name VARCHAR(120) NOT NULL,
  course VARCHAR(100) NOT NULL,
  year INT NOT NULL,
  email VARCHAR(180) NOT NULL,
  PRIMARY KEY (student_id),
  UNIQUE KEY uq_students_email (email),
  CONSTRAINT chk_students_year CHECK (year BETWEEN 1 AND 8)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS faculty (
  faculty_id INT NOT NULL,
  faculty_name VARCHAR(120) NOT NULL,
  department VARCHAR(100) NOT NULL,
  email VARCHAR(180) NOT NULL,
  PRIMARY KEY (faculty_id),
  UNIQUE KEY uq_faculty_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS attendance (
  attendance_id BIGINT NOT NULL AUTO_INCREMENT,
  student_id INT NOT NULL,
  subject_name VARCHAR(120) NOT NULL,
  attendance_date DATE NOT NULL,
  status ENUM('Present', 'Absent') NOT NULL,
  PRIMARY KEY (attendance_id),
  KEY ix_attendance_student_date (student_id, attendance_date),
  CONSTRAINT fk_attendance_student FOREIGN KEY (student_id)
    REFERENCES students(student_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS complaints (
  complaint_id BIGINT NOT NULL AUTO_INCREMENT,
  student_name VARCHAR(120) NOT NULL,
  complaint_text TEXT NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'Pending',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (complaint_id),
  KEY ix_complaints_status (status)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS events (
  event_id INT NOT NULL,
  event_name VARCHAR(150) NOT NULL,
  event_date DATE NOT NULL,
  location VARCHAR(180) NOT NULL,
  PRIMARY KEY (event_id),
  KEY ix_events_date (event_date)
) ENGINE=InnoDB;
