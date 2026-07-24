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

    document.getElementById("reviewTitle").textContent = `${course} - Level ${level} Review`;

    loadReview(course, level, token);
});

async function loadReview(course, level, token) {
    const list = document.getElementById("reviewList");

    try {
        // Expecting backend to return the most recent attempt for this course+level:
        // [ { questionText, options: [...], correctOption: 1, selectedOption: 2 }, ... ]
        const response = await fetch(
            `${API_BASE}/quiz/review?course=${encodeURIComponent(course)}&level=${level}`,
            { headers: { "Authorization": `Bearer ${token}` } }
        );

        if (!response.ok) {
            throw new Error("Failed to load review");
        }

        const questions = await response.json();
        renderReview(questions);

    } catch (err) {
        console.error(err);
        list.innerHTML = "<p>Could not load review. Please try again.</p>";
    }
}

function renderReview(questions) {
    const list = document.getElementById("reviewList");
    list.innerHTML = "";

    questions.forEach((q, idx) => {
        const card = document.createElement("div");
        card.className = "review-card";

        let tagHtml;
        if (q.selectedOption === null || q.selectedOption === undefined) {
            tagHtml = `<span class="tag unanswered">Unanswered</span>`;
        } else if (q.selectedOption === q.correctOption) {
            tagHtml = `<span class="tag correct">Correct</span>`;
        } else {
            tagHtml = `<span class="tag incorrect">Incorrect</span>`;
        }

        const optionsHtml = q.options.map((opt, optIdx) => {
            let cls = "review-option";
            let icon = "";

            if (optIdx === q.correctOption) {
                cls += " correct-answer";
                icon = `<i class="fa-solid fa-circle-check"></i>`;
            } else if (optIdx === q.selectedOption) {
                cls += " wrong-selected";
                icon = `<i class="fa-solid fa-circle-xmark"></i>`;
            }

            return `<div class="${cls}">${icon}<span>${opt}</span></div>`;
        }).join("");

        card.innerHTML = `
            ${tagHtml}
            <h3>${idx + 1}. ${q.questionText}</h3>
            <div class="review-options-list">${optionsHtml}</div>
        `;

        list.appendChild(card);
    });
}