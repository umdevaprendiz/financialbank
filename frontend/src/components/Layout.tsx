import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export function Layout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  async function handleLogout() {
    await logout();
    navigate("/login");
  }

  const linkClass = ({ isActive }: { isActive: boolean }) => (isActive ? "active" : "");

  return (
    <div>
      <header className="app-header">
        <div className="brand-mark" style={{ marginBottom: 0 }}>
          <span className="dot" /> FinancialBank
        </div>
        <nav className="app-nav">
          <NavLink to="/financas" className={linkClass}>Financeiro</NavLink>
          <NavLink to="/feed" className={linkClass}>Feed</NavLink>
          <NavLink to="/perfil" className={linkClass}>Perfil</NavLink>
        </nav>
        <div style={{ display: "flex", alignItems: "center", gap: 14 }}>
          <span style={{ fontSize: 13.5, color: "var(--ink-muted)", fontWeight: 500 }}>
            {user?.nome ?? user?.email}
          </span>
          <button className="ghost" onClick={handleLogout}>Sair</button>
        </div>
      </header>
      <main className="app-main">
        <Outlet />
      </main>
    </div>
  );
}
