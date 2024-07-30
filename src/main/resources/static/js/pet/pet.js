document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("updatePetForm")?.addEventListener("submit", function (event) {
        event.preventDefault();
        const formData = new FormData(event.target);
        const petData = Object.fromEntries(formData);

        fetch("/api/pet/update", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(petData),
        })
            .then(response => response.json())
            .then(data => {
                alert("Pet updated successfully!");
                window.location.href = `/api/pet/${petData.petId}`;
            });
    });

    document.querySelectorAll(".delete-pet-button").forEach(button => {
        button.addEventListener("click", function () {
            const petId = this.dataset.petId;

            if (confirm("Are you sure you want to delete this pet?")) {
                fetch(`/api/pet/delete/${petId}`, {
                    method: "DELETE",
                })
                    .then(response => {
                        if (response.ok) {
                            alert("Pet deleted successfully!");
                            window.location.href = "/api/pet";
                        } else {
                            alert("Failed to delete pet.");
                        }
                    });
            }
        });
    });
});
