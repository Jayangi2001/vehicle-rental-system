import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Vehicles from "./pages/Vehicles";
import Booking from "./pages/Booking";
import Payment from "./pages/Payment";
import MyRentals from "./pages/MyRentals";
import { isLoggedIn } from "./utils/auth";

function ProtectedRoute({ children }) {
  return isLoggedIn() ? children : <Navigate to="/login" />;
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<ProtectedRoute><Vehicles /></ProtectedRoute>} />
        <Route path="/book/:vehicleId" element={<ProtectedRoute><Booking /></ProtectedRoute>} />
        <Route path="/pay/:rentalId" element={<ProtectedRoute><Payment /></ProtectedRoute>} />
        <Route path="*" element={<Navigate to="/login" />} />
        <Route path="/my-rentals" element={<ProtectedRoute><MyRentals /></ProtectedRoute>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;