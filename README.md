# Book Store Application

Minimal full-stack runtime for local and VM deployment.

## Requirements

- Node.js 20+
- npm
- MongoDB available at `MONGO_URI` (or local default `mongodb://127.0.0.1:27017/hogwarts_bookstore`)

## Install

From repository root:

```powershell
npm install
cd Backend; npm install; cd ..
cd Frontend; npm install; cd ..
```

## Run

From repository root:

```powershell
npm start
```

This starts:
- Backend API on port `5000` (or `PORT` if set)
- Frontend Vite server on port `5174`

## Environment

Backend supports:
- `PORT`
- `MONGO_URI`
- `ADMIN_EMAIL` (optional)
- S3-related vars (optional for image storage)

If S3 variables are not set, uploads are stored under `Backend/public`.
