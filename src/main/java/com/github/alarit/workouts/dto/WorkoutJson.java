package com.github.alarit.workouts.dto;

import com.github.alarit.workouts.model.Workout;

import java.util.List;

public class WorkoutJson {
    
    private List<Workout> data;

    public static WorkoutJson of(List<Workout> data){
        return new WorkoutJson(data);
    }

    private WorkoutJson(List<Workout> data) {
        this.data = data;
    }
}
