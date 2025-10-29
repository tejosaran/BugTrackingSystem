# Stage 1: Build Angular app
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install -g @angular/cli && npm install

COPY . .
RUN ng build --configuration production

# Stage 2: Serve using Nginx
FROM nginx:alpine

# Copy built Angular files to Nginx html folder
COPY --from=build /app/dist/bug-tracking-system-frontend /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
