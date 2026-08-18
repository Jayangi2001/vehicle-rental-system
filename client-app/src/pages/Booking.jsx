import { useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import apiClient from "../api/apiClient";
import Navbar from "../components/Navbar";
import { getTokenPayload } from "../utils/auth";
import { saveLocalRental } from "../utils/rentals";

function Booking() {
  const { vehicleId } = useParams();
  const location = useLocation();
  const vehicle = location.state?.vehicle;
  const [days, setDays] = useState(1);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleBook = async (e) => {
    e.preventDefault();
    setError("");
    const payload = getTokenPayload();
    try {
      const res = await apiClient.post("/rentals", {
        vehicleId: Number(vehicleId),
        userId: payload?.userId,
        days: Number(days),
      });

      saveLocalRental({
        id: res.data.id,
        brand: vehicle?.brand || "Vehicle",
        model: vehicle?.model || `#${vehicleId}`,
        days,
        totalAmount: vehicle ? vehicle.pricePerDay * days : null,
        paid: false,
      });

      navigate(`/pay/${res.data.id}`, {
        state: { amount: vehicle ? vehicle.pricePerDay * days : "" },
      });
    } catch (err) {
      setError("Booking failed. Try again.");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="auth-page" style={{ background: "var(--bg)" }}>
        <div className="auth-card">
          <h1>Confirm Booking</h1>
          <p className="auth-sub">
            {vehicle ? `${vehicle.brand} ${vehicle.model}` : `Vehicle #${vehicleId}`}
          </p>
          <form onSubmit={handleBook}>
            <label style={{ fontSize: 13, color: "var(--muted)" }}>Number of Days</label>
            <input
              type="number"
              min="1"
              value={days}
              onChange={(e) => setDays(e.target.value)}
              className="field"
              required
            />
            {vehicle && (
              <p className="vehicle-meta">
                Estimated total: <strong>Rs. {vehicle.pricePerDay * days}</strong>
              </p>
            )}
            {error && <p className="error-text">{error}</p>}
            <button type="submit" className="btn">Confirm Booking</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Booking;