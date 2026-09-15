import { Navigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { user, loading } = useAuth();

  if (loading) return <p style={{ padding: 24 }}>Carregando...</p>;
  if (!user) return <Navigate to="/login" replace />;

  return <>{children}</>;
}
