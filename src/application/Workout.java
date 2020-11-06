package application;

import java.sql.Date;

public class Workout {
	long sesh_id, user_id;
	Date sesh_date;
	String exercise;
	int sets, reps, weight;
	
	public Workout(long sesh_id, long user_id, Date sesh_date, String exercise, int sets, int reps, int weight) {
		this.sesh_id = sesh_id;
		this.user_id = user_id;
		this.sesh_date = sesh_date;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
		this.weight = weight;
	}
	
	public Workout(Date sesh_date, String exercise, int sets, int reps, int weight) {
		this.sesh_date = sesh_date;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
		this.weight = weight;
	}

	public Workout() {
	}

	public long getSesh_id() {
		return sesh_id;
	}

	public void setSesh_id(long sesh_id) {
		this.sesh_id = sesh_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Date getSesh_date() {
		return sesh_date;
	}

	public void setSesh_date(Date sesh_date) {
		this.sesh_date = sesh_date;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
