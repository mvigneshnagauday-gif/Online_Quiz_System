const API_BASE = "http://localhost:8080/api";

const TIME_BY_LEVEL = {
    1: 10 * 60, // 10 minutes
    2: 20 * 60, // 20 minutes
    3: 30 * 60  // 30 minutes
};

let course = "";
let level = 1;
let questions = [];
let currentIndex = 0;
let userAnswers = {}; // { questionId: selectedOptionIndex }
let timeRemaining = 0;
let timerInterval = null;
let token = null;

document.addEventListener("DOMContentLoaded", () => {
    token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    course = params.get("course");
    level = parseInt(params.get("level"), 10);

    if (!course || !level || !TIME_BY_LEVEL[level]) {
        window.location.href = "dashboard.html";
        return;
    }

    document.getElementById("quizTitle").textContent = `${course} - Level ${level}`;
    timeRemaining = TIME_BY_LEVEL[level];

    loadQuestions();
});

async function loadQuestions() {
    try {
        const response = await fetch(
            `${API_BASE}/quiz/questions?course=${encodeURIComponent(course)}&level=${level}`,
            { headers: { "Authorization": `Bearer ${token}` } }
        );

        if (!response.ok) {
            throw new Error("Failed to load questions");
        }

        questions = await response.json();
        // Expecting: [ { id, questionText, options: [opt1, opt2, opt3, opt4] }, ... ]

        renderQuestion();
        startTimer();

    } catch (err) {
        console.error(err);
        document.getElementById("questionCard").innerHTML = "<p>Could not load questions. Please try again.</p>";
    }
}

function startTimer() {
    updateTimerDisplay();

    timerInterval = setInterval(() => {
        timeRemaining--;

        if (timeRemaining <= 0) {
            clearInterval(timerInterval);
            timeRemaining = 0;
            updateTimerDisplay();
            // Auto-submit, no confirmation needed
            submitQuiz(true);
            return;
        }

        updateTimerDisplay();

        if (timeRemaining <= 60) {
            document.getElementById("timerBox").classList.add("warning");
        }
    }, 1000);
}

function updateTimerDisplay() {
    const minutes = Math.floor(timeRemaining / 60);
    const seconds = timeRemaining % 60;
    document.getElementById("timerDisplay").textContent =
        `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
}

function renderQuestion() {
    const q = questions[currentIndex];
    const card = document.getElementById("questionCard");

    document.getElementById("quizSubtitle").textContent =
        `Question ${currentIndex + 1} of ${questions.length}`;

    document.getElementById("progressFill").style.width =
        `${((currentIndex + 1) / questions.length) * 100}%`;

    const optionsHtml = q.options.map((opt, idx) => {
        const isSelected = userAnswers[q.id] === idx;
        return `
            <label class="option ${isSelected ? "selected" : ""}" onclick="selectOption(${q.id}, ${idx})">
                <input type="radio" name="q_${q.id}" ${isSelected ? "checked" : ""}>
                <span>${opt}</span>
            </label>
        `;
    }).join("");

    card.innerHTML = `
        <h3>${currentIndex + 1}. ${q.questionText}</h3>
        <div class="options-list">${optionsHtml}</div>
    `;

    document.getElementById("prevBtn").disabled = currentIndex === 0;
    document.getElementById("nextBtn").style.display =
        currentIndex === questions.length - 1 ? "none" : "block";
    document.getElementById("submitBtn").style.display =
        currentIndex === questions.length - 1 ? "block" : "none";
}

function selectOption(questionId, optionIndex) {
    userAnswers[questionId] = optionIndex;
    renderQuestion();
}

function prevQuestion() {
    if (currentIndex > 0) {
        currentIndex--;
        renderQuestion();
    }
}

function nextQuestion() {
    if (currentIndex < questions.length - 1) {
        currentIndex++;
        renderQuestion();
    }
}

function confirmSubmit() {
    const answeredCount = Object.keys(userAnswers).length;
    document.getElementById("answeredCount").textContent = answeredCount;
    document.getElementById("confirmModal").classList.add("show");
}

function closeModal() {
    document.getElementById("confirmModal").classList.remove("show");
}

async function submitQuiz(isAutoSubmit) {
    clearInterval(timerInterval);
    closeModal();

    // Build answers payload: unanswered questions are simply omitted -> scored as wrong
    const answersPayload = questions.map(q => ({
        questionId: q.id,
        selectedOption: userAnswers[q.id] !== undefined ? userAnswers[q.id] : null
    }));

    try {
        const response = await fetch(`${API_BASE}/quiz/submit`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                course,
                level,
                answers: answersPayload,
                autoSubmitted: isAutoSubmit
            })
        });

        if (!response.ok) {
            throw new Error("Failed to submit quiz");
        }

        const result = await response.json();
        // Expecting: { score, correctCount, totalQuestions, passed, nextLevelUnlocked }

        localStorage.setItem("lastResult", JSON.stringify(result));
        window.location.href = `result.html?course=${encodeURIComponent(course)}&level=${level}`;

    } catch (err) {
        console.error(err);
        alert("Something went wrong submitting your quiz. Please try again.");
    }
}