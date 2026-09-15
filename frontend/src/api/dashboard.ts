import { get } from "./client";

export type AccountBalance = { numberAccount: string; balance: number; status: string };
export type TransactionSummary = {
  trackingCode: string;
  type: string;
  amount: number;
  direction: string;
  description: string | null;
  dateCreation: string;
};

export const getBalances = () => get<AccountBalance[]>("/api/dashboard/balances");
export const getTransactions = (days = 30, limit = 20) =>
  get<TransactionSummary[]>(`/api/dashboard/transactions?days=${days}&limit=${limit}`);
