const API_BASE = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }
    loadLeaderboard(token);
});

async function loadLeaderboard(token) {
    const list = document.getElementById("leaderboardList");

    try {
        // NOTE: this endpoint (GET /api/leaderboard) does not exist on the
        // backend yet. It needs to return users ranked by total levels
        // passed (or average score), e.g.:
        // [ { userId, name, levelsPassed, averageScore }, ... ]
        const response = await fetch(`${API_BASE}/leaderboard`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to load leaderboard");
        }

        const entries = await response.json();
        renderLeaderboard(entries);

    } catch (err) {
        console.error(err);
        list.innerHTML = "<p>Could not load leaderboard. Backend endpoint /api/leaderboard may not be ready yet.</p>";
    }
}

function renderLeaderboard(entries) {
    const list = document.getElementById("leaderboardList");
    list.innerHTML = "";

    const currentUserId = getCurrentUserIdFromToken();

    entries.forEach((entry, idx) => {
        const rank = idx + 1;
        let badgeClass = "";
        if (rank === 1) badgeClass = "gold";
        else if (rank === 2) badgeClass = "silver";
        else if (rank === 3) badgeClass = "bronze";

        const row = document.createElement("div");
        row.className = `leaderboard-row ${entry.userId === currentUserId ? "me" : ""}`;
        row.innerHTML = `
            <div class="rank-badge ${badgeClass}">${rank}</div>
            <div class="leaderboard-name">${entry.name}</div>
            <div class="leaderboard-stats">${entry.levelsPassed} levels passed &bull; ${entry.averageScore}% avg</div>
        `;
        list.appendChild(row);
    });
}

// Best-effort: not critical if this fails, just skips highlighting "me"
function getCurrentUserIdFromToken() {
    try {
        const token = localStorage.getItem("token");
        const payload = JSON.parse(atob(token.split(".")[1]));
        return payload.userId || null;
    } catch {
        return null;
    }
}

function navigate(page) {
    window.location.href = `${page}.html`;
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}