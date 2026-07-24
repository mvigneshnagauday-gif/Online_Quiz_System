const API_BASE = "https://online-quiz-system-1-jsno.onrender.com/api";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const course = params.get("course");
    const level = params.get("level");

    if (!course || !level) {
        window.location.href = "dashboard.html";
        return;
    }

    document.getElementById("solutionsTitle").textContent = `${course} - Level ${level} Solutions`;

    loadSolutions(course, level, token);
});

async function loadSolutions(course, level, token) {
    const list = document.getElementById("solutionsList");

    try {
        // Expecting: [ { questionText, options: [...], correctOption: 1, explanation: "..." }, ... ]
        const response = await fetch(
            `${API_BASE}/quiz/solutions?course=${encodeURIComponent(course)}&level=${level}`,
            { headers: { "Authorization": `Bearer ${token}` } }
        );

        if (!response.ok) {
            throw new Error("Failed to load solutions");
        }

        const questions = await response.json();
        renderSolutions(questions);

    } catch (err) {
        console.error(err);
        list.innerHTML = "<p>Could not load solutions. Please try again.</p>";
    }
}

function renderSolutions(questions) {
    const list = document.getElementById("solutionsList");
    list.innerHTML = "";

    questions.forEach((q, idx) => {
        const card = document.createElement("div");
        card.className = "review-card";

        const optionsHtml = q.options.map((opt, optIdx) => {
            const isCorrect = optIdx === q.correctOption;
            return `
                <div class="review-option ${isCorrect ? "correct-answer" : ""}">
                    ${isCorrect ? '<i class="fa-solid fa-circle-check"></i>' : ""}
                    <span>${opt}</span>
                </div>
            `;
        }).join("");

        const explanationHtml = q.explanation
            ? `<div class="explanation"><strong>Explanation:</strong> ${q.explanation}</div>`
            : "";

        card.innerHTML = `
            <h3>${idx + 1}. ${q.questionText}</h3>
            <div class="review-options-list">${optionsHtml}</div>
            ${explanationHtml}
        `;

        list.appendChild(card);
    });
}