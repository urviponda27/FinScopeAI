import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoadingSpinner from "../components/LoadingSpinner";

const AddClientForm = ({ closeModal }) => {
  const [name, setName] = useState("");
  const [number, setNumber] = useState("");
  const [panNumber, setPanNumber] = useState("");
  const [loanType, setLoanType] = useState("");
  const [accountNumber, setAccountNumber] = useState("");
  const [ifscCode, setIfscCode] = useState("");
  const [pdfFile, setPdfFile] = useState();
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const registerCliet = async () => {
    // console.log("Name :- ", name);
    // console.log("Mobile :- ", number);
    // console.log("Pan Number :- ", panNumber);
    // console.log("Loan Type :- ", loanType);
    // console.log("Account Number :- ", accountNumber);
    // console.log("IFSC Code :- ", ifscCode);
    // console.log("PDF File :- ", pdfFile);

    try {
      setIsLoading(true)
      const formData = new FormData();

      formData.append("fullName", name); // string (min 2, max 100)
      formData.append("mobileNumber", number); // exactly 10 digits, numeric string
      formData.append("panNumber", panNumber); // e.g., "ABCDE1234F"
      formData.append("consentId", "baf9d5e2-14c4-4f90-b5b3-3e54a347dbf1"); // any string, min length 5
      formData.append("loanType", loanType); // must be one of: PERSONAL, HOME, EDUCATION, BUSINESS
      formData.append("accountNumber", accountNumber); // string (min 8, max 20)
      formData.append("ifscCode", ifscCode); // e.g., "HDFC0123456"
      formData.append("pdfFile", pdfFile); // File object (not null)

      const response = await axios.post(
        "http://localhost:8080/api/combined/register",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      );
      const dummy = await axios.get(
        `http://localhost:5000/categorize_transaction?user_id=${response.data}`
      );
      console.log("Dummy Response :- ", dummy.data);
      console.log("Upload success", response.data);
      navigate(`/clientData/${response.data}`);
    } catch (error) {
      console.error("Upload failed:", error.response?.data || error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      {/* Blur background behind modal */}
      <div
        className="absolute inset-0 bg-white/10 backdrop-blur-md"
        onClick={closeModal}
      ></div>

      <div className="relative z-10 bg-white rounded-2xl shadow-xl w-full max-w-md max-h-[90vh] overflow-hidden border border-indigo-100">
        {/* Close button */}
        <button
          onClick={closeModal}
          className="absolute top-3 right-4 text-gray-500 hover:text-gray-800 text-2xl"
        >
          ×
        </button>

        {/* Modal content with scroll */}
        <div className="p-8 overflow-y-auto h-full max-h-[90vh]">
          <div className="text-center mb-6">
            <h2 className="text-2xl font-bold text-gray-800">Add Client</h2>
          </div>

          <div className="space-y-4">
            <div>
              <label className="text-sm font-medium text-gray-700">Name</label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                placeholder="XYZ PQR"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>

            <div>
              <label className="text-sm font-medium text-gray-700">Mobile Number</label>
              <input
                type="number"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                placeholder="0000000000"
                value={number}
                onChange={(e) => setNumber(e.target.value)}
              />
            </div>

            <div>
              <label className="text-sm font-medium text-gray-700">Pan Number</label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                placeholder="ABCD1111E"
                value={panNumber}
                onChange={(e) => setPanNumber(e.target.value)}
              />
            </div>

            <div>
              <label className="text-sm font-medium text-gray-700">Loan Type</label>
              <select
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                value={loanType}
                onChange={(e) => setLoanType(e.target.value)}
              >
                <option value="">Select Loan Type</option>
                <option value="HOME">HOME</option>
                <option value="EDUCATION">EDUCATION</option>
                <option value="PERSONAL">PERSONAL</option>
                <option value="BUSINESS">BUSINESS</option>
              </select>
            </div>

            <div>
              <label className="text-sm font-medium text-gray-700">Account Number</label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                placeholder="00000000"
                value={accountNumber}
                onChange={(e) => setAccountNumber(e.target.value)}
              />
            </div>

            <div>
              <label className="text-sm font-medium text-gray-700">IFSC Code</label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-indigo-500"
                placeholder="ABCD01234"
                value={ifscCode}
                onChange={(e) => setIfscCode(e.target.value)}
              />
            </div>

            {/* PDF File Input */}
            <div>
              <label className="text-sm font-medium text-gray-700">Upload PDF</label>
              <input
                type="file"
                accept="application/pdf"
                className="w-full px-4 py-2 border border-gray-200 rounded-lg file:mr-4 file:py-2 file:px-4 file:border-0 file:bg-indigo-600 file:text-white file:rounded-lg"
                onChange={(e) => setPdfFile(e.target.files[0])}
              />
            </div>

            <button
              onClick={registerCliet}
              className={`w-full py-2 rounded-lg mt-2 text-white ${isLoading
                ? "bg-gray-400 cursor-not-allowed"
                : "bg-indigo-600 hover:bg-indigo-700"
                }`}
              disabled={isLoading}
            >
              {isLoading ? "Adding Guest" : "Add Guest"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );

};

export default AddClientForm;
