import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import MainLogo from "../assets/MainLogo.png";

export default function Navbar() {
  const [token, setToken] = useState(localStorage.getItem("accessToken"));
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const navigate = useNavigate();

  // Listen for storage changes to refresh token state
  useEffect(() => {
    const handleStorageChange = () => {
      setToken(localStorage.getItem("accessToken"));
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    window.dispatchEvent(new Event("storage")); // This triggers Navbar update
    navigate("/login");
  };

  return (
    <nav className="bg-white shadow-md relative z-10">
      <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-indigo-500 to-purple-600"></div>
      <div className="max-w-7xl mx-auto px-4 sm:px-6">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/" className="flex items-center">
              <img src={MainLogo} alt="Logo" className="w-14 h-14 rounded-full" />
              <span className="text-2xl font-bold bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
                FinScopeAI
              </span>
            </Link>
          </div>

          {token && (
            <div className="hidden md:flex md:items-center md:space-x-6">
              <div className="flex gap-6">
                <NavLink to="/dashboard">Dashboard</NavLink>
                <NavLink to="/clients">Clients</NavLink>
              </div>
              <div className="flex items-center gap-3 ml-6 border-l border-gray-100 pl-6">
                <button
                  onClick={handleLogout}
                  className="bg-red-50 hover:bg-red-100 text-red-600 px-3 py-1.5 rounded-lg text-sm font-medium transition-colors border border-red-100"
                >
                  Logout
                </button>
              </div>
            </div>
          )}
        </div>

        <div className="md:hidden flex items-center">
          <button
            onClick={toggleMenu}
            className="inline-flex items-center justify-center p-2 rounded-md text-gray-600 hover:text-indigo-600 hover:bg-gray-100"
          >
            <span className="sr-only">Open main menu</span>
            {isMenuOpen ? (
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            ) : (
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            )}
          </button>
        </div>

        {!token && (
          <div className="md:hidden flex items-center">
            <Link
              to="/login"
              className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg text-sm font-medium"
            >
              Login
            </Link>
          </div>
        )}
      </div>

      {isMenuOpen && token && (
        <div className="md:hidden">
          <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3 border-t border-gray-100">
            <MobileNavLink to="/dashboard" onClick={toggleMenu}>
              Dashboard
            </MobileNavLink>
            <MobileNavLink to="/clients" onClick={toggleMenu}>
              Clients
            </MobileNavLink>
          </div>
          <div className="pt-4 pb-3 border-t border-gray-100">
            <div className="mt-3 px-2 space-y-1">
              <button
                onClick={handleLogout}
                className="w-full text-left px-3 py-2 rounded-md text-base font-medium text-red-600 hover:bg-red-50"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      )}
    </nav>
  );
}

const NavLink = ({ to, children }) => (
  <Link
    to={to}
    className="text-gray-600 hover:text-indigo-600 font-medium text-sm relative group"
  >
    {children}
    <span className="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-600 group-hover:w-full transition-all duration-300" />
  </Link>
);

const MobileNavLink = ({ to, children, onClick }) => (
  <Link
    to={to}
    className="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-indigo-600 hover:bg-gray-50"
    onClick={onClick}
  >
    {children}
  </Link>
);
