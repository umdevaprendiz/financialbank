import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../api/auth";
import { ApiError } from "../api/client";

export function RegisterPage() {
  const navigate = useNavigate();
  const [nome, setNome] = useState("");
  const [dataNascimento, setDataNascimento] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await register(nome, dataNascimento, email, senha);
      navigate("/login");
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao criar conta.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-shell">
      <div className="auth-box">
        <div className="brand-mark"><span className="dot" /> FinancialBank</div>
        <h1>Criar conta</h1>
        <p className="subtitle">Leva menos de um minuto.</p>

        <form onSubmit={handleSubmit} className="card">
          <div className="field">
            <label htmlFor="nome">Nome</label>
            <input id="nome" value={nome} onChange={(e) => setNome(e.target.value)} required autoFocus />
          </div>
          <div className="field">
            <label htmlFor="dataNascimento">Data de nascimento</label>
            <input id="dataNascimento" type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
          </div>
          <div className="field">
            <label htmlFor="email">E-mail</label>
            <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div className="field">
            <label htmlFor="senha">Senha</label>
            <input id="senha" type="password" minLength={8} value={senha} onChange={(e) => setSenha(e.target.value)} required />
            <p className="hint-text">Mínimo de 8 caracteres.</p>
          </div>
          {error && <p className="error-text">{error}</p>}
          <button type="submit" disabled={loading} style={{ width: "100%", marginTop: 4 }}>
            {loading ? "Criando..." : "Criar conta"}
          </button>
        </form>

        <p className="auth-footer">Já tem conta? <Link to="/login">Entrar</Link></p>
      </div>
    </div>
  );
}
