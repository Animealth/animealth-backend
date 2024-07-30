document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("updateArticleForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const articleId = document.getElementById("updateId").value;
        const title = document.getElementById("updateTitle").value;
        const content = document.getElementById("updateContent").value;

        fetch("/api/articles/update", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ articleId, title, content }),
        })
            .then(response => response.json())
            .then(data => {
                alert("Article updated successfully!");
                window.location.href = "/articles/read";
            });
    });
});
