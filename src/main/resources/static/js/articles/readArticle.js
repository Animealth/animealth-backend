document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("getArticleByIdForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const articleId = document.getElementById("articleId").value;

        fetch(`/api/articles/read/${articleId}`)
            .then(response => response.json())
            .then(data => {
                const articleDetails = document.getElementById("articleDetails");
                articleDetails.innerHTML = `
                <h2>Article Details</h2>
                <p><strong>Title:</strong> ${data.data.title}</p>
                <p><strong>Content:</strong> ${data.data.content}</p>
                <p><strong>ID:</strong> ${data.data.articleId}</p>
                <p><strong>Writer:</strong> ${data.data.writer}</p>
            `;
            });
    });
});
