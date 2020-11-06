#CREATE DATABASE workoutTracker;

USE workoutTracker;

CREATE TABLE registration (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

CREATE TABLE workout_sesh (
	sesh_id BIGINT PRIMARY KEY auto_increment,
    user_id BIGINT,
    sesh_date date NOT NULL,
    exercise VARCHAR(100) NOT NULL,
    num_of_sets INT NOT NULL,
    num_of_reps INT NOT NULL,
    weight INT DEFAULT 0,
	FOREIGN KEY (user_id) REFERENCES registration(id)
);



