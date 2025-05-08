from flask import Flask, request, jsonify
from flask_cors import CORS
import subprocess

app = Flask(__name__)
CORS(app)  # or CORS(app, resources={r"/*": {"origins": "http://localhost:3002"}})

@app.route('/categorize_transaction', methods=['GET'])
def categorize_transaction():
    user_id = request.args.get('user_id')
    if not user_id:
        return jsonify({'error': 'Missing user_id parameter'}), 400

    try:
        subprocess.run(["python", "categorize_transactions.py", user_id], check=True)
        return jsonify({'message': 'Transaction categorization completed successfully.'}), 200
    except subprocess.CalledProcessError as e:
        return jsonify({'error': f'An error occurred while running the script: {str(e)}'}), 500

if __name__ == '__main__':
    app.run(debug=True)
#http://localhost:5000/categorize_transaction?user_id=1