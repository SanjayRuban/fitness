import React from "react";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const ProgressChart = ({ data }) => {
  const chartData = {
    labels: data.map(entry => entry.date || "Unknown Date"),
    datasets: [
      {
        label: "Weight (kg)",
        data: data.map(entry => entry.weight),
        borderColor: "blue",
        backgroundColor: "rgba(0, 0, 255, 0.2)",
        fill: true,
      },
      {
        label: "Calories Burned",
        data: data.map(entry => entry.caloriesBurned),
        borderColor: "green",
        backgroundColor: "rgba(0, 255, 0, 0.2)",
        fill: true,
      }
    ],
  };

  return <Line data={chartData} />;
};

export default ProgressChart;
