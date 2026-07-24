const API_BASE = "http://localhost:8080/api";

const LEVEL_CONFIG = [
    { level: 1, name: "Level 1", difficulty: "Easy", questions: 10, timeMinutes: 10, iconClass: "easy" },
    { level: 2, name: "Level 2", difficulty: "Intermediate", questions: 10, timeMinutes: 20, iconClass: "intermediate" },
    { level: 3, name: "Level 3", difficulty: "Hard", questions: 10, timeMinutes: 30, iconClass: "hard" }
];

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const course = params.get("course");

    if (!course) {
        window.location.href = "dashboard.html";
        return;
    }

    document.getElementById("courseTitle").textContent = course;
    loadLevels(course, token);
});

async function loadLevels(course, token) {
    const wrapper = document.getElementById("levelsWrapper");
    wrapper.innerHTML = "<p>Loading levels...</p>";

    try {
        // Expecting backend to return progress for this course:
        // { highestLevelUnlocked: 1, results: [ { level: 1, score: 80, passed: true }, ... ] }
        const response = await fetch(`${API_BASE}/progress?course=${encodeURIComponent(course)}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to load progress");
        }

        const progress = await response.json();
        renderLevels(course, progress);

    } catch (err) {
        console.error(err);
        wrapper.innerHTML = "<p>Could not load levels. Please try again.</p>";
    }
}

function renderLevels(course, progress) {
    const wrapper = document.getElementById("levelsWrapper");
    wrapper.innerHTML = "";

    const highestUnlocked = progress.highestLevelUnlocked || 1;
    const resultsByLevel = {};
    (progress.results || []).forEach(r => {
        resultsByLevel[r.level] = r;
    });

    LEVEL_CONFIG.forEach(cfg => {
        const isUnlocked = cfg.level <= highestUnlocked;
        const result = resultsByLevel[cfg.level];

        const card = document.createElement("div");
        card.className = `level-card ${isUnlocked ? "unlocked" : "locked"}`;

        let statusHtml;
        if (!isUnlocked) {
            statusHtml = `<i class="fa-solid fa-lock"></i><span class="status-locked">Locked</span>`;
        } else if (result && result.passed) {
            statusHtml = `<i class="fa-solid fa-circle-check status-passed"></i><span class="status-passed">Passed (${result.score}%)</span>`;
        } else if (result && !result.passed) {
            statusHtml = `<i class="fa-solid fa-rotate-right"></i><span class="status-failed">Failed - Retake</span>`;
        } else {
            statusHtml = `<span class="status-start">Start →</span>`;
        }

        card.innerHTML = `
            <div class="level-info">
                <div class="level-icon ${isUnlocked ? cfg.iconClass : "locked-icon"}">
                    ${isUnlocked ? "<i class='fa-solid fa-lock-open'></i>" : "<i class='fa-solid fa-lock'></i>"}
                </div>
                <div class="level-details">
                    <h3>${cfg.name} - ${cfg.difficulty}</h3>
                    <p>${cfg.questions} Questions &bull; ${cfg.timeMinutes} min timer &bull; Pass mark: 50%</p>
                </div>
            </div>
            <div class="level-status">
                ${statusHtml}
            </div>
        `;

        if (isUnlocked) {
            card.addEventListener("click", () => {
                window.location.href = `quiz.html?course=${encodeURIComponent(course)}&level=${cfg.level}`;
            });
        }

        wrapper.appendChild(card);
    });
}