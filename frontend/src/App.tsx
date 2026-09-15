import { Navigate, Route, Routes } from "react-router-dom";
import { Layout } from "./components/Layout";
import { ProtectedRoute } from "./components/ProtectedRoute";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";
import { FinancePage } from "./pages/FinancePage";
import { FeedPage } from "./pages/FeedPage";
import { ProfilePage } from "./pages/ProfilePage";

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/registrar" element={<RegisterPage />} />

      <Route
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/financas" element={<FinancePage />} />
        <Route path="/feed" element={<FeedPage />} />
        <Route path="/perfil" element={<ProfilePage />} />
      </Route>

      <Route path="*" element={<Navigate to="/financas" replace />} />
    </Routes>
  );
}

export default App;
