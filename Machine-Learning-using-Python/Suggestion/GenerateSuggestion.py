import json
from openai import OpenAI
import os
import subprocess

# Initialize Groq-compatible OpenAI client
client = OpenAI(api_key=os.getenv("GROQ_API_KEY", "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"),
                base_url="https://api.groq.com/openai/v1")

def load_summary(file_path='summary.json'):
    with open(file_path, 'r') as f:
        return json.load(f)

def estimate_score_gain(issue_key, summary):
    income = summary.get("income", 1)
    gains = {
        "gambling": 0,
        "LateEMIs": 0,
        "LowSavings": 0,
        "NonEssentialSpending": 0
    }

    if issue_key == "gambling":
        gambling_ratio = summary.get("gambling", 0) / income
        if gambling_ratio > 0.10:
            gains["gambling"] = 100
        elif gambling_ratio > 0.05:
            gains["gambling"] = 50
        elif gambling_ratio > 0.01:
            gains["gambling"] = 20

    elif issue_key == "LateEMIs":
        gains["LateEMIs"] = summary.get("lateEMIPayments", 0) * 50

    elif issue_key == "LowSavings":
        total_spending = sum(summary.get(k, 0) for k in [
            "entertainment", "utilities", "shopping", "gambling", "food", "travel", "others"
        ])
        expected_savings = income - total_spending
        savings = summary.get("savings", 0)
        savings_gap = expected_savings - savings

        if savings_gap > 0.2 * income:
            gains["LowSavings"] = 80
        elif savings_gap > 0.1 * income:
            gains["LowSavings"] = 40

    elif issue_key == "NonEssentialSpending":
        nes_total = sum(summary.get(k, 0) for k in ["entertainment", "shopping", "travel", "food"])
        nes_ratio = nes_total / income
        if nes_ratio > 0.5:
            gains["NonEssentialSpending"] = 60
        elif nes_ratio > 0.3:
            gains["NonEssentialSpending"] = 30
        elif nes_ratio > 0.15:
            gains["NonEssentialSpending"] = 10

    return gains.get(issue_key, 0)

def get_issues(summary):
    income = summary.get("income", 1)
    issues = []

    if (summary.get("gambling", 0) / income) > 0.01:
        issues.append("gambling")
    if summary.get("lateEMIPayments", 0) > 0:
        issues.append("LateEMIs")
    if summary.get("savings", 0) / income < 0.10:
        issues.append("LowSavings")
    nes_total = sum(summary.get(k, 0) for k in ["entertainment", "shopping", "travel", "food"])
    if (nes_total / income) > 0.15:
        issues.append("NonEssentialSpending")

    return issues

def build_prompt(issues, tier):
    prompt = "As a financial advisor, provide exactly one short, actionable, and personalized suggestion for each of the following financial issues:\n"
    for issue in issues:
        if issue == "gambling":
            prompt += "- High gambling expenses\n"
        elif issue == "LateEMIs":
            prompt += "- EMI payments have been delayed\n"
        elif issue == "LowSavings":
            prompt += "- Savings rate is too low\n"
        elif issue == "NonEssentialSpending":
            prompt += "- Overspending on food, travel, entertainment, and shopping\n"

    # Clear and strict instruction
    prompt += (
        "\nRespond with exactly one suggestion per line, matching the number of issues above.\n"
        "Do not include any introduction, summary, filler, or emojis.\n"
        "Only output the suggestions."
    )

    if tier == "High Risk":
        prompt += " Use a serious and firm tone."
    elif tier == "Moderate":
        prompt += " Use a supportive and constructive tone."

    return prompt

def call_llm(prompt):
    try:
        response = client.chat.completions.create(
            model="llama3-70b-8192",
            messages=[
                {"role": "system", "content": "You are a financial advisor who writes short and useful suggestions."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.7
        )
        lines = response.choices[0].message.content.strip().split("\n")
        suggestions = [line.strip("- ").strip() for line in lines if line.strip()]
        # Filter out any non-actionable intros like "Here are the suggestions:"
        filtered = [s for s in suggestions if not s.lower().startswith("here are")]
        return filtered
    except Exception as e:
        print(f"API Error: {str(e)}")
        return [f"Error: {str(e)}"]

def determine_credit_tier(score):
    if score >= 750:
        return "Excellent"
    elif score >= 600:
        return "Moderate"
    return "High Risk"

def save_output(score, tier, suggestion_pairs, file_path="../Suggestion/updates.json"):
    with open(file_path, 'w') as f:
        json.dump({
            "currCreditScore": score,
            "creditTier": tier,
            "suggestions": suggestion_pairs
        }, f, indent=4)

def main():
    try:
        summary = load_summary()
        score = summary.get("currCreditScore", 0)
        tier = determine_credit_tier(score)

        if tier == "Excellent":
            suggestion_pairs = {"No suggestions needed. User qualifies for loan seamlessly.": 0}
        else:
            issues = get_issues(summary)
            if not issues:
                suggestion_pairs = {"Your financial profile looks good, but your credit score could still be improved.": 0}
            else:
                prompt = build_prompt(issues, tier)
                suggestions = call_llm(prompt)

                suggestion_pairs = {}
                total_score = score
                for i, issue in enumerate(issues):
                    if i < len(suggestions):
                        gain = estimate_score_gain(issue, summary)
                        if total_score + gain > 850:
                            gain = max(0, 850 - total_score)
                        if gain > 0:
                            suggestion_pairs[suggestions[i]] = gain
                            total_score += gain

        save_output(score, tier, suggestion_pairs)
        print("Successfully generated financial suggestions!")
    except Exception as e:
        print(f"An error occurred: {str(e)}")
        save_output(0, "Error", {f"Failed to process: {str(e)}": 0})

if __name__ == "__main__":
    main()
    subprocess.run(["python","../Suggestion/ExposeSummary.py"])
    subprocess.run(["python","../Suggestion/ExposeUpdates.py"])
