import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import apiClient from "../api/apiClient";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await apiClient.post("/auth/login", { email, password });
      localStorage.setItem("token", res.data.accessToken);
      navigate("/dashboard");
    } catch (err) {
      setError("Login failed. Check your email/password.");
    }
  };
    return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>🚗 Welcome Back</h1>
        <p className="auth-sub">Login to book your next ride</p>
        <form onSubmit={handleLogin}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="field"
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="field"
            required
          />
          {error && <p className="error-text">{error}</p>}
          <button type="submit" className="btn">Login</button>
        </form>
        <p className="link-row">
          No account? <Link to="/register">Register here</Link>
        </p>
      </div>
    </div>
  );
  
}

export default Login;