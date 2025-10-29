# Step 1: Build Angular app
FROM node:18 AS build
WORKDIR /app

# Copy package files first
COPY frontend/package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the frontend source code
COPY frontend/ .

# Build the Angular project (replace project-name if needed)
RUN npm run build -- --configuration production

# Step 2: Serve built app via nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
