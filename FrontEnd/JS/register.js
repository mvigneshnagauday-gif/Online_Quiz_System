const API_BASE = "https://online-quiz-system-1-jsno.onrender.com/api";

document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMsg = document.getElementById("errorMsg");
    const registerBtn = document.getElementById("registerBtn");

    errorMsg.classList.remove("show");
    errorMsg.textContent = "";

    if (password !== confirmPassword) {
        errorMsg.textContent = "Passwords do not match";
        errorMsg.classList.add("show");
        return;
    }

    if (password.length < 6) {
        errorMsg.textContent = "Password must be at least 6 characters";
        errorMsg.classList.add("show");
        return;
    }

    registerBtn.disabled = true;
    registerBtn.textContent = "Creating account...";

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, email, password })
        });

        if (!response.ok) {
            const errData = await response.json().catch(() => null);
            throw new Error(errData?.message || "Registration failed. Try a different email.");
        }

        // Registration successful -> send them to login
        window.location.href = "login.html?registered=true";

    } catch (err) {
        errorMsg.textContent = err.message;
        errorMsg.classList.add("show");
        registerBtn.disabled = false;
        registerBtn.textContent = "Register";
    }
});