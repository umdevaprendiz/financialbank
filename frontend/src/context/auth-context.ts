import { createContext } from "react";
import type { Me } from "../api/auth";

export type AuthContextValue = {
  user: Me | null;
  loading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
};

export const AuthContext = createContext<AuthContextValue | null>(null);
