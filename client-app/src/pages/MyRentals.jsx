import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import { getLocalRentals } from "../utils/rentals";

function MyRentals() {
  const [rentals, setRentals] = useState([]);

  useEffect(() => {
    setRentals(getLocalRentals());
  }, []);

  return (
    <div>
      <Navbar />
      <div className="container">
        <h2 className="page-title">My Rentals</h2>
        <p className="page-sub">Bookings you've made in this browser</p>

        {rentals.length === 0 && (
          <div className="empty-state">
            <p>No rentals yet.</p>
            <Link to="/dashboard">Browse vehicles →</Link>
          </div>
        )}

        {rentals.map((r) => (
          <div className="rental-card" key={r.id}>
            <div>
              <strong>{r.brand} {r.model}</strong>
              <p className="vehicle-meta">Rental ID: {r.id}</p>
              <p className="vehicle-meta">{r.days} day(s) · Rs. {r.totalAmount || "—"}</p>
            </div>
            <span className={`status-pill ${r.paid ? "paid" : ""}`}>
              {r.paid ? "Paid" : "Pending Payment"}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyRentals;