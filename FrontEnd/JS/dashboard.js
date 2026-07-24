
const API_BASE = "http://localhost:8080/api";


document.addEventListener("DOMContentLoaded", () => {
    loadUserInfo();
});


async function loadUserInfo() {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/user/me`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error("Failed to fetch user info");
        }

        const user = await response.json();
        document.getElementById("userGreeting").textContent = user.name;

    } catch (err) {
        console.error(err);
        // token invalid/expired -> send back to login
        localStorage.removeItem("token");
        window.location.href = "login.html";
    }
}


function openCourse(courseName) {
    // levels.html will call the backend to check which levels
    // are unlocked for this user, based on UserProgress
    window.location.href = `levels.html?course=${encodeURIComponent(courseName)}`;
}


function navigate(page) {
    switch (page) {
        case "dashboard":
            window.location.href = "dashboard.html";
            break;
        case "progress":
            window.location.href = "progress.html";
            break;
        case "results":
            window.location.href = "results.html";
            break;
        case "leaderboard":
            window.location.href = "leaderboard.html";
            break;
        case "profile":
            window.location.href = "profile.html";
            break;
        default:
            console.warn("Unknown page:", page);
    }
}
function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}