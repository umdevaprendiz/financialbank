import { get, post, put } from "./client";

export type PaymentMethod = "DINHEIRO" | "PIX" | "CARTAO" | "BOLETO" | "TRANSFERENCIA";

export type ExpenseEntry = {
  id: number;
  amount: number;
  paymentMethod: PaymentMethod;
  description: string | null;
  worthIt: boolean | null;
  reason: string | null;
  dateCreation: string;
};

export type SpendingByMethod = { method: PaymentMethod; total: number; count: number };

export type PersonalFinanceSummary = {
  averageIncome: number;
  totalSpentThisMonth: number;
  remaining: number;
  percentOfIncomeSpent: number;
  byMethod: SpendingByMethod[];
  previousMonthTotal: number;
  percentChangeVsPreviousMonth: number;
};

export const getIncome = () => get<{ averageIncome: number }>("/api/finance/income");
export const updateIncome = (averageIncome: number) =>
  put<{ averageIncome: number }>("/api/finance/income", { averageIncome });

export const addExpense = (expense: {
  amount: number;
  paymentMethod: PaymentMethod;
  description: string;
  worthIt: boolean | null;
  reason: string;
}) => post<ExpenseEntry>("/api/finance/expenses", expense);

export const getExpenses = () => get<ExpenseEntry[]>("/api/finance/expenses");
export const getNotWorthIt = () => get<ExpenseEntry[]>("/api/finance/expenses/not-worth-it");
export const getSummary = () => get<PersonalFinanceSummary>("/api/finance/summary");
