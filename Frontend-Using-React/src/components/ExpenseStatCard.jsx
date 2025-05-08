import {  BadgeDollarSignIcon, Bus, BusFront, BusFrontIcon, BusIcon, Check, Clock, Coins, CoinsIcon, Cross, CrossIcon, DollarSign, DollarSignIcon, Hand, List, ListCheckIcon, ListCollapse, ListEnd, ListFilter, PersonStanding, Pizza, Plane, PlaneLanding, Train, TrainFront, TrainFrontTunnel, TrainFrontTunnelIcon, X } from "lucide-react"
import { BiCoinStack, BiDollarCircle, BiMoney, BiMoneyWithdraw } from "react-icons/bi"

const ExpenseStatCard = ({ title, value, icon, description }) => {
  const icons = {
    total: (
        <BiCoinStack className="h-6 w-6" />
    ),
    approved: (
      <BadgeDollarSignIcon className="h-6 w-6"/>
    ),
    rejected: (
      <Check className="h-6 w-6" />
    ),
    pending: (
      <Clock className="w-6 h-6" />
    ),
  }

  return (
    <div className="bg-white rounded-xl shadow-md border border-indigo-100 p-4 flex items-center">
      <div className="w-12 h-12 bg-indigo-100 rounded-full flex items-center justify-center mr-4 shrink-0">
        <div className="text-indigo-600">{icons[icon]}</div>
      </div>
      <div>
        <h2 className="text-sm font-medium text-gray-500">{title}</h2>
        <h3 className="text-lg font-semibold text-gray-800">{value}</h3>
        <p className="text-sm text-gray-500">{description}</p>
      </div>
    </div>
  );
}

export default ExpenseStatCard;
