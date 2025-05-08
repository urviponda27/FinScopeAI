import sys
import fitz  # PyMuPDF
import os
import csv
import json
import pandas as pd
import re
import requests
import subprocess
from collections import defaultdict
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from labeled_data import categories

# === 0. Get user_id from command line ===
if len(sys.argv) < 2:
    print("User ID not provided.")
    sys.exit(1)

user_id = sys.argv[1]

# === 1. Load and train classifier ===
X_train, y_train = [], []
for cat, words in categories.items():
    for word in words:
        X_train.append(word.lower())
        y_train.append(cat)

vectorizer = TfidfVectorizer()
X_train_vec = vectorizer.fit_transform(X_train)
model = MultinomialNB()
model.fit(X_train_vec, y_train)

# === 2. Utility Functions ===
def is_vague(desc):
    return bool(re.fullmatch(r"[A-Z]*\d+[A-Z]*", desc.strip(), re.IGNORECASE))

def classify_description(desc):
    desc_clean = desc.lower()
    if is_vague(desc_clean):
        return "others"
    desc_vec = vectorizer.transform([desc_clean])
    predicted_category = model.predict(desc_vec)[0]
    return predicted_category

def fetch_pdf_and_headers(user_id):
    url = f"http://localhost:8080/api/combined/pdf-consent/{user_id}"
    response = requests.get(url)

    if response.status_code != 200:
        raise Exception(f"Failed to fetch PDF and consent info. Status code: {response.status_code}, Message: {response.text}")

    pdf_path = "downloaded_bank_statement.pdf"
    with open(pdf_path, 'wb') as f:
        f.write(response.content)

    consent_id = response.headers.get("Consent-ID")
    loan_type = response.headers.get("Loan-Type")

    print(f"Downloaded PDF to {pdf_path}")
    print(f"Consent ID: {consent_id}")
    print(f"Loan Type: {loan_type}")

    return pdf_path, loan_type, consent_id

def extract_transactions(pdf_path):
    doc = fitz.open(pdf_path)
    transactions = []
    buffer = []

    for page_number, page in enumerate(doc):
        print(f"\n--- Page {page_number + 1} ---")
        lines = page.get_text().split('\n')

        for line in lines:
            line = line.strip()
            if not line:
                continue
            # print(f"Raw line: {repr(line)}")
            buffer.append(line)

            if len(buffer) == 5:
                datetime, description, acc_no, txn_type, amount_str = buffer
                buffer = []

                try:
                    amount = float(amount_str.replace(',', ''))
                except ValueError:
                    continue

                txn_type = txn_type.lower()
                if txn_type == "debited":
                    category = classify_description(description)
                elif txn_type == "credited":
                    category = "income"
                else:
                    continue

                txn = {
                    "datetime": datetime,
                    "description": description,
                    "amount": amount,
                    "category": category,
                    "type": txn_type
                }

                transactions.append(txn)
                # print(f"Parsed: {txn}")

    print(f"\Total transactions parsed: {len(transactions)}")
    return transactions

def save_to_csv(transactions, csv_path):
    with open(csv_path, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.DictWriter(file, fieldnames=["datetime", "description", "amount", "category", "type"])
        writer.writeheader()
        writer.writerows(transactions)
    print(f"Transactions saved to: {csv_path}")

def summarize_from_csv(csv_path):
    df = pd.read_csv(csv_path)
    summary = df.groupby("category")["amount"].sum().to_dict()
    return summary

# === 3. Main Entry Point ===
if __name__ == "__main__":
    try:
        # user_id = 1  # Change this as needed
        pdf_path, loan_type, consent_id = fetch_pdf_and_headers(user_id)
        csv_path = "categorized_transactions.csv"

        if not os.path.exists(pdf_path):
            print("PDF not found after download.")
        else:
            transactions = extract_transactions(pdf_path)

            if not transactions:
                print("No transactions found.")
            else:
                save_to_csv(transactions, csv_path)
                summary = summarize_from_csv(csv_path)

                income = summary.get("income", 0)
                other_total = sum(amount for cat, amount in summary.items() if cat != "income")
                savings = income - other_total
                summary["savings"] = round(savings, 2)
                summary["loantype"] = loan_type
                summary["consentId"] = consent_id
                summary["userId"] = user_id

                with open("summary.json", "w") as f:
                    json.dump(summary, f, indent=4)

                print("\n=== Summary ===")
                for key, val in summary.items():
                    if key != "consentId":
                        print(f"{key}: ₹{val:.2f}" if isinstance(val, (int, float)) else f"{key}: {val}")
                    else:
                        print(f"{key}: {val}")

                print("\Summary saved to 'summary.json'")
                subprocess.run(["python", "store_emi_status.py"])

    except Exception as e:
        print(f"Error occurred: {str(e)}")
