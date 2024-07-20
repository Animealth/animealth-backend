document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("deleteArticleForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const articleId = document.getElementById("deleteId").value;

        fetch(`/api/articles/delete/${articleId}`, {
            method: "POST",
        })
            .then(response => {
                if (response.ok) {
                    alert("Article deleted successfully!");
                    window.location.href = "/articles/read";
                }
            });
    });
});
