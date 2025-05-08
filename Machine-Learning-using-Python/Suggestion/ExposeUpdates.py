import json
import requests

def expose_updates():
    # Read userID from summary.json
    try:
        with open('../Suggestion/summary.json', 'r') as summary_file:
            summary_data = json.load(summary_file)
            user_id = summary_data.get("userId")
    except FileNotFoundError:
        print("summary.json file not found.")
        return
    except json.JSONDecodeError:
        print("summary.json is not a valid JSON file.")
        return

    if not user_id:
        print("userID not found in summary.json.")
        return

    # Load updates.json
    try:
        with open('../Suggestion/updates.json', 'r') as updates_file:
            updates_data = json.load(updates_file)
    except FileNotFoundError:
        print("updates.json file not found.")
        return
    except json.JSONDecodeError:
        print("updates.json is not a valid JSON file.")
        return

    # URL of your Spring Boot endpoint
    url = f'http://localhost:8080/api/updates/user/{user_id}'

    headers = {
        'Content-Type': 'application/json'
    }

    response = requests.post(url, headers=headers, json=updates_data)

    if response.status_code == 200:
        print("Updates data sent successfully.")
    else:
        print(f"Failed to send updates data: {response.status_code} {response.text}")

# Example usage:
if __name__ == '__main__':
    expose_updates()
