const API_BASE = "http://localhost:8080/api";

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const errorMsg = document.getElementById("errorMsg");
    const loginBtn = document.getElementById("loginBtn");

    errorMsg.classList.remove("show");
    errorMsg.textContent = "";

    loginBtn.disabled = true;
    loginBtn.textContent = "Logging in...";

    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            const errData = await response.json().catch(() => null);
            throw new Error(errData?.message || "Invalid email or password");
        }

        const data = await response.json();

        // Expecting backend to return { token: "...", user: { id, name, role } }
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.user.role);

        window.location.href = "dashboard.html";

    } catch (err) {
        errorMsg.textContent = err.message;
        errorMsg.classList.add("show");
        loginBtn.disabled = false;
        loginBtn.textContent = "Login";
    }
});