const API_BASE = "http://localhost:8080/api";
const COURSES = ["Java", "Python", "C++", "Database", "Spring Boot"];
const COURSE_ICONS = {
    "Java": "fa-brands fa-java",
    "Python": "fa-brands fa-python",
    "C++": "fa-solid fa-code",
    "Database": "fa-solid fa-database",
    "Spring Boot": "fa-solid fa-leaf"
};

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }
    loadAllProgress(token);
});

async function loadAllProgress(token) {
    const list = document.getElementById("progressList");
    list.innerHTML = "";

    try {
        // Fetch progress for every course in parallel
        const results = await Promise.all(
            COURSES.map(course =>
                fetch(`${API_BASE}/progress?course=${encodeURIComponent(course)}`, {
                    headers: { "Authorization": `Bearer ${token}` }
                }).then(res => res.ok ? res.json() : null)
            )
        );

        COURSES.forEach((course, idx) => {
            const progress = results[idx];
            renderCourseProgress(course, progress);
        });

    } catch (err) {
        console.error(err);
        list.innerHTML = "<p>Could not load progress. Please try again.</p>";
    }
}

function renderCourseProgress(course, progress) {
    const list = document.getElementById("progressList");

    const highestUnlocked = progress ? progress.highestLevelUnlocked : 1;
    const resultsByLevel = {};
    if (progress && progress.results) {
        progress.results.forEach(r => { resultsByLevel[r.level] = r; });
    }

    // Percentage of the 3 levels passed, for the bar fill
    const passedCount = [1, 2, 3].filter(lvl => resultsByLevel[lvl] && resultsByLevel[lvl].passed).length;
    const percent = Math.round((passedCount / 3) * 100);

    const levelChips = [1, 2, 3].map(lvl => {
        const result = resultsByLevel[lvl];
        if (result && result.passed) {
            return `<span class="done"><i class="fa-solid fa-circle-check"></i> Level ${lvl}</span>`;
        } else if (lvl <= highestUnlocked) {
            return `<span class="current"><i class="fa-solid fa-lock-open"></i> Level ${lvl}</span>`;
        } else {
            return `<span><i class="fa-solid fa-lock"></i> Level ${lvl}</span>`;
        }
    }).join("");

    const card = document.createElement("div");
    card.className = "progress-card";
    card.innerHTML = `
        <div class="progress-card-top">
            <div class="progress-course-name">
                <i class="${COURSE_ICONS[course]}"></i>
                ${course}
            </div>
            <span class="progress-level-badge">Level ${highestUnlocked} unlocked</span>
        </div>
        <div class="progress-bar-track">
            <div class="progress-bar-fill" style="width:${percent}%"></div>
        </div>
        <div class="progress-levels-row">${levelChips}</div>
    `;

    list.appendChild(card);
}

function navigate(page) {
    window.location.href = `${page}.html`;
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}