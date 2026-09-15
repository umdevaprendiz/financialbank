import { useEffect, useState } from "react";
import {
  addExpense,
  getExpenses,
  getIncome,
  getSummary,
  updateIncome,
  type ExpenseEntry,
  type PaymentMethod,
  type PersonalFinanceSummary,
} from "../api/finance";
import { ApiError } from "../api/client";

const METHOD_LABEL: Record<PaymentMethod, string> = {
  DINHEIRO: "Dinheiro",
  PIX: "PIX",
  CARTAO: "Cartão",
  BOLETO: "Boleto",
  TRANSFERENCIA: "Transferência",
};

function currency(value: number) {
  return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

export function FinancePage() {
  const [income, setIncome] = useState<number | null>(null);
  const [incomeInput, setIncomeInput] = useState("");
  const [summary, setSummary] = useState<PersonalFinanceSummary | null>(null);
  const [expenses, setExpenses] = useState<ExpenseEntry[]>([]);
  const [error, setError] = useState<string | null>(null);

  const [amount, setAmount] = useState("");
  const [method, setMethod] = useState<PaymentMethod>("PIX");
  const [description, setDescription] = useState("");
  const [worthIt, setWorthIt] = useState<"sim" | "nao" | "">("");
  const [reason, setReason] = useState("");
  const [savingExpense, setSavingExpense] = useState(false);

  async function loadAll() {
    try {
      const [incomeData, summaryData, expenseData] = await Promise.all([
        getIncome(),
        getSummary(),
        getExpenses(),
      ]);
      setIncome(incomeData.averageIncome);
      setSummary(summaryData);
      setExpenses(expenseData);
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao carregar dados financeiros.");
    }
  }

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect -- carregar os dados financeiros ao montar a página
    loadAll();
  }, []);

  async function handleSaveIncome(e: React.FormEvent) {
    e.preventDefault();
    const value = Number(incomeInput);
    if (Number.isNaN(value) || value < 0) return;
    await updateIncome(value);
    setIncomeInput("");
    await loadAll();
  }

  async function handleAddExpense(e: React.FormEvent) {
    e.preventDefault();
    const value = Number(amount);
    if (Number.isNaN(value) || value <= 0) return;

    setSavingExpense(true);
    try {
      await addExpense({
        amount: value,
        paymentMethod: method,
        description,
        worthIt: worthIt === "" ? null : worthIt === "sim",
        reason,
      });
      setAmount("");
      setDescription("");
      setWorthIt("");
      setReason("");
      await loadAll();
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao lançar gasto.");
    } finally {
      setSavingExpense(false);
    }
  }

  if (error) return <p className="error-text">{error}</p>;

  if (income === 0) {
    return (
      <div className="auth-shell" style={{ minHeight: "60vh" }}>
        <div className="auth-box">
          <h1>Antes de começar</h1>
          <p className="subtitle">Qual a sua renda média mensal? Pode mudar quando quiser, depois.</p>
          <form onSubmit={handleSaveIncome} className="card">
            <div className="field">
              <label htmlFor="income">Renda média mensal</label>
              <input
                id="income"
                type="number"
                min="0"
                step="0.01"
                placeholder="0,00"
                value={incomeInput}
                onChange={(e) => setIncomeInput(e.target.value)}
                required
                autoFocus
              />
            </div>
            <button type="submit" style={{ width: "100%" }}>Salvar e continuar</button>
          </form>
        </div>
      </div>
    );
  }

  return (
    <div>
      <div className="page-head">
        <h1>Painel financeiro</h1>
        <form onSubmit={handleSaveIncome} style={{ display: "flex", gap: 8, alignItems: "center" }}>
          <span style={{ fontSize: 13, color: "var(--ink-muted)" }}>
            Renda: <strong style={{ color: "var(--ink)" }}>{income !== null ? currency(income) : "—"}</strong>
          </span>
          <input
            style={{ width: 120 }}
            type="number"
            min="0"
            step="0.01"
            placeholder="Nova renda"
            value={incomeInput}
            onChange={(e) => setIncomeInput(e.target.value)}
          />
          <button type="submit" className="secondary">Atualizar</button>
        </form>
      </div>

      {summary && (
        <div className="section">
          <div className="stat-grid">
            <div className="stat-tile">
              <div className="label">Gasto este mês</div>
              <div className="value">{currency(summary.totalSpentThisMonth)}</div>
              <div className="hint-text">{summary.percentOfIncomeSpent.toFixed(1)}% da renda</div>
            </div>
            <div className="stat-tile">
              <div className="label">Sobra estimada</div>
              <div className={`value ${summary.remaining < 0 ? "negative" : "positive"}`}>
                {currency(summary.remaining)}
              </div>
            </div>
            <div className="stat-tile">
              <div className="label">Vs. mês passado</div>
              <div className="value" style={{ display: "flex", alignItems: "center", gap: 8 }}>
                <span className={`pill ${summary.percentChangeVsPreviousMonth > 0 ? "negative" : "positive"}`}>
                  {summary.percentChangeVsPreviousMonth > 0 ? "▲" : "▼"} {Math.abs(summary.percentChangeVsPreviousMonth).toFixed(1)}%
                </span>
              </div>
              <div className="hint-text">{currency(summary.previousMonthTotal)} no mês anterior</div>
            </div>
          </div>

          {summary.byMethod.length > 0 && (
            <div className="card" style={{ marginTop: 12 }}>
              {summary.byMethod.map((m) => (
                <div key={m.method} className="list-row">
                  <span>{METHOD_LABEL[m.method]}</span>
                  <span className="amount">{currency(m.total)} <span className="meta">({m.count}x)</span></span>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      <div className="section">
        <h2>Lançar gasto</h2>
        <form onSubmit={handleAddExpense} className="card">
          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
            <div className="field">
              <label htmlFor="amount">Valor</label>
              <input id="amount" type="number" min="0.01" step="0.01" placeholder="0,00" value={amount} onChange={(e) => setAmount(e.target.value)} required />
            </div>
            <div className="field">
              <label htmlFor="method">Forma de pagamento</label>
              <select id="method" value={method} onChange={(e) => setMethod(e.target.value as PaymentMethod)}>
                {Object.entries(METHOD_LABEL).map(([value, label]) => (
                  <option key={value} value={value}>{label}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="field">
            <label htmlFor="description">Descrição</label>
            <input id="description" value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Ex.: almoço, uber, aluguel..." />
          </div>
          <div style={{ display: "grid", gridTemplateColumns: "1fr 2fr", gap: 16 }}>
            <div className="field">
              <label htmlFor="worthIt">Valeu a pena?</label>
              <select id="worthIt" value={worthIt} onChange={(e) => setWorthIt(e.target.value as "sim" | "nao" | "")}>
                <option value="">Prefiro não dizer</option>
                <option value="sim">Sim</option>
                <option value="nao">Não</option>
              </select>
            </div>
            <div className="field">
              <label htmlFor="reason">Por quê?</label>
              <input id="reason" value={reason} onChange={(e) => setReason(e.target.value)} placeholder="Opcional" />
            </div>
          </div>
          <button type="submit" disabled={savingExpense}>{savingExpense ? "Salvando..." : "Lançar gasto"}</button>
        </form>
      </div>

      <div className="section">
        <h2>Histórico</h2>
        <div className="card">
          {expenses.length === 0 && <p className="empty-state">Nenhum gasto lançado ainda.</p>}
          {expenses.map((e) => (
            <div key={e.id} className="list-row">
              <div>
                <div>{e.description || METHOD_LABEL[e.paymentMethod]}</div>
                <div className="meta">{e.dateCreation.slice(0, 10)} · {METHOD_LABEL[e.paymentMethod]}</div>
              </div>
              <div style={{ display: "flex", alignItems: "center", gap: 10 }}>
                {e.worthIt !== null && (
                  <span className={`pill ${e.worthIt ? "positive" : "negative"}`}>
                    {e.worthIt ? "valeu" : "não valeu"}
                  </span>
                )}
                <span className="amount">{currency(e.amount)}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
