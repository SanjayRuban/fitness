require("dotenv").config();
const express = require("express");
const cors = require("cors");

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

let progressData = []; // Temporary in-memory database

// Add Progress Entry
app.post("/api/progress", (req, res) => {
  const newEntry = { id: progressData.length + 1, ...req.body };
  progressData.push(newEntry);
  res.json(newEntry);
});

// Get Progress Data
app.get("/api/progress/:userId", (req, res) => {
  const userProgress = progressData.filter((entry) => entry.userId === req.params.userId);
  res.json(userProgress);
});

app.delete("/api/progress/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const index = progressData.findIndex((entry) => entry.id === id);

  if (index !== -1) {
    progressData.splice(index, 1); // Remove entry from array
    res.json({ message: "Progress entry deleted successfully!" });
  } else {
    res.status(404).json({ message: "Entry not found" });
  }
});

app.put("/api/progress/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const index = progressData.findIndex((entry) => entry.id === id);

  if (index !== -1) {
    // Replace the entire entry
    progressData[index] = { id, ...req.body };
    res.json({ message: "Progress entry updated successfully!", data: progressData[index] });
  } else {
    res.status(404).json({ message: "Entry not found" });
  }
});

app.patch("/api/progress/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const index = progressData.findIndex((entry) => entry.id === id);

  if (index !== -1) {
    // Update only provided fields
    progressData[index] = { ...progressData[index], ...req.body };
    res.json({ message: "Progress entry partially updated!", data: progressData[index] });
  } else {
    res.status(404).json({ message: "Entry not found" });
  }
});


// Start Server
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
