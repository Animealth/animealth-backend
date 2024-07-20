document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("getArticlesForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const page = document.getElementById("page").value;
        const criteria = document.getElementById("criteria").value;

        getArticles(page, criteria);
    });

    function getArticles(page, criteria) {
        fetch(`/api/articles/read?page=${page}&criteria=${criteria}`)
            .then(response => response.json())
            .then(data => {
                const articleDetails = document.getElementById("articleDetails");
                articleDetails.innerHTML = '<h2>Articles List</h2>';
                data.data.content.forEach(article => {
                    articleDetails.innerHTML += `
                        <div>
                            <p><strong>ID:</strong> ${article.articleId}</p>
                            <p><strong>Title:</strong> ${article.title}</p>
                            <p><strong>Writer:</strong> ${article.writer}</p>
                        </div>
                    `;
                });
            });
    }

    // Initial fetch to display articles on page load
    getArticles(0, "createdTime");
});
