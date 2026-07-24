const API_BASE = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const course = params.get("course");
    const level = parseInt(params.get("level"), 10);

    const resultData = localStorage.getItem("lastResult");
    if (!resultData || !course || !level) {
        window.location.href = "dashboard.html";
        return;
    }

    const result = JSON.parse(resultData);
    // Expecting: { score, correctCount, totalQuestions, passed, nextLevelUnlocked }

    renderResult(course, level, result);
});

function renderResult(course, level, result) {
    const passed = result.passed;

    document.getElementById("resultSubtitle").textContent = `${course} - Level ${level}`;

    const icon = document.getElementById("resultIcon");
    const circle = document.getElementById("scoreCircle");
    const title = document.getElementById("resultTitle");

    if (passed) {
        icon.classList.add("passed");
        circle.classList.add("passed");
        title.textContent = "Level Passed!";
        icon.innerHTML = `<i class="fa-solid fa-circle-check"></i>`;
    } else {
        icon.classList.add("failed");
        circle.classList.add("failed");
        title.textContent = "Level Failed";
        icon.innerHTML = `<i class="fa-solid fa-circle-xmark"></i>`;
    }

    document.getElementById("scorePercent").textContent = `${result.score}%`;
    document.getElementById("correctCount").textContent = result.correctCount;
    document.getElementById("wrongCount").textContent = result.totalQuestions - result.correctCount;
    document.getElementById("totalCount").textContent = result.totalQuestions;

    document.getElementById("passNote").textContent = passed
        ? (level < 3 ? "Next level is now unlocked." : "You've completed all levels for this course!")
        : "You need 50% to pass. Retake this level to continue.";

    const primaryBtn = document.getElementById("primaryActionBtn");
    if (passed) {
        primaryBtn.textContent = level < 3 ? "Next Level" : "Back to Dashboard";
    } else {
        primaryBtn.textContent = "Retake Level";
    }
}

function handlePrimaryAction() {
    const params = new URLSearchParams(window.location.search);
    const course = params.get("course");
    const level = parseInt(params.get("level"), 10);
    const result = JSON.parse(localStorage.getItem("lastResult"));

    if (result.passed) {
        if (level < 3) {
            window.location.href = `quiz.html?course=${encodeURIComponent(course)}&level=${level + 1}`;
        } else {
            window.location.href = "dashboard.html";
        }
    } else {
        // Retake same level
        window.location.href = `quiz.html?course=${encodeURIComponent(course)}&level=${level}`;
    }
}

function reviewAnswers() {
    const params = new URLSearchParams(window.location.search);
    const course = params.get("course");
    const level = params.get("level");
    window.location.href = `review.html?course=${encodeURIComponent(course)}&level=${level}`;
}