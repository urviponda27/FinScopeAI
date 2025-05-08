import {  Check, Clock, List, X } from "lucide-react"
const StatCard = ({ title, value, icon }) => {
  const icons = {
    total: (
        <List className="h-6 w-6" />
    ),
    approved: (
      <Check className="h-6 w-6"/>
    ),
    rejected: (
      <X />
    ),
    pending: (
      <Clock className="w-6 h-6" />
    ),
  
  }

  return (
    <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-6 hover:shadow-lg transition-shadow">
      <div className="flex items-center">
        <div className="w-12 h-12 bg-indigo-100 rounded-full flex items-center justify-center mr-4">
          <span className="text-indigo-600">{icons[icon]}</span>
        </div>
        <div>
          <p className="text-sm font-medium text-gray-500">{title}</p>
          <h3 className="text-2xl font-bold text-gray-800">{value}</h3>
        </div>
      </div>
    </div>
  )
}

export default StatCard
