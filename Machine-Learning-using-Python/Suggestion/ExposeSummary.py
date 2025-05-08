import json
import requests

def expose_summary():
    with open('../Suggestion/summary.json', 'r') as file:
        summary_data = json.load(file)

    # Read userID from the JSON file
    user_id = summary_data.get("userId")

    if not user_id:
        print("userID not found in summary.json.")
        return

    # URL of your Spring Boot endpoint
    url = f'http://localhost:8080/api/summary/save/{user_id}'

    headers = {
        'Content-Type': 'application/json'
    }

    response = requests.post(url, headers=headers, json=summary_data)

    if response.status_code == 200:
        print("Summary data sent successfully.")
    else:
        print(f"Failed to send summary data: {response.status_code} {response.text}")

# Example usage:
if __name__ == '__main__':
    expose_summary()
