
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (nickname, email, phone, password, created_at) VALUES
('JohnDoe1', 'john1@example.com', '1234567890', 'password1', NOW()),
('JohnDoe2', 'john2@example.com', '1234567891', 'password2', NOW()),
('JohnDoe3', 'john3@example.com', '1234567892', 'password3', NOW()),
('JohnDoe4', 'john4@example.com', '1234567893', 'password4', NOW()),
('JohnDoe5', 'john5@example.com', '1234567894', 'password5', NOW()),
('JohnDoe6', 'john6@example.com', '1234567895', 'password6', NOW()),
('JohnDoe7', 'john7@example.com', '1234567896', 'password7', NOW()),
('JohnDoe8', 'john8@example.com', '1234567897', 'password8', NOW()),
('JohnDoe9', 'john9@example.com', '1234567898', 'password9', NOW()),
('JohnDoe10', 'john10@example.com', '1234567899', 'password10', NOW()),
('JohnDoe11', 'john11@example.com', '1234567800', 'password11', NOW()),
('JohnDoe12', 'john12@example.com', '1234567801', 'password12', NOW()),
('JohnDoe13', 'john13@example.com', '1234567802', 'password13', NOW()),
('JohnDoe14', 'john14@example.com', '1234567803', 'password14', NOW()),
('JohnDoe15', 'john15@example.com', '1234567804', 'password15', NOW());


CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    role_name VARCHAR(255) NOT NULL
);
