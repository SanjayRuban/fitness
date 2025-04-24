import React, { useState, useEffect } from "react";
import axios from "axios";
import ProgressChart from "./ProgressChart";

const FitnessTracker = () => {
  const [progress, setProgress] = useState([]);
  const [formData, setFormData] = useState({
    userId: "123",
    weight: "",
    caloriesBurned: "",
    workoutType: "",
  });

  useEffect(() => {
    fetchProgress();
  }, []);

  const fetchProgress = async () => {
    try {
      const res = await axios.get(`http://localhost:5000/api/progress/${formData.userId}`);
      setProgress(res.data);
    } catch (error) {
      console.error("Error fetching progress", error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:5000/api/progress", { ...formData, date: new Date().toISOString().split("T")[0] });
      fetchProgress();
    } catch (error) {
      console.error("Error adding progress", error);
    }
  };

  return (
    <div>
      <h2>Fitness Progress Tracker</h2>
      <form onSubmit={handleSubmit}>
        <input type="number" name="weight" placeholder="Weight (kg)" onChange={handleChange} required />
        <input type="number" name="caloriesBurned" placeholder="Calories Burned" onChange={handleChange} required />
        <input type="text" name="workoutType" placeholder="Workout Type" onChange={handleChange} required />
        <button type="submit">Log Progress</button>
      </form>

      <h3>Progress History</h3>
      <ProgressChart data={progress} />

      <ul>
        {progress.map((entry, index) => (
          <li key={index}>
            {entry.date}: {entry.workoutType} - {entry.caloriesBurned} calories burned, Weight: {entry.weight} kg
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FitnessTracker;
