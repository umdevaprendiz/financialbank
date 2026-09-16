import { useEffect, useState, type ReactNode } from "react";
import { login as apiLogin, logout as apiLogout, me as apiMe, type Me } from "../api/auth";
import { ApiError } from "../api/client";
import { AuthContext } from "./auth-context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<Me | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    apiMe()
      .then(setUser)
      .catch(() => setUser(null))
      .finally(() => setLoading(false));
  }, []);

  async function login(email: string, password: string) {
    await apiLogin(email, password);
    const profile = await apiMe();
    setUser(profile);
  }

  async function logout() {
    try {
      await apiLogout();
    } catch (e) {
      if (!(e instanceof ApiError)) throw e;
    }
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
