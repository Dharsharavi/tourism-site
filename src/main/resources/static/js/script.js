document.addEventListener("DOMContentLoaded", () => {
  const page = window.location.pathname.split("/").pop() || "index.html";
  document.querySelectorAll(".navbar nav a").forEach(link => {
    if (link.getAttribute("href") === page) {
      link.classList.add("active");
    }
  });

  // ---- Attractions page: fetch from GET /api/attractions ----
  const attractionsList = document.getElementById("attractionsList");
  if (attractionsList) {
    fetch("/api/attractions")
      .then(r => r.json())
      .then(items => {
        if (!items.length) {
          attractionsList.innerHTML = "<p>No attractions listed yet.</p>";
          return;
        }
        attractionsList.innerHTML = items.map(a => `
          <div class="card">
            <span class="badge">${a.category}</span>
            <h3>${a.name}</h3>
            <p><strong>📍 ${a.location}</strong></p>
            <p>${a.description}</p>
          </div>
        `).join("");
      })
      .catch(() => {
        attractionsList.innerHTML = "<p>Could not load attractions right now. Please try again later.</p>";
      });
  }

  // ---- Hotels page: fetch from GET /api/hotels ----
  const hotelsList = document.getElementById("hotelsList");
  if (hotelsList) {
    fetch("/api/hotels")
      .then(r => r.json())
      .then(items => {
        if (!items.length) {
          hotelsList.innerHTML = "<p>No hotels listed yet.</p>";
          return;
        }
        hotelsList.innerHTML = items.map(h => `
          <div class="card">
            <div class="stars">${"★".repeat(h.starRating)}${"☆".repeat(5 - h.starRating)}</div>
            <h3>${h.name}</h3>
            <p><strong>📍 ${h.location}</strong></p>
            <p class="hotel-price">₹${h.pricePerNight.toFixed(2)} / night</p>
            <p>Contact: ${h.contact}</p>
          </div>
        `).join("");
      })
      .catch(() => {
        hotelsList.innerHTML = "<p>Could not load hotels right now. Please try again later.</p>";
      });
  }

  // ---- Booking page: POST to /api/bookings ----
  const bookingForm = document.getElementById("bookingForm");
  if (bookingForm) {
    bookingForm.addEventListener("submit", (e) => {
      e.preventDefault();

      const payload = {
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        destination: document.getElementById("destination").value,
        travelDate: document.getElementById("travelDate").value,
        numberOfTravelers: parseInt(document.getElementById("numberOfTravelers").value, 10) || 1,
        notes: document.getElementById("notes").value
      };

      const successBox = document.getElementById("bookingSuccess");
      successBox.style.display = "none";
      successBox.style.background = "#e7f9ee";
      successBox.style.color = "#1a7f4e";

      fetch("/api/bookings", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      })
        .then(res => {
          if (!res.ok) throw new Error("Booking failed");
          return res.json();
        })
        .then(() => {
          successBox.textContent = "✅ Booking request submitted! Our travel desk will contact you shortly.";
          successBox.style.display = "block";
          bookingForm.reset();
        })
        .catch(() => {
          successBox.textContent = "⚠️ Something went wrong submitting your booking. Please try again.";
          successBox.style.background = "#fdecea";
          successBox.style.color = "#b3261e";
          successBox.style.display = "block";
        });
    });
  }
});
