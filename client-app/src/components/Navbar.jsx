import { NavLink, useNavigate } from "react-router-dom";
import { logout } from "../utils/auth";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <span className="brand">🚗 RentIt</span>
      <NavLink to="/dashboard" className={({ isActive }) => (isActive ? "active" : "")}>
        Vehicles
      </NavLink>
      <NavLink to="/my-rentals" className={({ isActive }) => (isActive ? "active" : "")}>
        My Rentals
      </NavLink>
      <button className="logout-btn" onClick={handleLogout}>Logout</button>
    </nav>
  );
}

export default Navbar;