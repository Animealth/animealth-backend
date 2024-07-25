document.addEventListener("DOMContentLoaded", function () {
    const articleId = window.location.pathname.split("/").pop();

    if (articleId) {
        fetchArticle(articleId);
    } else {
        console.error("No article ID found in the URL.");
    }

    document.getElementById("addCommentForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const content = document.getElementById("commentContent").value;
        addComment(articleId, content, null);
    });

    function fetchArticle(articleId) {
        fetch(`/api/articles/read/${articleId}`)
            .then(response => response.json())
            .then(data => {
                const articleDetails = document.getElementById("articleDetails");
                const article = data.data;

                if (article) {
                    articleDetails.innerHTML = `
                        <h2>${article.title}</h2>
                        <p><strong>Writer:</strong> ${article.writer}</p>
                        <p>${article.content}</p>
                    `;
                    displayComments(article.comments);
                } else {
                    articleDetails.innerHTML = '<p>Article not found</p>';
                }
            })
            .catch(error => console.error('Error fetching article:', error));
    }

    function displayComments(comments) {
        const commentsSection = document.getElementById("commentsSection");
        commentsSection.innerHTML = '';
        comments.forEach(comment => {
            const commentDiv = createCommentElement(comment);
            commentsSection.appendChild(commentDiv);
        });
    }

    function createCommentElement(comment) {
        const commentDiv = document.createElement("div");
        commentDiv.className = "comment";
        commentDiv.setAttribute("data-comment-id", comment.commentId);
        commentDiv.innerHTML = `
            <p>${comment.content}</p>
            <button class="replyButton">Reply</button>
            <button class="editComment">Edit</button>
            <button class="deleteComment">Delete</button>
            <div class="replies"></div>
        `;

        commentDiv.querySelector('.replyButton').addEventListener('click', function () {
            const replyContent = prompt("Reply to this comment:");
            if (replyContent) {
                addComment(comment.articleId, replyContent, comment.commentId);
            }
        });

        commentDiv.querySelector('.editComment').addEventListener('click', function () {
            const newContent = prompt("Edit your comment:", comment.content);
            if (newContent) {
                updateComment(comment.commentId, newContent);
            }
        });

        commentDiv.querySelector('.deleteComment').addEventListener('click', function () {
            deleteComment(comment.commentId);
        });

        if (comment.depth > 0) {
            const parentComment = commentsSection.querySelector(`[data-comment-id="${comment.articleId}"] .replies`);
            if (parentComment) {
                parentComment.appendChild(commentDiv);
            }
        }

        return commentDiv;
    }

    function addComment(articleId, content, parentCommentId) {
        fetch(`/api/comments/save`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ articleId, content, parentCommentId }),
        })
            .then(response => response.json())
            .then(() => {
                document.getElementById("commentContent").value = '';
                fetchArticle(articleId);
            })
            .catch(error => console.error('Error adding comment:', error));
    }

    function updateComment(commentId, content) {
        fetch(`/api/comments/update`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ commentId, content }),
        })
            .then(response => response.json())
            .then(() => {
                fetchArticle(articleId);
            })
            .catch(error => console.error('Error updating comment:', error));
    }

    function deleteComment(commentId) {
        fetch(`/api/comments/delete/${commentId}`, {
            method: "POST"
        })
            .then(response => response.json())
            .then(() => {
                fetchArticle(articleId);
            })
            .catch(error => console.error('Error deleting comment:', error));
    }
});
