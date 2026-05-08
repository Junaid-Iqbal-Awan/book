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

## Assignment 2: Part I (Docker + EC2)

### Files
- `Dockerfile`
- `docker-compose.yml`
- `.env.example` (copy to `.env`)

### Local Run (Part I)

```bash
cp .env.example .env
docker compose -f docker-compose.yml up -d --build
docker compose -f docker-compose.yml ps
```

App and API checks:
- `http://localhost:5000`
- `http://localhost:5000/api/book`

Stop:

```bash
docker compose -f docker-compose.yml down
```

### Push Docker Image to Docker Hub

```bash
docker login
docker push junaid283/bookstore-app:part1
```

### EC2 Deploy (Part I)

```bash
git clone https://github.com/Junaid-Iqbal-Awan/book.git
cd book
cp .env.example .env
docker compose -f docker-compose.yml up -d
```

Recommended inbound ports on App EC2:
- `22` (SSH)
- `5000` (Part I app)

## Assignment 2: Part II (Jenkins + Webhook)

### Files
- `docker-compose.part2.yml`
- `Jenkinsfile`

### Part II Behavior
- Uses code volume mount (assignment requirement).
- Uses different container names and app port (`5001`) from Part I.

### Jenkins Setup Checklist
1. Install Jenkins on a separate EC2 (recommended).
2. Install plugins: Git, Pipeline, Docker Pipeline.
3. Create Pipeline job from the GitHub repo and use the `Jenkinsfile`.
4. Add GitHub webhook:
	- URL: `http://<jenkins-public-ip>:8080/github-webhook/`
	- Event: Push
5. Trigger by push and verify pipeline stages complete.

Recommended inbound ports on Jenkins EC2:
- `22` (SSH)
- `8080` (Jenkins)

## Submission Notes

1. Keep Part I deployment up.
2. Keep Part II deployment down initially, then trigger via Jenkins pipeline.
3. Add collaborator: `qasimalik@gmail.com`.
4. Include report screenshots for all micro steps and required files.
test 
test 
test 
test
