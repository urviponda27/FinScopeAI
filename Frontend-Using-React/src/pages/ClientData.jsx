"use client";

import CreditScoreChart from "../components/CreditScoreChart";
import PredictedCreditScoreChart from "../components/PredictedCreditScoreChart";
import ExpenseChart from "../components/ExpenseChart";
import ExpenseStatCard from "../components/ExpenseStatCard";
import React, { useEffect, useState, useSyncExternalStore } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import LoadingSpinner from "../components/LoadingSpinner";




const ClientData = () => {
  const { clientId } = useParams();

  const [analyticsSummary, setAnalyticsSummary] = useState([
    {
      title: "Total Loans",
      value: "0",
      description: "Total Number of Loans Active Currently",
      icon: "total",
    },
    {
      title: "Total EMI",
      value: "0",
      description: "Total Amount of EMIs to be paid per month.",
      icon: "approved",
    },
    {
      title: "Regular Payments",
      value: "0",
      description: "Number of Loans at which he is regular for payments",
      icon: "rejected",
    },
    {
      title: "Late Payments",
      value: "0",
      description: "Number of Loans at which he is irregular for payments",
      icon: "pending",
    },
  ]);

  const [creditScore, setCreditScore] = useState(0);
  const [expenseData, setExpenseData] = useState([]);
  const [suggestionData, setSuggestionData] = useState([]);
  const [isLoading,setIsLoading] = useState(false)
  const navigate = useNavigate();
  const baseUrl = import.meta.env.VITE_API_BASE_URL;
  const fetchUpdates = async () => {
    try {
      setIsLoading(true);
      const response = await axios.get(`${baseUrl}/api/updates/user/${clientId}`);
      const temp = await axios.get(`${baseUrl}/api/summary/user/${clientId}`);

      setCreditScore(temp.data.currCreditScore);


      const colors = [
        "#6366f1", // indigo-500
        "#8b5cf6", // violet-500
        "#a855f7", // purple-500
        "#d946ef", // fuchsia-500
        "#4f46e5", // indigo-600
        "#7c3aed", // violet-600
        "#9333ea", // purple-600
        "#c026d3", // fuchsia-600
      ];

      const suggestionItems = Object.entries(response.data.suggestions).map(
        ([description, value], index) => {
          return {
            title: `Option ${index + 1}`,
            description: description,
            value: value,
            color: colors[index % colors.length], // Assign color based on index
          };
        }
      );

      setSuggestionData(suggestionItems);

      console.log("Items :- ", suggestionItems)

      const dynamicExpenseData = [
        { type: "Food", count: temp.data.food },
        { type: "Entertainment", count: temp.data.entertainment },
        { type: "Gambling", count: temp.data.gambling },
        { type: "Shopping", count: temp.data.shopping },
        { type: "Travel", count: temp.data.travel },
        { type: "Utilities", count: temp.data.utilities },
        { type: "Savings", count: temp.data.savings },
        { type: "Others", count: temp.data.others || 0 },
      ]
      setExpenseData(dynamicExpenseData);

      setAnalyticsSummary([
        {
          title: "Total Loans",
          value: temp.data.totalActiveEMIs,
          description: "Total Number of Loans Active Currently",
          icon: "total",
        },
        {
          title: "Total EMI",
          value: `${(temp.data.totalMonthlyEMIAmount / 1000).toFixed(1)}K`,
          description: "Total Amount of EMIs to be paid per month.",
          icon: "approved",
        },
        {
          title: "Regular Payments",
          value: temp.data.regularEMIPayments,
          description: "Number of Loans at which he is regular for payments",
          icon: "rejected",
        },
        {
          title: "Late Payments",
          value: temp.data.lateEMIPayments,
          description: "Number of Loans at which he is irregular for payments",
          icon: "pending",
        },
      ]);
      console.log(response.data);
      console.log(temp.data);
    } catch (error) {
      console.error(error)
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {

    const token = localStorage.getItem("accessToken");
    if (!token) {
      navigate("/login");
    }
    fetchUpdates();
  }, [])



  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-purple-50 py-6 px-4 sm:px-6">
    {isLoading && <LoadingSpinner/>}
      <div className="max-w-7xl mx-auto">
        <div className="mb-6 flex flex-col md:flex-row justify-between items-start md:items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-800">
              Expense Analytics
            </h1>
            <p className="text-gray-500">
              Insights and statistics about expense activities
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {analyticsSummary.map((item, index) => (
            <ExpenseStatCard key={index} {...item} />
          ))}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
          <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-6">
            <h2 className="text-lg font-semibold text-gray-800 mb-4">
              Total Expenses
            </h2>
            <ExpenseChart data={expenseData} />
          </div>

          <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-6">
            <h2 className="text-lg font-semibold text-gray-800 mb-4">
              Credit Score
            </h2>
            <CreditScoreChart data={creditScore} />
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-1 gap-6">
          <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-6">
            <h2 className="text-lg font-semibold text-gray-800 mb-4">
              Predicted Credit Score
            </h2>
            <PredictedCreditScoreChart suggestionData={suggestionData} creditScore={creditScore} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClientData;
