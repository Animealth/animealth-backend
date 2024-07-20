document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("createArticleForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const title = document.getElementById("title").value;
        const content = document.getElementById("content").value;

        fetch("/api/articles/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ title, content }),
        })
            .then(response => response.json())
            .then(data => {
                alert("Article created successfully!");
                window.location.href = "/articles/read";
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Failed to create article.");
            });
    });
});
