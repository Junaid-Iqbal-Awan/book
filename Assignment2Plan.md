# Assignment 2 Progress + Completion Sheet

## Current Status (Checked Against Rubric)

### Part I: Containerized Deployment
- Done: `book/Dockerfile` is present and production-ready (Node 20, installs dependencies, builds frontend, starts backend).
- Done: `book/docker-compose.yml` includes app + Mongo services.
- Done: Persistent DB volume is configured (`mongo_data_p1:/data/db`) as required.
- Done: App image/tag is configured for Docker Hub (`junaid283/bookstore-app:part1`).
- Done: Part I app port and container names are set (`5000`, `bookstore-app-p1`, `mongo-p1`).
- Done: Compose syntax validated successfully (`docker compose -f docker-compose.yml config`).
- Pending manual proof: container is running on EC2 and reachable publicly.
- Pending manual proof: image push to Docker Hub completed and verified.

### Part II: Jenkins Pipeline Automation
- Done: `book/docker-compose.part2.yml` uses bind-mounted code volume (`./:/workspace`) instead of Dockerfile-based app image.
- Done: Different app port/container names from Part I are configured (`5001`, `bookstore-app-p2`, `mongo-p2`).
- Done: `book/Jenkinsfile` exists with stages for checkout, image pull, compose up/down, and status checks.
- Done: Compose syntax validated successfully (`docker compose -f docker-compose.part2.yml config`).
- Pending manual proof: Jenkins job is connected to GitHub and webhook triggers on push.
- Pending manual proof: Part II deployment is down initially and brought up by pipeline trigger.

## Verified Assignment Files
- `book/Dockerfile`
- `book/docker-compose.yml`
- `book/docker-compose.part2.yml`
- `book/Jenkinsfile`
- `book/.env.example`

## Remaining Steps to Complete Submission

1. Validate Part I locally or on EC2
   ```bash
   docker compose -f docker-compose.yml up -d --build
   docker compose -f docker-compose.yml ps
   ```
   Collect screenshots of:
   - compose services running
   - app in browser (`http://<APP_EC2_IP>:5000`)
   - API endpoint response (`/api/book`)

2. Validate DB persistence (required rubric evidence)
   ```bash
   docker compose -f docker-compose.yml down
   docker compose -f docker-compose.yml up -d
   ```
   Show that previously inserted data still exists (volume-backed Mongo).

3. Push Part I image to Docker Hub
   ```bash
   docker login
   docker push junaid283/bookstore-app:part1
   ```
   Capture image/tag screenshot from Docker Hub.

4. Configure Jenkins EC2 (Part II)
   - Install plugins: Git, Pipeline, Docker Pipeline
   - Create Pipeline job from repository
   - Use repository Jenkinsfile path: `book/Jenkinsfile`
   - Add GitHub webhook: `http://<JENKINS_IP>:8080/github-webhook/` (Push events)

5. Prove webhook-triggered automation
   - Keep Part II deployment down first.
   - Push a small commit to GitHub.
   - Capture Jenkins build triggered automatically.
   - Capture successful stage logs and running containers on port `5001`.

6. Final submission tasks
   - Add collaborator: `qasimalik@gmail.com`
   - Fill Google form with required URLs
   - Submit formatted report with micro-steps and screenshots for both parts

## Ready-to-Submit Evidence Checklist
- [ ] Part I deployment URL working on EC2 (`:5000`)
- [ ] Mongo persistence proof screenshot
- [ ] Docker Hub image `junaid283/bookstore-app:part1` visible
- [ ] Jenkins job screenshot (pipeline stages)
- [ ] GitHub webhook delivery success screenshot
- [ ] Part II initially down, then up by Jenkins trigger screenshot
- [ ] Collaborator added (`qasimalik@gmail.com`)
- [ ] Report + form submitted
