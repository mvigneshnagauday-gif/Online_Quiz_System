const API_BASE = "https://online-quiz-system-1-jsno.onrender.com/api";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }
    loadResults(token);
});

async function loadResults(token) {
    const body = document.getElementById("resultsBody");

    try {
        // NOTE: this endpoint (GET /api/results) does not exist on the
        // backend yet. It needs to return every Result row for the logged
        // in user, most recent first:
        // [ { course, level, score, passed, attemptedAt }, ... ]
        const response = await fetch(`${API_BASE}/results`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to load results");
        }

        const results = await response.json();

        if (results.length === 0) {
            body.innerHTML = `<tr><td colspan="5">No attempts yet. Go take a quiz!</td></tr>`;
            return;
        }

        body.innerHTML = results.map(r => `
            <tr>
                <td>${r.course}</td>
                <td>Level ${r.level}</td>
                <td>${r.score}%</td>
                <td><span class="status-pill ${r.passed ? "passed" : "failed"}">${r.passed ? "Passed" : "Failed"}</span></td>
                <td>${new Date(r.attemptedAt).toLocaleDateString()}</td>
            </tr>
        `).join("");

    } catch (err) {
        console.error(err);
        body.innerHTML = `<tr><td colspan="5">Could not load results. Backend endpoint /api/results may not be ready yet.</td></tr>`;
    }
}

function navigate(page) {
    window.location.href = `${page}.html`;
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}