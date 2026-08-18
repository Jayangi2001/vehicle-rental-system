import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../api/apiClient";
import Navbar from "../components/Navbar";

function Vehicles() {
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    apiClient.get("/vehicles")
      .then((res) => setVehicles(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      <Navbar />
      <div className="container">
        <h2 className="page-title">Available Vehicles</h2>
        <p className="page-sub">Pick a ride and book it in seconds</p>

        {loading && <p>Loading...</p>}
        {!loading && vehicles.length === 0 && (
          <div className="empty-state">No vehicles found.</div>
        )}

        <div className="vehicle-grid">
          {vehicles.map((v) => (
            <div className="vehicle-card" key={v.id}>
              <div className="vehicle-thumb">🚘</div>
              <div className="vehicle-body">
                <h3>{v.brand} {v.model}</h3>
                <p className="vehicle-meta">Type: {v.type}</p>
                <p className="vehicle-meta">Plate: {v.licensePlate}</p>
                <div className="vehicle-price">Rs. {v.pricePerDay} / day</div>
                <span className={`badge ${v.isAvailable ? "badge-available" : "badge-unavailable"}`}>
                  {v.isAvailable ? "Available" : "Unavailable"}
                </span>
                <div style={{ marginTop: 12 }}>
                  <button
                    className="btn"
                    disabled={!v.isAvailable}
                    onClick={() => navigate(`/book/${v.id}`, { state: { vehicle: v } })}
                  >
                    Rent This
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Vehicles;