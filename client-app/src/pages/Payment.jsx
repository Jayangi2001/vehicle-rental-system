import { useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import apiClient from "../api/apiClient";
import Navbar from "../components/Navbar";
import { updateLocalRentalStatus } from "../utils/rentals";

function Payment() {
  const { rentalId } = useParams();
  const location = useLocation();
  const [amount, setAmount] = useState(location.state?.amount || "");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handlePay = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await apiClient.post("/payments/process", {
        rentalId,
        amount: Number(amount),
      });
      updateLocalRentalStatus(rentalId, true);
      setSuccess(true);
      setTimeout(() => navigate("/my-rentals"), 1800);
    } catch (err) {
      setError("Payment failed. Try again.");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="auth-page" style={{ background: "var(--bg)" }}>
        <div className="auth-card">
          <h1>Payment</h1>
          <p className="auth-sub">Rental ID: {rentalId}</p>
          <form onSubmit={handlePay}>
            <label style={{ fontSize: 13, color: "var(--muted)" }}>Amount (Rs.)</label>
            <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              className="field"
              required
            />
            {error && <p className="error-text">{error}</p>}
            {success && <p className="success-text">Payment successful! Redirecting...</p>}
            <button type="submit" className="btn">Pay Now</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Payment;