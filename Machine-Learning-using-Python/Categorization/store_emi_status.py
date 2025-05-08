import json
import os
import subprocess

# File paths
mock_aa_path = "MockAA.json"
summary_path = "summary.json"  # renamed by you

# Load EMI data from MockAA.json
def load_emi_summary(filepath):
    if not os.path.exists(filepath):
        print("MockAA.json not found.")
        return None

    with open(filepath, "r") as file:
        data = json.load(file)

    emi_info = {
        "totalActiveEMIs": data["summary"]["totalActiveEMIs"],
        "totalMonthlyEMIAmount": data["summary"]["totalMonthlyEMIAmount"],
        "regularEMIPayments": data["summary"]["regularEMIPayments"],
        "lateEMIPayments": data["summary"]["lateEMIPayments"],
        "overallEMIBehavior": data["summary"]["overallEMIBehavior"],
        "EMIRemarks": data.get("remarks", "")
    }

    return emi_info

# Update summary.json
def update_summary_with_emi(summary_path, emi_info):
    if not os.path.exists(summary_path):
        print("summary.json not found.")
        return

    with open(summary_path, "r") as file:
        summary_data = json.load(file)

    # Update with EMI info
    summary_data.update(emi_info)

    with open(summary_path, "w") as file:
        json.dump(summary_data, file, indent=4)

    print(f"EMI information successfully added to {summary_path}")

# Main
if __name__ == "__main__":
    emi_summary = load_emi_summary(mock_aa_path)
    if emi_summary:
        update_summary_with_emi(summary_path, emi_summary)
    subprocess.run(["python", "CurrentCreditScore.py"])
