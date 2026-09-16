import { get, post } from "./client";

export type Me = { email: string; nome: string };

export const login = (email: string, password: string) =>
  post<{ email: string }>("/api/auth/login", { email, password });

export const logout = () => post<void>("/api/auth/logout");

export const me = () => get<Me>("/api/auth/me");

export const register = (nome: string, dataNascimento: string, email: string, senha: string) =>
  post<{ id: number; email: string }>("/auth/register", { nome, dataNascimento, email, senha });
