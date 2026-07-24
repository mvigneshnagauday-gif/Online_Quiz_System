const API_BASE = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }
    loadProfile(token);
});

async function loadProfile(token) {
    const card = document.getElementById("profileCard");

    try {
        const response = await fetch(`${API_BASE}/user/me`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to load profile");
        }

        const user = await response.json();
        renderProfile(user);

    } catch (err) {
        console.error(err);
        card.innerHTML = "<p>Could not load profile. Please try logging in again.</p>";
    }
}

function renderProfile(user) {
    const card = document.getElementById("profileCard");
    const initials = user.name
        .split(" ")
        .map(part => part[0])
        .join("")
        .toUpperCase()
        .slice(0, 2);

    card.innerHTML = `
        <div class="profile-avatar">${initials}</div>
        <h2>${user.name}</h2>
        <p class="profile-email">${user.email}</p>
        <span class="profile-role-badge">${user.role}</span>

        <div class="profile-detail-row">
            <span class="label">Full Name</span>
            <span class="value">${user.name}</span>
        </div>
        <div class="profile-detail-row">
            <span class="label">Email</span>
            <span class="value">${user.email}</span>
        </div>
        <div class="profile-detail-row">
            <span class="label">Account Type</span>
            <span class="value">${user.role === "ADMIN" ? "Administrator" : "Student"}</span>
        </div>
    `;
}

function navigate(page) {
    window.location.href = `${page}.html`;
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}