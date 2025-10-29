# Step 1: Build Angular App
FROM node:18 AS build
WORKDIR /app

# Copy dependency files and install packages
COPY package*.json ./
RUN npm install

# Copy the entire frontend source code
COPY . .

# Build the Angular project
RUN npm run build --prod

# Step 2: Serve using Nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
