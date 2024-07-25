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
                const articles = data.data.content;

                if (articles && articles.length > 0) {
                    articles.forEach(article => {
                        const articleDiv = document.createElement("div");
                        articleDiv.className = "article";
                        articleDiv.setAttribute("data-article-id", article.articleId);
                        articleDiv.innerHTML = `
                            <p><strong>ID:</strong> ${article.articleId}</p>
                            <p><strong>Title:</strong> ${article.title}</p>
                            <p><strong>Writer:</strong> ${article.writer}</p>
                        `;
                        articleDetails.appendChild(articleDiv);
                    });

                    document.querySelectorAll('.article').forEach(articleDiv => {
                        articleDiv.addEventListener('click', function () {
                            const articleId = this.getAttribute('data-article-id');
                            window.location.href = `/articles/read/${articleId}`;
                        });
                    });
                } else {
                    articleDetails.innerHTML = '<p>No articles found.</p>';
                }
            })
            .catch(error => console.error('Error fetching articles:', error));
    }

    // Initial fetch to display articles on page load
    getArticles(0, "createdTime");
});
