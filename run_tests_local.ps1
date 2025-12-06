# Run Selenium Tests Locally
# This script runs the Selenium tests using the markhobson/maven-chrome Docker image

Write-Host "Running Selenium Tests for BookStore application..." -ForegroundColor Cyan

# Check if Docker is running
$dockerRunning = docker info 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Docker is not running. Please start Docker Desktop." -ForegroundColor Red
    exit 1
}

# Check if application is running
$appRunning = docker ps --filter "name=bookstore-frontend" --format "{{.Names}}"
if (-not $appRunning) {
    Write-Host "WARNING: BookStore application is not running." -ForegroundColor Yellow
    Write-Host "Starting application with docker-compose..." -ForegroundColor Yellow
    docker compose up -d
    Write-Host "Waiting 30 seconds for services to start..." -ForegroundColor Yellow
    Start-Sleep -Seconds 30
}

# Run tests
Write-Host "`nRunning Selenium tests in Docker container..." -ForegroundColor Cyan
docker run --rm `
    -v "${PWD}/SeleniumTests:/app" `
    -w /app `
    --add-host=host.docker.internal:host-gateway `
    markhobson/maven-chrome:latest `
    mvn clean test "-Dbase.url=http://host.docker.internal:80"

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✅ All tests passed!" -ForegroundColor Green
} else {
    Write-Host "`n❌ Some tests failed. Check the output above." -ForegroundColor Red
}

Write-Host "`nTest reports available at: SeleniumTests/target/surefire-reports/" -ForegroundColor Cyan
