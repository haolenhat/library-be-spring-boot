CREATE DATABASE LibrabryManament;
USE LibrabryManament;

-- Tạo bảng users (Chỉ dành cho Admin và Nhân viên thư viện)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'LIBRARIAN') NOT NULL
) ENGINE=InnoDB;

-- Bảng borrowers (Sinh viên/Giảng viên mượn sách, không có tài khoản)
CREATE TABLE borrowers (
    borrower_code VARCHAR(20) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Bảng book_categories
CREATE TABLE book_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Bảng publishers
CREATE TABLE publishers (
    publisher_id INT PRIMARY KEY AUTO_INCREMENT,
    publisher_name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Bảng authors
CREATE TABLE authors (
    author_id INT PRIMARY KEY AUTO_INCREMENT,
    author_name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Bảng books
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    category_id INT,
    publisher_id INT,
    link_img VARCHAR(255),
    available_copies INT NOT NULL CHECK (available_copies >= 0),
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES book_categories(category_id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (publisher_id) REFERENCES publishers(publisher_id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng loan_records
CREATE TABLE loan_records (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    borrower_code VARCHAR(20),
    librarian_id INT NULL,
    borrow_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date DATETIME DEFAULT NULL,
    status ENUM('Borrowed', 'Returned', 'Lost','OverDue') DEFAULT 'Borrowed',
    lost_or_damaged_fee DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (borrower_code) REFERENCES borrowers(borrower_code) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (librarian_id) REFERENCES users(user_id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng overdue_records
CREATE TABLE overdue_records (
    overdue_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT UNIQUE,
    due_date DATE NOT NULL,
    return_date DATETIME,
    overdue_days INT GENERATED ALWAYS AS (DATEDIFF(return_date, due_date)) STORED,
    fine_amount DECIMAL(10,2) GENERATED ALWAYS AS (
        CASE 
            WHEN DATEDIFF(return_date, due_date) > 0 THEN DATEDIFF(return_date, due_date) * 5000
            ELSE 0 
        END
    ) STORED,
    FOREIGN KEY (loan_id) REFERENCES loan_records(loan_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng loan_card (phiếu mượn)
CREATE TABLE loan_card (
    loancard_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT,
    book_id INT,
    quantity INT CHECK (quantity > 0),
    FOREIGN KEY (loan_id) REFERENCES loan_records(loan_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng authors_list (liên kết sách - tác giả)
CREATE TABLE authors_list (
    listauthor_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    author_id INT,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;
