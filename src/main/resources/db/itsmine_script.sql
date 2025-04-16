USE itsmine;

CREATE TABLE users(
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	nickname VARCHAR(8),
	email VARCHAR(30),
	phone VARCHAR(13),
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	provider VARCHAR(10)
);


CREATE TABLE public_found_items(			#공공기관 습득물
	pfi_id INT AUTO_INCREMENT PRIMARY KEY,
	atcId VARCHAR(17) UNIQUE,
	category VARCHAR(100),
	color VARCHAR(100),
	name VARCHAR(100),
	description TEXT,
	location VARCHAR(100),
	img_path VARCHAR(300),
	found_at DATE
);

CREATE TABLE items(  					#사용자 등록 분실물·습득물  --itme_type으로 구분
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    User_id INT,
    name VARCHAR(100),
    color VARCHAR(100),
    category VARCHAR(100),
    description TEXT,
    occurrence_at DATE,
    status VARCHAR(255),
    reg_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    item_type VARCHAR(255)
);


CREATE TABLE images(
	image_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    img_path VARCHAR(2048),
    FOREIGN KEY (item_id) REFERENCES items(item_id)
);


CREATE TABLE chatrooms (
    chatroom_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    registrant_id INT, -- 습득물/분실물 등록자 ID
    contact_id INT, -- 대화 상대방 ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (registrant_id) REFERENCES users(user_id),
    FOREIGN KEY (contact_id) REFERENCES Users(user_id)
);

CREATE TABLE messages(
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    chatroom_id INT,
    sender_id INT,
    message_content TEXT,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chatroom_id) REFERENCES chatrooms(chatroom_id),
    FOREIGN KEY (sender_id) REFERENCES users(user_id)
);

CREATE TABLE keywords (
    keyword_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    keyword VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    item_id INT,
    keyword_id INT,
    notification_type VARCHAR(255),
    notification_message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (keyword_id) REFERENCES keywords(keyword_id)
);
