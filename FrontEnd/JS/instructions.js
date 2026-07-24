const LEVEL_CONFIG = {
    1: { name: "Level 1 - Easy", minutes: 10 },
    2: { name: "Level 2 - Intermediate", minutes: 20 },
    3: { name: "Level 3 - Hard", minutes: 30 }
};

let course = "";
let level = 1;

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    course = params.get("course");
    level = parseInt(params.get("level"), 10);

    if (!course || !level || !LEVEL_CONFIG[level]) {
        window.location.href = "dashboard.html";
        return;
    }

    const cfg = LEVEL_CONFIG[level];

    document.getElementById("courseTitle").textContent = course;
    document.getElementById("levelBadge").textContent = cfg.name;
    document.getElementById("timeText").textContent = `${cfg.minutes} minutes`;
});

function startTest() {
    window.location.href = `quiz.html?course=${encodeURIComponent(course)}&level=${level}`;
}