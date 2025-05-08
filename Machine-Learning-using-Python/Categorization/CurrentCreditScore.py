import json
import os
import subprocess

summary_path = "summary.json"

def load_summary(filepath):
    if not os.path.exists(filepath):
        raise FileNotFoundError("summary.json not found.")

    with open(filepath, "r") as file:
        return json.load(file)

def save_summary(filepath, data):
    with open(filepath, "w") as file:
        json.dump(data, file, indent=4)

def calculate_credit_score(summary):
    # Base score
    score = 850

    # Extract values
    income = summary.get("income", 0)
    savings = summary.get("savings", 0)
    gambling = summary.get("gambling", 0)
    utilities = summary.get("utilities", 0)
    food = summary.get("food", 0)
    shopping = summary.get("shopping", 0)
    travel = summary.get("travel", 0)
    others = summary.get("others", 0)
    entertainment = summary.get("entertainment", 0)

    # EMI info
    total_emis = summary.get("totalActiveEMIs", 0)
    regular_emis = summary.get("regularEMIPayments", 0)
    late_emis = summary.get("lateEMIPayments", 0)

    # Loan type
    loan_type = summary.get("loantype", "").lower()

    ### --- Factor 1: Gambling Ratio Penalty ---
    gambling_ratio = gambling / income if income else 0
    if gambling_ratio > 0.10:
        score -= 100
    elif gambling_ratio > 0.05:
        score -= 50
    elif gambling_ratio > 0.01:
        score -= 20

    ### --- Factor 2: EMI Regularity Penalty ---
    if total_emis > 0:
        emi_irregularity_penalty = late_emis * 50
        score -= emi_irregularity_penalty

    ### --- Factor 3: Loan Type Risk Adjustment ---
    loan_penalty = 0
    if loan_type == "homeloan":
        loan_penalty = 10
    elif loan_type == "carloan":
        loan_penalty = 20
    elif loan_type == "businessloan":
        loan_penalty = 50
    elif loan_type == "personalloan":
        loan_penalty = 40
    score -= loan_penalty

    ### --- Factor 4: Savings Evaluation ---
    expected_savings = income - (entertainment + utilities + shopping + gambling + food + travel + others)
    savings_gap = expected_savings - savings

    if savings_gap > 0.2 * income:
        score -= 80
    elif savings_gap > 0.1 * income:
        score -= 40
    elif savings_gap < 0:
        score += 10

    ### --- Factor 5: Non-essential Spending Bias ---
    non_essential_spending = entertainment + shopping + travel + food
    nes_ratio = non_essential_spending / income if income else 0

    if nes_ratio > 0.5:
        score -= 60
    elif nes_ratio > 0.3:
        score -= 30
    elif nes_ratio > 0.15:
        score -= 10

    # Clamp score
    score = max(300, min(850, round(score)))
    return score

def main():
    try:
        # Set paths
        cat_path = os.path.join("..", "Categorization", "summary.json")
        sug_path = os.path.join("..", "Suggestion", "summary.json")

        # Load, calculate, and update
        summary = load_summary(cat_path)
        credit_score = calculate_credit_score(summary)
        summary["currCreditScore"] = credit_score

        # Save to Categorization
        save_summary(cat_path, summary)
        print(f"Credit score saved to Categorization: {credit_score}")

        # Ensure Suggestion folder exists
        os.makedirs(os.path.dirname(sug_path), exist_ok=True)

        # Save (or create) in Suggestion
        save_summary(sug_path, summary)
        print(f"summary.json copied to Suggestion with updated score")

    except Exception as e:
        print(f"Error: {e}")
    try:
        summary = load_summary(summary_path)
        credit_score = calculate_credit_score(summary)
        summary["currCreditScore"] = credit_score  # append new score
        save_summary(summary_path, summary)        # save updated file
        print(f"Credit score calculated and saved: {credit_score}")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
    print("Reached Here!!!!")
    subprocess.run(["python", "../Suggestion/GenerateSuggestion.py"])
