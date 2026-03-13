# Book Store Application

Cloud-ready Node.js full-stack app for EC2 and Elastic Beanstalk.

## Runtime

- Runtime: Node.js 20+
- Dependency manager: npm
- Start command: `npm start` (from repo root)
- Exposed port: `PORT` environment variable (default `5000`)

The backend serves API routes under `/api` and serves the built frontend from `Frontend/dist`.

## Environment Variables

Required:
- `MONGO_URI` (example: `mongodb://127.0.0.1:27017/hogwarts_bookstore`)

Optional:
- `PORT` (default `5000`)
- `ADMIN_EMAIL`
- S3-related variables for cloud image storage

If S3 variables are not set, uploads are stored under `Backend/public`.

## Linux Commands (Clone / Install / Start)

```bash
git clone https://github.com/Junaid-Iqbal-Awan/book.git
cd book
npm install
npm run setup
PORT=5000 MONGO_URI="mongodb://127.0.0.1:27017/hogwarts_bookstore" npm start
```

## Ubuntu EC2 Quick Deploy (SSH)

1. Connect to EC2 from your machine:
	- PowerShell example:
	  ```powershell
	  ssh -i "L:\DevOps\<your-key>.pem" ubuntu@13.53.97.48
	  ```

2. On the EC2 server, install Node.js 20 and Git:
	```bash
	sudo apt update
	sudo apt install -y git curl
	curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
	sudo apt install -y nodejs
	node -v
	npm -v
	```

3. Clone and run the app:
	```bash
	git clone https://github.com/Junaid-Iqbal-Awan/book.git
	cd book
	npm install
	npm run setup
	export PORT=5000
	export MONGO_URI="mongodb://127.0.0.1:27017/hogwarts_bookstore"
	npm start
	```

4. Open EC2 Security Group inbound rules for the app port (default `5000`) to access it publicly.

## Elastic Beanstalk Notes

- Deploy this repository as a Node.js application.
- Elastic Beanstalk runs `npm install` and `npm start` automatically.
- Set the EB start command to: `npm run setup && npm start --prefix Backend`.
- Ensure `MONGO_URI` is set in Elastic Beanstalk environment variables.
- App listens on `PORT`, which is provided by the platform.
