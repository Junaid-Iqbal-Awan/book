FROM node:20-alpine

WORKDIR /app

COPY . .

# Install backend/frontend dependencies and build the frontend bundle.
RUN npm run install:all && npm run build

ENV NODE_ENV=production
ENV PORT=5000
ENV UPLOAD_DIR=/app/Backend/public

EXPOSE 5000

CMD ["npm", "start", "--prefix", "Backend"]
