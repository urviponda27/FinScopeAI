import { useEffect, useState } from "react";
import StatCard from "../components/StatCard";
import { IoPersonAdd } from "react-icons/io5";
import AddClientForm from "./AddClientForm";
import ApplicationStatistics from "../components/ApplicationStatistics";
import RecentClientsTable from "../components/RecentClientsTable";
import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import LoadingSpinner from "../components/LoadingSpinner";



const Dashboard = () => {
  const baseUrl = import.meta.env.VITE_API_BASE_URL;
  const [stats, setStats] = useState([
    { title: "Total Application", value: 0, icon: "total" },
    { title: "Approved Application", value: 0, icon: "approved" },
    { title: "Rejected Application", value: 0, icon: "rejected" },
    { title: "Pending Application", value: 0, icon: "pending" },
  ]);
  const [clients,setClients] = useState([]);
  const [monthlyData,setMonthlyData] = useState([]);
  const [isLoading,setIsLoading] = useState(false);
  const navigate = useNavigate();

  const getClients = async () => {
    try {
      setIsLoading(true);
      const response = await axios.get(`${baseUrl}/api/users/all`);
      const allApps = response.data;
  
      console.log("Clients :- ", allApps);
      setClients(allApps.slice(0, 5));
  
      const approved = allApps.filter(app => app.status === "APPROVED").length;
      const rejected = allApps.filter(app => app.status === "REJECTED").length;
      const pending = allApps.filter(app => app.status === "PENDING" || "IN_PROGRESS").length;
      const total = allApps.length;
  
      setStats([
        { title: "Total Application", value: total, icon: "total" },
        { title: "Approved Application", value: approved, icon: "approved" },
        { title: "Rejected Application", value: rejected, icon: "rejected" },
        { title: "Pending Application", value: pending, icon: "pending" },
      ]);
  
      // Prepare monthly data for chart
      setTimeout(() => {
        const monthLabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"];
        const monthlyCounts = Array(12).fill(0);
        allApps.forEach((app) => {
          const month = new Date(app.createdAt).getMonth();
          monthlyCounts[month]++;
        });
        const chartData = monthLabels.map((month, index) => ({
          month,
          applications: monthlyCounts[index],
        }));
        setMonthlyData(chartData);
        console.log("Monthly Data :- ", chartData);
      }, 0); // 2 second timeout
      
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  }
  
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      navigate("/login");
    }
    getClients();
  },[])
  const [showModal, setShowModal] = useState(false);
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-purple-50 py-6 px-4 sm:px-6">
      {isLoading && <LoadingSpinner/>}
      <div className="max-w-7xl mx-auto">
        <div className="mb-6 flex flex-col md:flex-row justify-between items-start md:items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-800">
              Admin Dashboard
            </h1>
            <p className="text-gray-500">
              Overview of Customer application and statistics
            </p>
          </div>
          <button
            onClick={() => setShowModal(true)}
            // disabled={exportLoading || filteredStudents.length === 0}
            className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 flex items-center"
          >
            <IoPersonAdd className="mr-2" />
            Add Client
          </button>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stats.map((stat, index) => (
            <StatCard key={index} {...stat} />
          ))}
        </div>
        {monthlyData.length > 0 ? (
            <div className=" w-full grid grid-cols-1 lg:grid-cols-1 gap-6 mb-8">
            <div className="lg:col-span-2 bg-white rounded-xl shadow-md border border-indigo-100 p-6">
              <div className="flex items-center gap-2 mb-4">
                <h2 className="text-lg font-semibold text-gray-800">
                  Application Statistics
                </h2>
              </div>
              <ApplicationStatistics data={monthlyData} />
            </div>
          </div>
        ):(
          <div className="text-center text-gray-500 my-10">Loading statistics...</div>
        )}
        

        <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-6">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Recent Applications
          </h2>
          <RecentClientsTable data={clients} />
        </div>
      </div>
      {showModal && <AddClientForm closeModal={() => setShowModal(false)} />}
    </div>
  );
};

export default Dashboard;
